import java.util.ArrayList;
import java.util.Arrays;

public class Controller
{
	private ArrayList<Information> info;	// ArrayList to contain the jobs
	private int timeQuantum;																// Time quantum only for roundRobin
	private int noOfProcesses;																// To know how many processes are there
	private int partition;																	// To know how many partition the user want to user
	private String memoryAllocation;
	private String allocation;																// To know what type of allocation method is used
	private String algorithm;																// To know what algorithm is used
	public boolean hasMemory=false;
	public final int[] MEMORYMAP = new int[] { 350,275 , 85, 120, 500 };					// Used as a backup for spacesLeft
	private int[] spacesLeft;																// Used to alter to see how many spaces are left
	private ArrayList<Integer> time;
	private ArrayList<Integer> processOrder;

	public void generate(int noOfProcess, int aTime, int bTime,String algorithm){ 			// Get all the informations from the GUI
		System.out.println("noOfProcess = "+noOfProcess+" aTime = "+aTime+" bTime = "+bTime+" algorithm = "+algorithm);
		noOfProcesses = noOfProcess;
		info = new ArrayList<Information>();												// To create new Information ArrayList for containing the details and to clear if there is any previous details
		for (int x = 0; x < noOfProcesses; x++) {
			info.add(new Information(generateRand(aTime, 0), 								// Generate random number for arrival
					generateRand(bTime, 1), 												// Generate random number for burst time
					generateRand(noOfProcesses, 0),											// Generate random number for priority
					info.size() + 1,														// Set index number
					generateRand(120, 80)));
		}
		this.algorithm = algorithm;															// To assign algorithm to global variable
		ArrangeByAT();																		// Arrange all the jobs by arrival time

		if (algorithm.equals("FCFS"))
			FCFS();
		if (algorithm.equals("Round Robin"))
			RoundRobin();
		if (algorithm.equals("SJF (Premptive)"))
			SJF();
		if (algorithm.equals("SJF (Non-Premptive)"))
			SJFNon();
		if (algorithm.equals("Priority"))
			Priority();
		
		/*if(hasMemory)
		{
		this.memoryAllocation = memoryAllocation;
		if (memoryAllocation.equals("First Fit"))
			FirstFit();
		if (memoryAllocation.equals("Best Fit"))
			BestFit();
		if (memoryAllocation.equals("Worst Fit"))
			WorstFit();
		}*/
	

	}
	
	public void generateMem(int noOfProcess,String algorithm){ 
		System.out.println("noOfProcess = "+noOfProcess+" algorithm = "+algorithm);
		noOfProcesses = noOfProcess;
		info = new ArrayList<Information>();												// To create new Information ArrayList for containing the details and to clear if there is any previous details
		for (int x = 0; x < noOfProcesses; x++) {
			info.add(new Information(generateRand(1, 0), 								// Generate random number for arrival
					generateRand(1, 1), 												// Generate random number for burst time
					generateRand(noOfProcesses, 0),											// Generate random number for priority
					info.size() + 1,														// Set index number
					generateRand(120, 80)));
		}
		//this.algorithm = algorithm;															// To assign algorithm to global variable
		ArrangeByAT();		
		
		if (algorithm.equals("First Fit"))
			FirstFit();
		if (algorithm.equals("Best Fit"))
			BestFit();
		if (algorithm.equals("Worst Fit"))
			WorstFit();
	
	}
	
	public void ArrangeByAT()//To arrange all the jobs by arrival time
	{
		ArrayList<Information> doneInfo = new ArrayList<Information>();
		int min;

		while (info.size() > 0) {															//Check if there are still any processes left
			min = 0;																		//to make sure it start with the first process left in the arrayList
			for (int x = 0; x < info.size() - 1; x++) {
				if (info.get(min).getArrivalTime() > info.get(x + 1)						 
						.getArrivalTime()) {
					min = x + 1;															//if the arrival time is smaller, min will be set to its index
				}
			}
			doneInfo.add(info.get(min));													//add the Information to doneInfo array then remove it to ensure there is no duplication
			info.remove(min);
		}
		info = doneInfo;
	}
	
	////////////////////////////////////////////////////
	/////////ALGORITHM///////////////////////
	//////////////////////////////////////////////////
	
