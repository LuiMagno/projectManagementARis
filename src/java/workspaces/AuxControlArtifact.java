// CArtAgO artifact code for project projectManagement

package workspaces;

import java.util.LinkedList;
import java.util.List;

import models.Activity;
import models.Project;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class AuxControlArtifact extends Artifact
{	
	Project proj;
	Activity activ;
	List<Activity> activitiesOutCP = new LinkedList<Activity>();
	int idParallel;
	
	void init()
	{
		defineObsProperty("activityOutCP", activ);
		defineObsProperty("listActivitiesOutCP", activitiesOutCP);
	}
	
	@OPERATION
	void setProject(Project project)
	{
		proj = project;
	}
	
	@OPERATION
	void sendActivity(Activity a)
	{
		activ = a;
		activitiesOutCP.add(a);
		
		getObsProperty("activityOutCP").updateValue(a);
		getObsProperty("listActivitiesOutCP").updateValue(activitiesOutCP);
		
		signal("statusOutCP", "newActivOutCP");
	}
	
	@OPERATION
	void setParallel(int idCurrent, int idParallel)
	{
    	if(idCurrent != idParallel)
    		this.idParallel = idParallel;
	}
	
	@OPERATION
	void getParallel(OpFeedbackParam<Integer> idParallel)
	{
		idParallel.set(this.idParallel);
	}
	
	@OPERATION
	void getSuccessorsActivitiesOutCP(int id, OpFeedbackParam<Activity[]> succOutCP)
	{
		Activity a = proj.getActivityById(id);
		succOutCP.set(a.getSuccessorsActivitiesOutCP());
	}
	
	@OPERATION
	void getActivity(int id, OpFeedbackParam<Activity> activ)
	{
		Activity a = proj.getActivityById(id);
		activ.set(a);
	}
	
	@OPERATION
	void union(Object[] v1, Object[] v2, OpFeedbackParam<Activity[]> union)
	{
		List<Activity> u = new LinkedList<Activity>();
		boolean isNotContained = true;
		
		for (int k = 0; k < v1.length; k++)
			u.add((Activity)v1[k]);

		for (int i = 0; i < v2.length; i++)
		{
			Activity aux = (Activity)v2[i];
			for (int j = 0; j < v1.length; j++)
			{ 
				if(aux.getId() == u.get(j).getId())
				{
					isNotContained = false;
					break;
				}
			}
			if(isNotContained)
				u.add(aux);
			isNotContained = true;
		}
		
		union.set(u.toArray(new Activity[]{}));
	}
	
	@OPERATION
	void clearList()
	{
		activitiesOutCP.clear();
		getObsProperty("listActivitiesOutCP").updateValue(activitiesOutCP);
	}
}