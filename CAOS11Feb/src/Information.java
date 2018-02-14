public class Information
{
	private int arrivalTime;
	private int waitingTime;
	private int burstTime;
	private int turnaboutTime;
	private int finishTime;
	private int priority;
	private int indexNo;
	private int startTime;
	private int location;
	private int size;

	public Information(int aTime, int bTime, int p, int i, int s)
	{
		arrivalTime = aTime;
		burstTime = bTime;
		priority = p;
		indexNo = i;
		size = s;
		
	}
	public Information(int aTime, int i,  int s)
	{
		arrivalTime = aTime;
		indexNo = i;
		size = s;
	}


	public int getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}

	public int getWaitingTime()
	{
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime)
	{
		this.waitingTime = waitingTime;
	}

	public int getBurstTime()
	{
		return burstTime;
	}

	public void setBurstTime(int burstTime)
	{
		this.burstTime = burstTime;
	}

	public int getTurnaboutTime()
	{
		return turnaboutTime;
	}

	public void setTurnaboutTime(int turnaboutTime)
	{
		this.turnaboutTime = turnaboutTime;
	}

	public int getFinishTime()
	{
		return finishTime;
	}

	public void setFinishTime(int finishTime)
	{
		this.finishTime = finishTime;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public int getIndexNo()
	{
		return indexNo;
	}

	public void setIndexNo(int indexNo)
	{
		this.indexNo = indexNo;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	public int getLocation()
	{
		return location;
	}

	public void setLocation(int location)
	{
		this.location = location;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}


}
