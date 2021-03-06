#include <iostream>
#include <cstdlib>
#include <string>
#include <vector>
#include <ctime>
#include <cmath>
#include <numeric>
#include <chrono>
#include <thread>
#include <limits>
#include <sstream>
#include <pthread.h>
#include <queue>
#include <unistd.h>
#include <mutex>
#include <iomanip>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
std::queue<int> Q;
int MAX_THREAD;
int sleepDuration;
int requestCount;
int qSize;
int j = 1;

//returns the current time in format H:M:S
std::string GetTime()
{
    auto timeRn = std::chrono::system_clock::now();
    std::time_t currentTime = std::chrono::system_clock::to_time_t(timeRn);
    std::string s(30, '\0');
    std::strftime(&s[0], s.size(), "%H:%M:%S", std::localtime(&currentTime));
    return s;
}

void *processRequest(void *args)
{
    //Continues to process all the requests until the queue is empty.
    while (1)
    {
        if(Q.size() >0){
        pthread_mutex_lock(&mutex);
        int sleepTime = Q.front();
        //Remove the first item from the Q and sleep for that amount of time.
        Q.pop();
        std::cout << "Request " << j << " "; // prints request id
        j++;
        printf("sleeping for %d seconds at %s. Consumer id: %lu\n", sleepTime, GetTime().c_str(), pthread_self());
        pthread_mutex_unlock(&mutex);
        sleep(sleepTime);
        }
        else{
            sleep(5);
        }
       
    }
    return NULL;
}

//The producer method that produces the jobs
void *master(void *args)
{
    int value = *((int*) args);
    int m = 1;
    while (requestCount > 0)
    {
        //If queue is full, wait before adding more.
        if (Q.size() == qSize)
        {
            printf("Queue full at %s Trying again in 5 seconds \n", GetTime().c_str());
            sleep(5);
        }
        else
        {
            //If queue is not full, push a another job to the queue
            while(Q.size()!=qSize && m != value+1)
            {
                int sleeping = (rand() % sleepDuration) + 1;
                 Q.push((rand() % sleepDuration) + 1);
                 requestCount--;
                printf("Produced request %d at %s, sleeping for %d seconds\n", m, GetTime().c_str(), sleeping);
                m++;
                sleep(sleeping);
            }
        }
    }
    //Once all the jobs have been processed
    printf("All processes added to queue. Stopped producing at %s \n", GetTime().c_str());
    return NULL;
}

int main()
{

    //Asking for user input
    std::cout << "Enter number of threads: ";
    std::cin >> MAX_THREAD;
    std::cout << "Enter the number of requests to produce: ";
    std::cin >> requestCount;
    std::cout << "Enter the max sleep duration of each request: ";
    std::cin >> sleepDuration;
    std::cout << "Enter max queue size: ";
    std::cin >> qSize;

    //Create the n threads, and master threads
    pthread_t threads[MAX_THREAD];
    pthread_t masterThread;
    pthread_create(&masterThread, NULL, master, (void*) &requestCount);

    //Creating n threads and running processRequest
    for (int i = 0; i < MAX_THREAD; i++)
    {
        pthread_create(&threads[i], NULL, processRequest, NULL);
    }
    // waiting for all threads to complete
    pthread_join(masterThread, NULL);
    for (int i = 0; i < MAX_THREAD; i++)
        pthread_join(threads[i], NULL);
}