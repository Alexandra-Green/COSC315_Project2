import java.time.LocalTime;
import java.util.*;

public class Consumer extends Thread{
int cID;
LinkedList<Buffer> queue;

Consumer(){
	
}

Consumer(int cID, LinkedList<Buffer> queue){
	this.cID = cID;
	this.queue = queue;
}

public void run() {
	while(true) {
		Buffer request = new Buffer();
		request = null;
		//Leave lock as soon as possible with request data so others consumers can work
		if(queue.size()>0) 
			synchronized(queue) {
				request = queue.peek();
				queue.remove();
				queue.notifyAll(); //Make sure producer knows new space is available
			}
		
		//This part only runs if a request was pulled from queue
		if(request!=null) {
			System.out.println("Consumer " + cID + ": assigned request ID " + request.rID + ", processing request for the next " + request.length/1000 + " seconds, current time is " + LocalTime.now());
			try {
				Thread.sleep(request.length);
				System.out.println("Consumer " + cID + ": completed request ID " + request.rID + " at time " + LocalTime.now());
			}
			catch (InterruptedException e) {
				System.out.println("Consumer " + cID + " cancelled request ID " + request.rID + ", current time is " + LocalTime.now());
			}
		}
	//Empty queue so Consumer must wait
	if(queue.size()==0)
		synchronized(queue) {
	while(queue.size()==0) {
		try {
			queue.notifyAll(); //Make sure producer wakes up
			queue.wait();
		} catch (InterruptedException e) {
			return;
		}
	}
	}
	
}
}
}