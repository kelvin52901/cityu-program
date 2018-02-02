/*
Lam Yu Hin
53079618
Lok Hiu Fung
54608948
CS3103 Assignment 2
The following program is a multithreaded C++ program under Linux.
It going to simulate the manager thread to control maximum 36 QC thread work.
*/
#include <iostream>
#include <cstdlib>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <semaphores.h>
using namespace std;
sem_t task;
struct ma{
	int x;
	int y;
	int report;
};
int ma_x=1;
int ma_y=1;
int mas_no=36;
struct ma mas[36];
struct ma* pointer=&mas[1];
	char report[6][6]; 	
void *dispatcher()
{
	sem_wait(&task);
	sleep(1);//generate row
	mas[ma_x*ma_y].x=ma_x;
	sleep(1);
	mas[ma_x*ma_y].x=ma_y;	//generate col
	if((ma_x*ma_y%6)==0){
	ma_x++;
	ms_y=1;
	}
		else
	ma_y++;
sem_post(&task);
}
void enqueue()
{
	
}
void dequeue()
{
	return *pointer.report++;
}

void *worker(void *arg)//implement the function of worker thread
{
	srand(time(NULL));		 
   dispatch();
    sleep(rand()%6+5);		//set the time interval : 5-10 second
    if(rand()%100 < 90)		//setting the condition : 90% pass the test and 10% fail the test
	{
	   *((char*)arg)='Q';	//apply the value 'Q' with the corresponding QC task when it pass the test
	}
    else
	{
	   *((char*)arg)='U';	//apply the value 'U' with the corresponding QC task when it pass the test
	}
	enqueue();
	pthread_exit(arg);		//exit and return the test result
}

int main ()
{
	//initialize the variable
	//QC task
	int x;					//start how many machine
	int counter=0;			//counter of unqualified.
	int rc;					//check the thread success or fail
	char result[6][6]; 	
	for(int i=0;i<6;i++)	//set all the task to default stage "I"
	{
		for(int j=0;j<6;j++)
		{
			result[i][j]='I';
		}
	}
	
	
	
	//check the data validation x between 0 - 36
	while(true)			//check the user input whether in 0<=n<=36
	{
		cout<<">qc ";
		cin>>x;
		if(x>0&&x<=36)
		{
			//printf("valid number.");
			break;
		}
		cout<<"Invaild input! Please input integer form 0 to 36"<<endl;
	}
	
	
	pthread_t threads[x];
	//create N threadID
	for(int i=0;i<6;i++)
	{
		
		for(int j=0;j<6;j++){
		
		if((i*6+j)<x)
		{
			rc = pthread_create(&threads[i*6+j], NULL,worker,&result[i][j]);	//create N thread with NthreadID 
		}
		if (rc)			//error checking for thread fail
		{
			cout << "Error:unable to create thread," << rc << endl;
			exit(-1);
		}
		}
	}
	
	for(int i=0;i<x;i++)
	{
		pthread_join(threads[i], NULL);	//wait until all the thread finish
	}
		for(int i=0;i<x;i++)
	{
		result[][]=report[i];	//wait until all the thread finish
	}
//	for(int i=0;i<6;i++)		//print the result and counting the QC fail item
//	{
//		for(int j=0;j<6;j++)
//		{
//			if(result[i][j]=='U')
//			{
//				counter++;
//			}
//			cout<<result[i][j];
//		}
//		cout<<endl;
//	}
//	
//	
//	
	cout<<counter<<" products are unqualified."<<endl;
	//----------end of main function--------
	pthread_exit(NULL);
	return 0;
}
