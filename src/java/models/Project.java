package models;

import java.util.ArrayList;

public class Project
{	
	private int id;
	private int duration;
	private int realDuration;
	private float budget;
	private int instant;
	
	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private ArrayList<Risk> risks = new ArrayList<Risk>();
	
	public Project()
	{
		id		 	= 0;
		duration 	= 0;
		budget		= 0.0f;
		instant 	= 0;
	}
	
	public Project(int id, int duration, float budget)
	{
		this.id			= id;
		this.duration	= duration;
		this.budget		= budget;
		this.realDuration = this.duration;
	}
	
	/*************************************************
	  					ACTIVITIES
	 **************************************************/
	public void clearActivities()
	{
		activities.clear();
	}
	public void addActivity(Activity a)
	{
		activities.add(a);
	}
	public void addRealDuration(int time)
	{
		this.realDuration += time;
	}
	public boolean existsTaskNotFinalized(int instant)
	{           //verifica se todas as tarefas acabaram
		for (Activity a: activities)
			if (a.getRealTime() > instant)
				return true;
		return false;
	}
	
	public int getNumActivitiesRunning()
	{
		int cnt = 0;
		for (int i = 0; i < activities.size(); i++)
		{
			if(activities.get(i).isRunning(instant))
				cnt++;
		}
		return cnt;
	}

	public int[] getIdsActivitiesRunning()
	{
		int numActivitiesRunning = getNumActivitiesRunning();
		int [] ids = new int[numActivitiesRunning];
		int cnt = 0;
		
		for (Activity a : activities)
		{
			if(a.isRunning(instant))
				ids[cnt++] = a.getId();
		}
		return ids;
	}
	
	public Activity getActivityById(int id)
	{	
		Activity activity = new Activity();
		
		for (int i = 0; i < activities.size(); i++)
		{
			if(activities.get(i).getId() == id)
				activity = activities.get(i);
		}
		return activity;
	}
	
	public Object[] getActivitiesByInstant()
	{
		ArrayList<Activity> activs = new ArrayList<Activity>();
		for (Activity a : activities)
		{
			if(a.isRunning(instant))
				activs.add(a);
		}
		return activs.toArray(new Activity[]{});
	}
	
	/*************************************************
		RISKS
	 **************************************************/
	public void addRisk(Risk a)
	{
		risks.add(a);
	}
	public void clearRisk()
	{
		risks.clear();
	}
	public Risk getRiskById(int id)
	{	
		Risk risk =  new Risk();
		
		for (int i = 0; i < risks.size(); i++)
		{
			if(risks.get(i).getId() == id)
				risk = risks.get(i);
		}
		return risk;
	}
	
	
	/*************************************************
							GETS
	 **************************************************/
	public int getId()
	{
		return id;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public int getRealDuration()
	{
		return realDuration;
	}

	public float getBudget()
	{
		return budget;
	}
	
	public int getInstant()
	{
		return instant;
	}	
	
	public ArrayList<Activity> getActivities()
	{
		return activities;
	}
	
	public ArrayList<Risk> getRisks()
	{
		return risks;
	}

	/**********************************************
						SETS
	 **********************************************/

	public void setInstant(int instant)
	{
		this.instant = instant;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	
	public void setRisks(ArrayList<Risk> risks) {
		this.risks = risks;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}
}