	public void FCFS()//To calculate finish, waiting and turnaround time for FCFS
	{
		System.gc();
		time = new ArrayList<Integer>();													
		processOrder = new ArrayList<Integer>();
		info.get(0).setStartTime(info.get(0).getArrivalTime());											//set the time the process "0" start executing to its arrival time
		processOrder.add(info.get(0).getIndexNo());														//first job index
		time.add(info.get(0).getArrivalTime());
		info.get(0).setFinishTime(info.get(0).getStartTime()+info.get(0).getBurstTime());				//set the finish time for process "0" 
		info.get(0).setTurnaboutTime(info.get(0).getFinishTime()-info.get(0).getArrivalTime());
		info.get(0).setWaitingTime(info.get(0).getTurnaboutTime()-info.get(0).getBurstTime());
		time.add(info.get(0).getStartTime()+info.get(0).getBurstTime());
		for (int x = 1; x < info.size(); x++) {
			if(info.get(x-1).getFinishTime()<info.get(x).getArrivalTime())									//Check if the arrival time of the job is before or after the previous job finish time just in clase there are any idle state
				{
				processOrder.add(0);
				time.add(info.get(x).getArrivalTime());
				info.get(x).setStartTime(info.get(x).getArrivalTime());					//if yes, the start time of process "x" will be its arrival time
				
				}					
			else
				info.get(x).setStartTime(info.get(x-1).getFinishTime());					//if no, the start time of the process will be the previous job's finish time
			info.get(x).setFinishTime(info.get(x).getStartTime()+info.get(x).getBurstTime());
			info.get(x).setTurnaboutTime(info.get(x).getFinishTime()-info.get(x).getArrivalTime());
			info.get(x).setWaitingTime(info.get(x).getTurnaboutTime()-info.get(x).getBurstTime());
			time.add(info.get(x).getStartTime()+info.get(x).getBurstTime());
			processOrder.add(info.get(x).getIndexNo());
			
		}
	}
	
	public void RoundRobin()//To calculate finish, waiting and turnaround time for RoundRobin
	{
		System.gc();
		time = new ArrayList<Integer>();
		processOrder = new ArrayList<Integer>();
		ArrayList<Integer> doneInfo = this.getBurstTime();	//to clone info so that anything done to doneInfo will not affect info
		int finishTime = info.get(0).getArrivalTime();									//to set finishTime to the time in which the job first start
		int x = 0;
		time.add(info.get(0).getArrivalTime());
		while (doneInfo.size() > 0) {															//check if there are still any burst time left
			if (x >= doneInfo.size())														//x is used to control the loop, goes from 0 to the last index continuously
				x = 0;
			
			if (doneInfo.get(x) > timeQuantum) {								//check if process with index "x" still have one or more cycle left
				finishTime += timeQuantum;													//add to finish time to know where have the scheduler reached
				doneInfo.set(x, doneInfo.get(x) - timeQuantum);	//minus from burst time to know how many burst time is remaining after that
				time.add(finishTime);
				processOrder.add(info.get(x).getIndexNo());
			}

			else if (doneInfo.get(x) != 0) {									//to check if process have completed
				finishTime += doneInfo.get(x);							//add the remaining burst time to finishTime
				doneInfo.set(x, 0);											//to tell that the process have completed
				info.get(x).setFinishTime(finishTime);	//set finish time

				info.get(x).setTurnaboutTime(info.get(x).getFinishTime()-info.get(x).getArrivalTime());
				info.get(x).setWaitingTime(info.get(x).getTurnaboutTime()-info.get(x).getBurstTime());
				time.add(finishTime);
				processOrder.add(info.get(x).getIndexNo());
				System.out.println(info.get(x).getIndexNo()+"with burstTime"+info.get(x).getBurstTime());
				doneInfo.remove(x);
			}
			x++;																			
		}

	}
	
