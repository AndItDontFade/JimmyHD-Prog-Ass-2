import java.util.concurrent.atomic.AtomicReference;
import java.util.*;

public class Classic {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numThreads;
		System.out.println("There will 7 guests attending the bday party");
		numThreads = 7;
		ProblemOne[] probOne= new ProblemOne[numThreads];
		
		for(int i = 0; i < numThreads; i++) {
			probOne[i] = new ProblemOne(i + 1, numThreads);
			probOne[i].start();
		}
		for(int i = 0; i < numThreads; i++){
			try {
				probOne[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("All guests have been inside Labrynth");
		System.out.println("They left a total of " + probOne[0].cupcakeLiners.get() + " cupcake wrappers");
      	System.out.println();
      	System.out.println("===========BEGINNING PROBLEM TWO==============");
      	ProblemTwo[] probTwo = new ProblemTwo[numThreads];
      	for(int i = 0; i < numThreads; i++) {
			probTwo[i] = new ProblemTwo(i + 1);
			probTwo[i].start();
		}
		for(int i = 0; i < numThreads; i++){
			try {
				probTwo[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}