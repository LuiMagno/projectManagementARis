// CArtAgO artifact code for project projectManagement

package workspaces;

import java.util.LinkedList;
import java.util.List;

import cartago.*;
import models.Project;
import models.Risk;

public class EnvironmentRiskControl extends Artifact {
	
	private List<Risk> risks = new LinkedList<Risk>();
	private Project project;
	
	void init(int initialValue) {
		defineObsProperty("risks1", risks);
	}
	
	@OPERATION
	void setProject(Project p) {
		this.project = p;
	}
}

