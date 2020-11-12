import java.util.*;

public class Request {

	public static void main(String []args) {
		String restart;
	do {
		Scanner in = new Scanner(System.in);
		restart = "";
	//Section of code that obtains sleep information for the Producer.
	System.out.println("Enter the minimum followed by the maximum Producer thread sleep time in seconds");
	long minSleep = 1; //Default
	long maxSleep = 10; //Default
	boolean valid;
	do {
		valid = true;
		try {
		minSleep = (long)(in.nextLong()*1000); //Convert into milliseconds
		maxSleep = (long)(in.nextLong()*1000); ////Convert into milliseconds
		if((minSleep<0)||(maxSleep<0)) {
			valid=false;
			System.out.println("Please enter a postive number for the minimum and maximum seconds that the Producer thread can sleep");
		}
		else if(minSleep>maxSleep) {
			long temp = minSleep;
			minSleep = maxSleep;
			maxSleep = temp;
			System.out.println("It seems the minimum and maximum sleep time were inputted in the wrong order. They have been switched");
		}
		if(valid==true)
		System.out.println("The minimum Producer sleep time is " + minSleep/1000 + " and the maximum is " + maxSleep/1000 + " seconds");
		}
		catch(Exception e) {
			valid=false;
			System.out.println("Please enter a postive number for the minimum and maximum seconds that the Producer thread can sleep");
			in.nextLine();
		}
	}while(valid==false);
	
	//Section of code that obtains request length interval
	System.out.println("Enter the minimum followed by the maximum request length in seconds");
	long minRequest = 1; //Default
	long maxRequest = 10; //Default
	do {
		valid = true;
		try {
			minRequest = (long)(in.nextLong()*1000); //Convert into milliseconds
			maxRequest = (long)(in.nextLong()*1000); //Convert into milliseconds
			if((minRequest<0)||(maxRequest<0)){
				valid = false;
					System.out.println("Please enter a positive number for the minimum and maximum seconds that a request can take");
			}
			else if(minRequest>maxRequest) {
				long temp = minRequest;
				minRequest = maxRequest;
				maxRequest = temp;
				System.out.println("It seems the minimum and maximum request time were inputted in the wrong order. They have been switched");
			}
			if(valid==true)
			System.out.println("The minimum reqeust time is " + minRequest/1000 + " and the maximum is " + maxRequest/1000 + " seconds");
		}
		catch(Exception e) {
			valid = false;
			System.out.println("Please enter a positive number for the minimum and maximum seconds that a request can take");
			in.nextLine();
		}
	}while(valid==false);
	
	//Section of code that obtains number of Consumer threads
	System.out.println("Enter the number of Consumer threads that will run");
	int numThreads = 1; //Default;
	do {
		valid = true;
		try {
			numThreads = in.nextInt();
			if(numThreads<0) {
				System.out.println("Please enter a positive integer for the number of threads that will run");
				valid=false;
			}
			else
			if(valid==true)	{
			System.out.println(numThreads + " Consumer threads will run");
			}
		}
		catch(Exception e) {
			valid = false;
			System.out.println("Please enter a positive integer for the number of threads that will run");
			in.nextLine();
		}
	}while(valid==false);
	
	
	
	//Instantiate objects at start Threads
	LinkedList<Buffer> requests = new LinkedList<Buffer>();
	Producer master = new Producer(minSleep, maxSleep, minRequest, maxRequest, numThreads, requests);
	master.start();
	Consumer[] group = new Consumer[numThreads];
	for(int i=1; i<=numThreads; i++) {
		Consumer slave = new Consumer(i, requests);
		slave.start();
		group[i-1] = slave;
	}
	do { //Keep checking if user wants to exit
		restart = in.nextLine();
		
		if((restart.equalsIgnoreCase("exit")==true)||(restart.equalsIgnoreCase("restart")==true)) {
			{
			while(master.isAlive())
			master.interrupt();
			for(Consumer c: group)
				while(c.isAlive())
				c.interrupt();
			}
			if(restart.equalsIgnoreCase("exit")==true) {
				in.close();
				System.out.println("Program complete. Goodbye");
			return;
			}
		}
		else if(restart.equalsIgnoreCase("restart")==false)
	System.out.println("Enter \"exit\" to complete execution or \"restart\" to run this program again at any time\n");
	}while(restart.equalsIgnoreCase("restart")==false); //If exit was typed correct program would have already ended
	in.close();
	
	}while(restart.equalsIgnoreCase("restart")==true); //Main thread restart after it is typed correctly
	}
}

