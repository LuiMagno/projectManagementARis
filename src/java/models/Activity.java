package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Activity
{
	private int id;
	private String label;
	private int estimatedTime, currentTime;
	private float estimatedCost, currentCost;
	private int tEarlyStart, tEarlyFinish, tLateStart, tLateFinish, GAP;
	private int realTime;
	private int timeStopped;
	private ArrayList<Integer> instants = new ArrayList<Integer>();
	
	private Activity[] predecessorsActivities;
	private Activity[] successorsActivities;
	
	public Activity()
	{
		id				= 0;
		label			= "-";
		estimatedTime	= 0;
		realTime = 0;
		currentTime		= 0;
		timeStopped = 0;
		estimatedCost	= 0.0f;
		currentCost		= 0.0f;
		tEarlyStart 	= 0;
		tEarlyFinish 	= 0;
		tLateStart 		= 0;
		tLateFinish 	= 0;
		GAP				= 0;
	}
	
	public Activity(int id, String label, int estimatedTime, float estimatedCost)
	{
		this.id				= id;
		this.label 			= label;
		this.estimatedTime 	= estimatedTime;
		this.realTime 		= estimatedTime;
		this.estimatedCost 	= estimatedCost;
		this.timeStopped 	= 0;
	}
	
	/**********************************************
					METODOS INTERNOS
	 **********************************************/
	public boolean isRunning(int instant)
	{
		return instants.contains(instant);
	}
	
	public boolean isCriticalPath()
	{
		boolean flag = false;
		if(GAP == 0)
			flag = true;
		
		return flag;
	}
	
	public void compute_Tes_Tef()
	{	
		if(predecessorsActivities.length == 0)
			tEarlyStart = 0;
		else
			tEarlyStart = maxTefPredecessors(predecessorsActivities);
		
		tEarlyFinish = tEarlyStart + estimatedTime;
	}

	public void compute_Tlf_Tls()
	{	
		if(successorsActivities.length == 0)
			tLateFinish = tEarlyFinish; 
		else
			tLateFinish = minTlsSuccessors(successorsActivities);
		
		tLateStart = tLateFinish - estimatedTime;
	}
	
	public void compute_GAP()
	{	
		GAP = tLateFinish - tEarlyFinish;
	}
	
	public int maxTefPredecessors(Activity[] predecessors)
	{
		int maxTef = predecessors[0].tEarlyFinish;
		
		if(predecessors.length > 1)
		{
			for(int i = 1; i < predecessors.length; i++)
			{
				if(maxTef < predecessors[i].tEarlyFinish)
					maxTef = predecessors[i].tEarlyFinish;
			}
		}
		return maxTef;
	}

	public int minTlsSuccessors(Activity[] successors)
	{
		int minTls = successors[0].tLateStart;
		
		if(successors.length > 1)
		{
			for(int i = 1; i < successors.length; i++)
			{
				if(successors[i].tLateStart < minTls)
					minTls = successors[i].tLateStart;
			}
		}
		return minTls;
	}
	
	private int[] range(int start, int stop)
	{
	   int[] result = new int[stop - start];

	   for(int i = 0; i < (stop - start); i++)
	      result[i] = (start + i);

	   return result;
	}
	
	public void orderDecreasingGAP(Activity[] activs)
	{
		for (int i = 0; i < activs.length-1; i++)
		{
			boolean isOrderly = true;
			for (int j = 0; j < activs.length-1-i; j++)
			{
				if( activs[j].getGAP() < activs[j+1].getGAP() )
				{
					Activity aux = activs[j];
					activs[j] = activs[j+1];
					activs[j+1] = aux;
					isOrderly = false;
				}
			}
			if(isOrderly)
				break;
		}
	}

	/**********************************************
						GETERS
	 **********************************************/
	
	public int getId()
	{
		return id;
	}

	public String getLabel()
	{
		return label;
	}

	public int getEstimatedTime()
	{
		return estimatedTime;
	}
	
	public int getCurrentTime()
	{
		return currentTime;
	}

	public float getEstimatedCost()
	{
		return estimatedCost;
	}
	
	public float getCurrentCost()
	{
		return currentCost;
	}
	
	public int getTearlyStart()
	{
		return tEarlyStart;
	}

	public int getTearlyFinish()
	{
		return tEarlyFinish;
	}

	public int getTlateStart()
	{
		return tLateStart;
	}

	public int getTlateFinish()
	{
		return tLateFinish;
	}

	public int getGAP()
	{
		return GAP;
	}
	
	public Activity[] getPredecessorsActivities()
	{
		return predecessorsActivities;
	}

	public Activity[] getSuccessorsActivities()
	{
		return successorsActivities;
	}
	
	public Activity[] getSuccessorsActivitiesOutCP()
	{
		List<Activity> succOutCP = new LinkedList<Activity>();
		if(successorsActivities.length != 0)
		{
			succOutCP.clear();
			for(int i = 0; i < successorsActivities.length; i++)
			{
				Activity a = successorsActivities[i];
				if(a.getGAP() != 0)
					succOutCP.add(a);
			}
		}
		orderDecreasingGAP(succOutCP.toArray(new Activity[]{}));
		return succOutCP.toArray(new Activity[]{});
	}

	public int getInstant(int instantProject)
	{
		/* 
		 * Retorna o instante no qual a tarefa se encontra em 
		 * relação o seu total, partindo do instante atual do projeto.
		 */
		int init = instants.get(0);
		int ends = instants.get(instants.size()-1);
		if (instantProject >= init && instantProject <= ends){
			return instantProject - init;
		}
		return 0;
	}
	
	public int getRealTime(){
		return realTime;
	}
	
	public int getTimeStopped(){
		return timeStopped;
	}
	
	public ArrayList<Integer> getInstants()
	{
		return instants;
	}
	
	/**********************************************
						SETERS
	 **********************************************/
	
	public void setInstants(int instantInitial, int instantFinal)
	{
		int size = (instantFinal - instantInitial);
		int [] instants = new int[size];
		instants = range(instantInitial, instantFinal);
		
		for (int i = 0; i < instants.length; i++){
			this.instants.add(instants[i]);			
		}
	}
	
	public void setPredecessorsAndSuccessors(Activity[] preActivities, Activity[] succActivities)
	{
		predecessorsActivities = preActivities;
		successorsActivities = succActivities;
	}
	
	public void setCurrentTime(int currentTime)
	{
		this.currentTime = currentTime;
		if (currentTime > realTime && timeStopped > 0)  
			addRealTime(1);
	}
	
	public void setCurrentCost(float currentCost)
	{
		this.currentCost = currentCost;
	}
	
	public void setGAP(int GAP)
	{
		this.GAP = GAP;
	}
	/**********************************************
									MÉTODOS    PÚBLICOS
	 **********************************************/
	
	public void addRealTime(int times){
		realTime += times;
		timeStopped += times;
	}
	
	public void removeLastInstant(){
		instants.remove(instants.size()-1);
	}
	
	public void addOneInstant(){
		instants.add(instants.get(instants.size()-1) + 1);             //Adiciona na lista de instantes o momento seguinte de instante do projeto 
	}
	
	public int getStartInstant(){
		return instants.get(0);																	//retorna o primento instante
	}
	
	public int getEndInstant(){
		return instants.get(instants.size()-1);																	//retorna o último instante
	}
		
}