	public void SJF()//To calculate finish, waiting and turnaround time for SJF Preemptive
	{
		System.gc();
		time = new ArrayList<Integer>();
		processOrder = new ArrayList<Integer>();
		ArrayList<Information> doneInfo = (ArrayList<Information>)info.clone();
		ArrayList<Information> waitingList = new ArrayList<Information>();
		int excecuting=0;
		int tempFinishTime= doneInfo.get(0).getArrivalTime()+doneInfo.get(0).getBurstTime();
		boolean isInterrupted=false;
		time.add(info.get(0).getArrivalTime());
		processOrder.add(0);
		info.get(0).setStartTime(info.get(0).getArrivalTime());
		info.get(0).setFinishTime(info.get(0).getArrivalTime()+info.get(0).getBurstTime());
		for (int x = 0; x < info.size() - 1; x++) {
			isInterrupted = false;
			while (info.size()!=0)
			if(tempFinishTime>info.get(x).getArrivalTime())
			{
				if(info.get(excecuting).getPriority()>info.get(x).getPriority())
				{
					isInterrupted = true;
					waitingList.get(excecuting).setBurstTime(info.get(x).getArrivalTime()-doneInfo.get(excecuting).getStartTime());
					excecuting=x;
					info.get(x).setStartTime(info.get(x).getArrivalTime());
					tempFinishTime=doneInfo.get(0).getArrivalTime()+doneInfo.get(0).getBurstTime();
				}
			}
			
				waitingList.add(info.get(x));
				doneInfo.remove(x);
		}
	}
	
	
	public void Priority()//To calculate finish, waiting and turnaround time for Priority
	{
		System.gc();
		time = new ArrayList<Integer>();
		processOrder = new ArrayList<Integer>();
		ArrayList<Information> doneInfo = new ArrayList<Information>();
		ArrayList<Information> waitingList = new ArrayList<Information>();
		int currentTime =info.get(0).getArrivalTime();
		while(info.size()!=0)
		{
		for(int x=0;x<info.size();x++)
		{
			if(info.get(x).getArrivalTime()<=currentTime)
			{
				waitingList.add(info.get(x));
				info.remove(x);
			}
			if(waitingList.size()==0)
			{	
				time.add(doneInfo.get(doneInfo.size()-1).getFinishTime());
				processOrder.add(-1);
				waitingList.add(info.get(0));
				info.remove(0);
			}
			else{								//Check if there are still any processes left
				int min = 0;																		//to make sure it start with the first process left in the arrayList
				for (int y = 0; y > waitingList.size() - 1; y++) {
					if (waitingList.get(min).getPriority() > waitingList.get(y + 1)						 
							.getPriority()) {
						min = y + 1;														//if the arrival time is smaller, min will be set to its index
					}
				}
				waitingList.get(min).setStartTime(currentTime);
				processOrder.add(waitingList.get(min).getIndexNo());
				currentTime+=waitingList.get(min).getBurstTime();
				waitingList.get(min).setFinishTime(currentTime);
				time.add(waitingList.get(min).getArrivalTime());
				doneInfo.add(waitingList.get(min));													//add the Information to doneInfo array then remove it to ensure there is no duplication
				waitingList.remove(min);
				
			}
			}
		}	
		info=doneInfo;
		
			
	
	}
	
	public void SJFNon()//To calculate finish, waiting and turnaround time for SJF Non-Preemptive
	{
		System.gc();
		time = new ArrayList<Integer>();
		processOrder = new ArrayList<Integer>();
		ArrayList<Information> doneInfo = new ArrayList<Information>();
		ArrayList<Information> waitingList = new ArrayList<Information>();
		int finishTime = info.get(0).getArrivalTime();
		time.add(info.get(0).getArrivalTime());
		while (waitingList.size() > 0 || info.size() > 0) {

			for (int x = 0; x < info.size() - 1; x++) {
				//check if there are still any job arrives before or at the same time which the previous job finishes
				if (finishTime >= info.get(x).getArrivalTime()) {	
				//if there is, add it into the waiting list
					waitingList.add(info.get(x));
					//remove the job from info to prevent duplication
					info.remove(x);
				}
			}
			if(waitingList.size()==0)
			{
			if(info.get(0).getArrivalTime()>finishTime)
			{
			processOrder.add(0);
			time.add(info.get(0).getArrivalTime());	
			}
			
			waitingList.add(info.get(0));
			info.remove(0);
			
			}
			int min= 0;
			for (int x = 0; x < waitingList.size() - 1; x++) {	//for-loop to get the shortest burst Time
				if (waitingList.get(min).getBurstTime() > waitingList
						.get(x + 1).getBurstTime()) {
					min = x + 1;
				}
			}
			if (finishTime < waitingList.get(min).getArrivalTime())
				finishTime = waitingList.get(min).getArrivalTime();
			finishTime += waitingList.get(min).getBurstTime();

			time.add(finishTime);
			processOrder.add(waitingList.get(min).getIndexNo());
			waitingList.get(min).setFinishTime(finishTime);
			waitingList.get(min).setTurnaboutTime(waitingList.get(min).getFinishTime()-waitingList.get(min).getArrivalTime());
			waitingList.get(min).setWaitingTime(waitingList.get(min).getTurnaboutTime()-waitingList.get(min).getBurstTime());
			doneInfo.add(waitingList.get(min));
			waitingList.remove(min);
		}
		info = doneInfo;
	}
	
	////////////////////////////////////////////////////
	/////////ALGORITHM///////////////////////
	//////////////////////////////////////////////////
	
