import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//this will rely on the # of cupcake Wrappers (liners) in the garbage to know if all threads have aten
//odd threads who have not visited will eat cupCake and increment cupCakeWrapper counter
//even threads who have not visited will request a cupcake, but not eat
//threads who have visited will do nothing except enter the room
public class ProblemOne extends Thread implements Lock{
	class Qnode{
		AtomicBoolean locked = new AtomicBoolean(false);
		Qnode next = null;
	}
	Qnode qnode;
	boolean hasVisited = false;
	int numThreads;
	int threadID;
	int numOddThreads = 1;
	static AtomicBoolean allHaveVisited = new AtomicBoolean(false);
	static AtomicBoolean cupCake = new AtomicBoolean(true); 
	static AtomicInteger cupcakeLiners = new AtomicInteger(0);
	static AtomicReference<Qnode> tail = new AtomicReference<Qnode>();
	
	public ProblemOne(int threadID, int numThreads) {
		int i;
		this.threadID = threadID;
		this.numThreads = numThreads;
		numOddThreads = ((numThreads - 1) / 2) + 1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!allHaveVisited.get()) {
			lock();
			if(!allHaveVisited.get()) {
				enterRoom();
			}
			unlock();
			System.out.println("Guest " + threadID + " entered Labrynth");
		}
	}
	
	@Override
	public void lock() { //someone wants to enter the room
		Qnode pred = null;
		qnode = new Qnode();
		pred = tail.getAndSet(qnode);
		if(pred != null) {
			qnode.locked.set(true);
			pred.next = qnode;
			while(pred.locked.get()) {}
			//enter room and eat/not eat cupcake
		}
	}
	
	public Qnode getAndSetTail(Qnode node) {
		Qnode temp = tail.get();
		tail.set(node);
		return temp;
	}
	
	public void enterRoom() {
		if(hasVisited) {}
		else if(cupCake.get()) { //will already have cupcake from previous unique thread and eat it
			cupCake.set(false);
			cupcakeLiners.set(cupcakeLiners.get() + 1);
			if(cupcakeLiners.get() >= numOddThreads)
				allHaveVisited.set(true);
			hasVisited = true;
		}else {
			cupCake.set(true); //will request cupCake but not eat it
			hasVisited = true;
		}
	}
	
	@Override
	public void unlock() { //lets someone else enter the room
		// TODO Auto-generated method stub
		if(qnode.next != null) {
			qnode.next.locked.set(false);
		}
		
		qnode.locked.set(false);
	}
	
	//JAVA MADE ME IMPLEMENT ALL THESE
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
}