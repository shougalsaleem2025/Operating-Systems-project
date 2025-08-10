public class PCB {
	 String processID;
	 int priority;
	 int arrivalTime;
     int newarrivalTime;
	 int remainingTime; //remaining burst time
	 int cpuBurstTime; //original cpu burst time
     int startTime;
	 int terminationTime;
	 int turnaroundTime;
	 int waitingTime;
	 int responseTime;
	 boolean isStarted;
	
	
	public PCB(String processID, int priority, int arrivalTime, int cpuBurstTime) {
		super();
		this.processID = processID;
		this.priority = priority;
		this.arrivalTime = arrivalTime;
		this.cpuBurstTime = cpuBurstTime;
		this.remainingTime = cpuBurstTime;
		this.startTime= -1;
		this.terminationTime = -1;
		this.isStarted = false;
	}


	public String getProcessID() {
		return processID;
	}


	public void setProcessID(String processID) {
		this.processID = processID;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public int getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	public int getCpuBurstTime() {
		return cpuBurstTime;
	}


	public void setCpuBurstTime(int cpuBurstTime) {
		this.cpuBurstTime = cpuBurstTime;
	}


	public int getRemainingTime() {
		return remainingTime;
	}


	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}


	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
		this.isStarted = true;
		this.responseTime = this.startTime - this.arrivalTime;
	}


	public int getTerminationTime() {
		return terminationTime;
	}


	public void setTerminationTime(int terminationTime) {
		this.terminationTime = terminationTime;
		this.turnaroundTime = this.cpuBurstTime + this.waitingTime;  //both Non-preemptive & preemptive turnaround time
	}


	public int getTurnaroundTime() {
		return turnaroundTime;
	}


	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}


	public int getWaitingTime() {
		return waitingTime;
	}


	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}


	public int getResponseTime() {
		return responseTime;
	}


	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	
	public boolean hasStarted(int currentTime) {
	        return startTime != -1 && currentTime >= startTime;
	}


	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
	
	public void calculateWaitingTime(int currentTime) {
        this.waitingTime += currentTime;
    }
	
	
	
}
