import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

List<PCB> q1;
List<PCB> q2;
int currentTime;
int NotEnd;

public Scheduler() {
q1 = new ArrayList<>();
q2 = new ArrayList<>();
currentTime = 0;
NotEnd=0; //SJF
}

public void addProcess(String processID, int priority, int arrivalTime, int cpuBurst) {
PCB process = new PCB(processID, priority, arrivalTime, cpuBurst);
if (priority == 1)
q1.add(process);
else
q2.add(process);
}

public String calculateAverages(List<PCB> scheduleOrder) {
    int totalTurnaroundTime = 0;
    int totalWaitingTime = 0;
    int totalResponseTime = 0;
    // Calculate totals
    for (PCB process : scheduleOrder) {
    totalTurnaroundTime += process.turnaroundTime;
    totalWaitingTime += process.waitingTime;
    totalResponseTime += process.responseTime;
    }
    double avgTurnaroundTime = (double) totalTurnaroundTime / scheduleOrder.size();
    double avgWaitingTime = (double) totalWaitingTime / scheduleOrder.size();
    double avgResponseTime = (double) totalResponseTime / scheduleOrder.size();
    String averages = "Average Turnaround Time: " + avgTurnaroundTime + "\n" 
    + "Average Waiting Time: " + avgWaitingTime + "\n"
    + "Average Response Time: " + avgResponseTime;
 return averages;
}

public void schedule() {

    Collections.sort(q1, Comparator.comparingInt(process -> process.newarrivalTime));
    Collections.sort(q2, Comparator.comparingInt(process -> process.newarrivalTime));

    PCB shortestJob = null;
    List<PCB> scheduleOrder = new ArrayList<>();

    while (!q1.isEmpty() || !q2.isEmpty()) { 

    if (!q1.isEmpty() && q1.get(0).arrivalTime <= currentTime) { // no process in q1 has arrived
      PCB currentProcess = q1.remove(0);
      System.out.print(currentProcess.processID + " | ");

        if (currentProcess.startTime == -1) { // if process has entered for the first time
        currentProcess.startTime = currentTime; 
        }

      int remainingBurst = currentProcess.remainingTime;
        if (remainingBurst > 3) {
        currentTime += 3;
        currentProcess.newarrivalTime = currentTime;
        currentProcess.remainingTime -= 3;
        q1.add(currentProcess);
        Collections.sort(q1, Comparator.comparingInt(process -> process.
        newarrivalTime));
        } 
    
      else {
      currentTime += remainingBurst;
      currentProcess.responseTime = currentProcess.startTime - currentProcess.arrivalTime;
      currentProcess.terminationTime = currentTime; // set termination time when the process finishes
      currentProcess.turnaroundTime = currentProcess.terminationTime - currentProcess.arrivalTime;
      currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.cpuBurstTime;
      scheduleOrder.add(currentProcess); // add to the gantt chart
      }
    } // finish RR
    
    else if (q2.isEmpty()) {
      System.out.print("C| "); // to let the user know that there is nothing 
      currentTime++;
    } 

    else if (!q2.isEmpty() && q2.get(0).arrivalTime <= currentTime) { 

        if (NotEnd == 1) { // if there is unfinished process
         shortestJob = q2.get(0);
        }
        else {
            if (q2.get(0).arrivalTime <= currentTime) { // a process has arrived
               shortestJob = q2.get(0); // assume the first is the shortest
                for (PCB process : q2) { // look for the shortest
                    if (process.arrivalTime <= currentTime && process.remainingTime < shortestJob.remainingTime) {
                     shortestJob = process;
                    }
                }
            }
        }

        q2.remove(shortestJob);
        System.out.print(shortestJob.processID + " | ");
        if (shortestJob.startTime == -1) { // the process has entered for the first time
         shortestJob.startTime = currentTime; 
        }
        shortestJob.responseTime = shortestJob.startTime - shortestJob.arrivalTime;

        while ((q1.isEmpty()||q1.get(0).arrivalTime > currentTime) && shortestJob.remainingTime > 0) {
            shortestJob.remainingTime--; // work on q2 as long as q1 is empty or has not started yet
            currentTime++;
            shortestJob.terminationTime = currentTime ;
        }
            if (shortestJob.remainingTime > 0) { // if q2 was preempted by q1
            NotEnd = 1;
            q2.add(0, shortestJob); // add shorted to be first
            } 
            else {
            NotEnd = 0; 
            shortestJob.turnaroundTime = shortestJob.terminationTime - shortestJob.
            arrivalTime;
            shortestJob.waitingTime = shortestJob.turnaroundTime - shortestJob.
            cpuBurstTime;
            scheduleOrder.add(shortestJob);
            currentTime = shortestJob.terminationTime;
            } // finish SJF
    } 
     
     else {
     System.out.print("C| ");
     currentTime++;
    }
    } 


    // Print detailed information
    System.out.print(" ]");
    System.out.println("\n" +"\n" +
    "ProcessID | Pr | AT | Burst | ST | ET | TT | WT | RT");
    for (PCB process : scheduleOrder) {
    System.out.println(process.processID + "        | "
    + process.priority + "  | "
    + process.arrivalTime + "  | "
    + process.cpuBurstTime + "     | "
    + process.startTime + "  | "
    + process.terminationTime + "  | "
    + process.turnaroundTime + "  | "
    + process.waitingTime + "  | "
    + process.responseTime);
    }
    System.out.println( "\n" + calculateAverages(scheduleOrder));

    // Write to file
   
     try {
    FileWriter fileWriter = new FileWriter("Report.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);
    printWriter.println( "\n" + "ProcessID | Pr | AT | Burst | ST | ET | TT | WT | RT");
    for (PCB process : scheduleOrder) {
    printWriter.println(process.processID + "        | "
    + process.priority + "  | "
    + process.arrivalTime + "  | "
    + process.cpuBurstTime + "     | "
    + process.startTime + "  | "
    + process.terminationTime + "  | "
    + process.turnaroundTime + "  | "
    + process.waitingTime + "  | "
    + process.responseTime);
    }
    printWriter.println("\n" +calculateAverages(scheduleOrder));
    printWriter.close();
    } catch (IOException e) {
    e.printStackTrace();
    } 
    }

    }


