# COSC315_Project2                              

This project is to implement a multithreaded request scheduler, similiar to Microsoft Apache. It will be implemented in both C++ and Java. Done by Alex Green, Jordan Bao, Mark Behnke and Talon Pratt.

### Contributions:
Alex: Created the Makefiles, set up and contributed to the readme, implemented the time method and user inputs and contributed with other areas of the C++ code.

Jordan: Implemented threads and synchronization for the Java portion by creating a Producer, Consumer, Buffer and main class

Mark: Wrote the thread synchronization methods (producer and consumer) using mutex’s to allow for continual read and writing to one resource between N threads. 

Talon: Created starting base code for the Java portion and the java portion of the readme file

### C++ Implementation details: 

  For the C++ implementation, we used mutex semaphores and pthreads to create a master thread that produces requests, which are then processed by the slave threads. We used 4 parameters for user input; the number of slave threads to be made, the number of requests to be processed, the maximum time each request can sleep, and the size of the bounded buffer. The master thread is the producer and will produce N number of requests to the queue. This queue is processed in the consumer threads where each thread will consume the top of the queue. They are synchronized through mutex’s where they lock before popping the resource from the queue. After they retrieve the element, they release the lock, allowing for the other threads to retrieve the queue elements. This will run till all the processes are completed.. The time is attained through the GetTime() method that incorporates chrono and time, to display the hours, minutes, and seconds of the local time of day. When all the request have been processed, the program will exit, printing out that the program is finished and the time of completion.  
  
### Build instructions for C++:

  To compile the C++ code on Linux, open the terminal. Then change directory, "cd", to the Project2 folder. Once there, type "make", which will build the program and create the executable code file. It should be under the name C++Code and the terminal will print out the first component of the Makefile. To run the code enter "./C++Code", then the file should run and ask you to enter the number of threads, then enter the number of requests to produce, then enter the max sleep duration of each request, and lastly, enter max queue size. In each case, the program expects and accepts only numbers inputs. The program then prints out the completion of each process. 

### Sample Output for C++: 
![2020-11-11](https://user-images.githubusercontent.com/60950452/98864870-63b00c00-241f-11eb-92ee-03ab0d2f93d6.png)

### Experience using C++ vs Java:
  We didn't find too much of a varariance in the difficulty of this project between languages. Both parts of this project were challenging in their own way and took us awhile to figure out and implement in a way that worked. Neither side was easier and they both took roughly the same amount of time to complete.
  
### Java Implementation Details:
  For the java implementation, there are 4 classes in use. The Producer and Consumer thread classes are used to define the two different threads and how they run. The Buffer class creates a buffer for the queue and the Request class creates the requests for the threads. User input is used for the max producer sleep time, consumer threads available, and max duration of a request. Requests are made and are added into a list for consumer threads to work on. These tasks are made so that the first available consumer thread will respond to it and begin working on it. Between each task creation the producer thread will sleep for a random amount of time within the parameters specified by the user. Each consumer thread will only work on a task as long as there isn't already a thread tasked to it through the use of monitors. After a consumer thread has finished the task, it is put back into the queue of available threads to be tasked to the next available task. 

### Build Instructions for Java:
  To compile the Java code on Linux, open the terminal. Then change directory, "cd", to the Project2-Java folder. Once there, type "make", which will build the program. The terminal should output "javac Request.java". To run the code enter "java Request", then the file should run and ask you to enter the minimum and maximum sleep time for the producer, the minimum and maximum time for requests, and the number of consumer threads to be run. The first two cases require positive numbers separated by a new line, while the third parameter requires only 1 number. The program is a continuous loop, so to exit, press the control key followed by the c key.
### Sample Output for Java
![Sample Java output 2020-11-11](https://i.imgur.com/9GVOFeF.png)
