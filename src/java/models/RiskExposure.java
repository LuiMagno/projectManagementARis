package models;

public class RiskExposure {
	
	private double totalRiskExposure;
	private double costP; // cost probability
	private int costI;  // cost impact
	private double timeP; // time probability
	private int timeI; // time impact
	private double scopeP; // scope probability
	private int scopeI;  // scope impact
	
	
	public RiskExposure()
	{
		totalRiskExposure = 0;
		costP = 0;
		costI = 0;
		timeP = 0;
		timeI = 0;
		scopeP = 0;
		scopeI = 0;
		
	}
	
	public RiskExposure(double costP, int costI, double timeP, int timeI, double scopeP,int scopeI) {
		super();
		this.costP = costP;
		this.costI = costI;
		this.timeP = timeP;
		this.timeI = timeI;
		this.scopeP = scopeP;
		this.scopeI = scopeI;
	}

	public double getTotalRiskExposure() {
		return totalRiskExposure;
	}


	public void setTotalRiskExposure(double totalRiskExposure) {
		this.totalRiskExposure = totalRiskExposure;
	}


	public double getCostP() {
		return costP;
	}


	public void setCostP(double costP) {
		this.costP = costP;
	}


	public int getCostI() {
		return costI;
	}


	public void setCostI(int costI) {
		this.costI = costI;
	}


	public double getTimeP() {
		return timeP;
	}


	public void setTimeP(double timeP) {
		this.timeP = timeP;
	}


	public int getTimeI() {
		return timeI;
	}


	public void setTimeI(int timeI) {
		this.timeI = timeI;
	}


	public double getScopeP() {
		return scopeP;
	}


	public void setScopeP(double scopeP) {
		this.scopeP = scopeP;
	}


	public int getScopeI() {
		return scopeI;
	}


	public void setScopeI(int scopeI) {
		this.scopeI = scopeI;
	}
	
		
}
