import java.time.LocalTime; 
import java.util.*;
public class Producer extends Thread{
	
	long minSleep;
	long maxSleep;
	long minRequest;
	long maxRequest;
	LinkedList<Buffer> queue; //Synchronized element
	int maxSize;
	static int rID = 1;
	
	Producer(){
		
	}
	
	Producer(long minS, long maxS, long minR, long maxR, int maxSize, LinkedList<Buffer> list){
		this.minSleep = minS;
		this.maxSleep = maxS;
		this.minRequest = minR;
		this.maxRequest = maxR;
		this.queue =  list;
		this.maxSize = maxSize;
	}
	
	public void run() {
		while(true) {
			//Keep producing until queue is full
			while(queue.size()<maxSize) {
				LocalTime t = LocalTime.now();
				long length = (long)(minRequest + 1 + Math.random()*(maxRequest-minRequest));
				queue.add(new Buffer(rID, length));
				System.out.println("Producer: produced request ID " + rID++ + ", length " + length/1000 + " seconds at time " + t); 
				long rest = (long)(minSleep + 1 + Math.random()*(maxSleep-minSleep));
				System.out.println("Producer: sleeping for " + rest/1000 + " seconds");
				try {
					Thread.sleep(rest);
				}
				catch (InterruptedException e) {
					return;
				}
				}
		
			//After Producer produces as much as possible it must wait
			synchronized(queue) {
			while(queue.size()==maxSize) {
				try {
					queue.notifyAll();
					queue.wait();
				} 
				catch (InterruptedException e) {
					return;
				}
			}
			//Make sure consumers are working as much as possible
			if(queue.size()==1) 
				queue.notify();
			else
				queue.notifyAll();
			}
			
		}
	
	}	
}