	public ArrayList<Integer> getIndexOfProcesses()	//Return the index of the processes	
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getIndexNo());
		}
		return toBeReturned;
	}

	public ArrayList<Integer> getArrivalTime()//Return the arrival Time of the process
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getArrivalTime());
		}
		return toBeReturned;
	}

	public ArrayList<Integer> getFinishTime()//Return the arrival Time of the process
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getFinishTime());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getPriority()//return the priority of the process
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getPriority());
		}
		return toBeReturned;
	}

	public ArrayList<Integer> getBurstTime()//return the burstTime
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getBurstTime());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getWaitingTime()//return the burstTime
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getWaitingTime());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getTurnaroundTime()//return the turnaround time
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getTurnaboutTime());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getIndexByLocation(int location)//return the index by location
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			if(info.get(x).getLocation()==location)
			toBeReturned.add(info.get(x).getIndexNo());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getSize()//return the index by location
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			toBeReturned.add(info.get(x).getSize());
		}
		return toBeReturned;
	}
	
	public ArrayList<Integer> getSizeByLocation(int location)//return the size by location
	{
		ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
		for (int x = 0; x < info.size(); x++) {
			if(info.get(x).getLocation()==location)
			toBeReturned.add(info.get(x).getSize());
		}
		return toBeReturned;
	}

	public int noOfPartition()
	{
		return partition;
	}
	
	public void setPartition(int partition)//to only set the amount of partitions
	{
		this.partition = partition;
	}

	public void setTimeQuantum(int timeQuantum)//to set time quantum for round robin
	{
		this.timeQuantum = timeQuantum;
	}
	
	public void setMemoryAllocation(String mAll)//to set the memory allocation chosen
	{
		memoryAllocation = mAll;
	}
	
	public int generateRand(int max, int min)//generate a random number
	{
		return (int) (Math.random() * max) + min;
	}
	
	public int getNoOfProcesses()//return the total number of processes
	{
		return noOfProcesses;
	}
	
	public ArrayList<Integer> getTime()//return the time whenever there is any changes in the job
	{
		return time;
	}
	
	public ArrayList<Integer> getProcessOrder()//return the the order of how the process will be arranged in the gantt chart
	{
		
		return processOrder;
	}
	
	public void Memory(boolean have)//if the user selected memory management instead of cpu scheduling, have will be set to true
	{
		System.out.println("memory have been set to "+have);
		hasMemory = have;
	}
	
	public void FirstFit()//first fit memory allocation
	{
		spacesLeft = Arrays.copyOf(MEMORYMAP, partition);
		int location;
		for(int x=0;x<info.size();x++)
		{location =0;
			for(int y=0;y<partition;y++)
			{
				if(spacesLeft[y]>info.get(x).getSize())
				{
					location = y;
					break;
				}
			}
			if(spacesLeft[location]<info.get(x).getSize())
			{
				info.get(x).setLocation(-1);
				System.out.println("Process"+info.get(x).getIndexNo()+"is not added to any location");
			}
			else
			{
				info.get(x).setLocation(location);
				System.out.println("Process"+info.get(x).getIndexNo()+"is added to location"+location);
				spacesLeft[location]-=info.get(x).getSize();
				System.out.println("location" +location +"is left with "+spacesLeft[location]);
			}
		}
	}
	
	public void BestFit(){//best fit memory allocation

		spacesLeft = Arrays.copyOf(MEMORYMAP, partition);
		int location;
		for(int x=0;x<info.size();x++)
		{location =0;
			for(int y=0;y<partition;y++)
			{
				if(spacesLeft[location]>spacesLeft[y]&&spacesLeft[y]>info.get(x).getSize())
				{
					location = y;
				}
			}
			if(spacesLeft[location]<info.get(x).getSize())
			{
				info.get(x).setLocation(-1);
				System.out.println("Process"+info.get(x).getIndexNo()+"is not added to any location");
			}
			else
			{
				info.get(x).setLocation(location);
				System.out.println("Process"+info.get(x).getIndexNo()+"is added to location"+location);
				spacesLeft[location]-=info.get(x).getSize();
				System.out.println("location" +location +"is left with "+spacesLeft[location]);
			}
		}
	}
	
	public void WorstFit() //Worst fit memory allocation
	{
		spacesLeft = Arrays.copyOf(MEMORYMAP, partition);
		int location;
		for(int x=0;x<info.size();x++)
		{location =0;
			for(int y=0;y<partition;y++)
			{
				if(spacesLeft[location]<spacesLeft[y]&&spacesLeft[y]>info.get(x).getSize())
				{
					location = y;
				}
			}
			if(spacesLeft[location]<info.get(x).getSize())
			{
				info.get(x).setLocation(-1);
				System.out.println("Process"+info.get(x).getIndexNo()+"is not added to any location");
			}
			else
			{
				info.get(x).setLocation(location);
				System.out.println("Process"+info.get(x).getIndexNo()+"is added to location"+location);
				spacesLeft[location]-=info.get(x).getSize();
				System.out.println("location" +location +"is left with "+spacesLeft[location]);
			}
		}
	}

}
