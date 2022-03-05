COMPILE AND RUN Classic.java
have ProblemOne.java and ProblemTwo.java in same directory
Number of threads is pre-set to 7.


Problem 2 explanation:
//Will finish when all n threads have visited twice
//USING SAME approach (OPTION 3, Queue) from problem 1, 
//it would be the most efficient in terms of preventing deadlock and starvation
//The only problem is it would rely on memory (creating Qnodes) 
//the good thing is java has a garbage collector that will eliminate inactive nodes automatically
//unlike C and C++ where each node has to be manually freed
//each thread updates it qnode.locked as well as qnode.next.locked (if not null) when done with lock
//Threads will wait in while loop until it is their turn.

Problem 1 explanation:
//this will rely on the # of cupcake Wrappers (liners) in the garbage to know if all threads have aten
//odd threads who have not visited will eat cupCake and increment cupCakeWrapper counter
//even threads who have not visited will request a cupcake, but not eat
//threads who have visited will do nothing except enter the room