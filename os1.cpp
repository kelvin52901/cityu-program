#include <unistd.h>
 #include <sys/types.h>
 #include <sys/wait.h> 
#include <stdio.h>
 #include  <stdlib.h>
 #include <string.h>
 #include <readline/readline.h>
 #include <readline/history.h>
 #include <signal.h>
 #include <sys/ipc.h>
 #include <sys/shm.h>
  int counter = 0;
char name[1024];
int rpid;
void pre(char * input, char * * arg) {//for separeate the line into pointer
  while ( * input != '\0') {
    while ( * input == ' ' || * input == '\n' || * input == '\t') { * input = '\0';
      input++;
    } * arg = input;
    * arg++;
    while ( * input != ' ' && * input != '\n' && * input != '\t' && * input != '\0')
      input++;
  } * arg = '\0';
}

void execute(char * * arg, char * f, char input[]) {
  pid_t pid;
  pid = fork();

  int status;
  if (pid < 0) {
    printf("error");
    exit(1);
  }
  if (pid == 0 && counter++ == 0) { //for child process
    if (execvp( * arg, arg) < 0) {
      counter--;
      printf("error,no file find\n");
      exit(0);
    } else
    if (strcmp(f, "bgkill") == 0 || strcmp(f, "bginfo") == 0) {
      printf("No background process\n");
      exit(0);
    }
  } else if (pid > 0) {//for parent
    strcpy(name, * arg);
    while (waitpid(-1, & status, WNOHANG) != pid) {//wait for child and scan for command
      f = readline(input);
      if ( waitpid(-1, & status, WNOHANG) != pid){
	  
      if (strcmp(f, "bginfo") == 0) {
        printf("%d:%s\n", pid, name);
        fflush(stdout);
      }
      if (strcmp(f, "bgkill") == 0) {
        kill(pid, SIGKILL);
        printf("%d: %s bgkilled\n", pid, name);
      }
      if (strcmp(f, "exit") == 0) {
      	kill(pid, SIGKILL);
        exit(0);
      }
      pre(f, arg);
      if (strcmp(arg[0], "bg") == 0) {
        printf("%d: %s is running, no new background process allowed.\n", pid, name);
      }
 }
      else
      if(strcmp(arg[0], "bg") == 0||strcmp(f, "bgkill") == 0||strcmp(f, "bginfo") == 0){
      	 printf("%d: %s completed.\n", pid, name);	  
   }
    }
  }
}
int main(void) {
  char input[] = "BP>";
  char * f;
  char * cp[64];
  pid_t pid;
  while (1) {
    fflush(stdout);
    f = readline(input);
    if (strcmp(f, "bgkill") == 0 || strcmp(f, "bginfo") == 0) {
      printf("No background process\n");
      continue;
    }
    pre(f, cp);
    if (strcmp(cp[0], "bg") == 0) {
      for (int i = 0; cp[i] != '\0'; i++) {
        cp[i] = cp[i + 1];
      }
if(strcmp(cp[0],"exit")==0)
exit(0); 
   }
    execute(cp, f, input);
  }
}
