// CArtAgO artifact code for project projectManagement

package workspaces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import models.Activity;
import models.Change;
import models.Project;
import models.StateOfChange;
import cartago.Artifact;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;

public class EnvironmentChangeRequest extends Artifact {
	
	private List<Change> requests = new LinkedList<Change>();
	private Project project;
	private int projectInstant = 0;
	
	void init() {
		defineObsProperty("requests", requests);
		defineObsProperty("actualRequest", 0);
	}
	
	@OPERATION
	void requestChange(Change c){
		requests.add(c);
		getObsProperty("requests").updateValue(requests);
		getObsProperty("actualRequest").updateValue(c);
		signal("status", "newRequest");
	}
	
	@OPERATION
	void setProject(Project p) {
		this.project = p;
	}
	
	@OPERATION
	void setActivities(ArrayList<Activity> acts, int projectInstant) {
		this.project.setActivities(acts);
		this.projectInstant = projectInstant;
		execInternalOp("updateStates");
	}

	@INTERNAL_OPERATION
	private void updateStates(){
		for (Change c: requests){
			if (c.getActivity().getEndInstant() <= projectInstant && c.getState() == StateOfChange.REQUESTED)
				c.setState(StateOfChange.OBSOLETE);
		}
	}
}