package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Change {
	int change_id;
	String change_title;
	int change_instant;
	Activity activity; 
	List<Map<Integer, Double>> typeRequests;          //Lista com os tipos de solicitacoes para um mesmo instante
	int state;
	
	public Change(){
	}
	
	public Change(int change_id, String change_title, int change_instant, Activity activity, int state) {		
		super();
		this.change_id = change_id;
		this.change_title = change_title;
		this.change_instant = change_instant;
		this.activity = activity;
		this.state = state;
		typeRequests = new LinkedList<Map<Integer, Double>>();
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getChange_title() {
		return change_title;
	}

	public void setChange_title(String change_title) {
		this.change_title = change_title;
	}
	
	public int getChange_instant() {
		return change_instant;
	}

	public void setChange_instant(int change_instant) {
		this.change_instant = change_instant;
	}

	public int getState() {
		return state;
	}

	public void setState (int state) {
		this.state = state;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public void addRequest(int type, double percentValue){
		Map<Integer, Double> r = new HashMap<Integer, Double>();
		r.put(type, percentValue);
		typeRequests.add(r);
	}

	public List<Map<Integer, Double>> getTypeRequests() {
		return typeRequests;
	}
	
	public Double getRequestValue(int type){
		for (Map <Integer, Double> p: typeRequests)
			if (p.containsKey(type))
				return p.get(new Integer(type));
		return 0.0;
	}
	
	public String getDescriptionState(){
		return StateOfChange.getDescription(state);
	}

}
