import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//Will finish when all n threads have visited twice
//USING SAME approach (OPTION 3, Queue) from problem 1, 
//it would be the most efficient in terms of preventing deadlock and starvation
//The only problem is it would rely on memory (creating Qnodes) 
//the good thing is java has a garbage collector that will eliminate inactive nodes automatically
//unlike C and C++ where each node has to be manually freed
public class ProblemTwo extends Thread implements Lock{
	class Qnode{
		AtomicBoolean locked = new AtomicBoolean(false);
		Qnode next = null;
	}
	Qnode qnode;
	int threadID;
	int visitCounter = 0;
	static AtomicReference<Qnode> tail = new AtomicReference<Qnode>();
	
	public ProblemTwo(int threadID) {
		this.threadID = threadID;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(visitCounter < 2) {
			lock();
			enterRoom();
			unlock();
			System.out.println("Guest " + threadID + " saw the vase");
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
		visitCounter++;
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

