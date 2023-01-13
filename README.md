OOP Assignment 2

PART 1 :-

-Threads & ThreadPool:

*Threads:

A thread in Java is the direction or path that is taken while a program is being executed. Generally, all the programs have at least one thread, known as the main thread, that is provided by the JVM or Java Virtual Machine at the starting of the program's execution.

A thread is a single sequential flow of control within a program. The real excitement surrounding threads is not about a single sequential thread. Rather, it's about the use of multiple threads running at the same time and performing different tasks in a single program

*ThreadPool:

Java Thread pool represents a group of worker threads that are waiting for the job and reused many times. In the case of a thread pool, a group of fixed-size threads is created. A thread from the thread pool is pulled out and assigned a job by the service provider.

A thread pool reuses previously created threads to execute current tasks and offers a solution to the problem of thread cycle overhead and resource thrashing.

-The first part EX_2

In this part we made a number of text files , and sum the number of lines in these files.
we did this 3 times in 3 different methods , first time without threads , second time with using threads, the third time with using threadpool.
to do this we used 4 functions :

1. createTextFile - this function create n text files , the function returns an array with the names of the files.
2. getNumOfLines - this function gets an array with thw names of the files , and returns the sum of the lines in the text .
3. getNumOfLinesThreads - this function gets an array with thw names of the files , and returns the number of the lines in the text (with threads).
4. getNumOfLinesThreadPool - this function gets an array with thw names of the files , and returns the sum of the lines in the text (with threadpool).

** from the test we did on methods 2,3,4 - we conclude that using threads is faster than using threadpool , and using threadpool is faster than not using threads at all .

PART 2 :-

Task -

this class ia an object which represent tasks with pirority , this class implements the Callable interface and the comparable interface .

CustomExecutor -

this class represent our threadpoolexecutor , as it use his methods to create a coustem threadpool .
its holds the max priority of the task in the queue .

TaskType -

an enum with a kind of task , IO defined as 2 , computational is defined as 1 , other as 3 .

** from a tests we did we can conclude that using threads/threadpool is better than not use them at all , and use threads is faster than use a threadpool.
this is the results we got from the time test we made : 

