package models;

public class Risk {
	private int id;
	private String name;
	private RiskExposure riskExposure;
	//private RiskType riskType;
	
	public Risk()
	{
		id				= 0;
		name			= "-";
		riskExposure = new RiskExposure();
	}
	
	public Risk(int id, String name, RiskExposure riskExposure) {
		super();
		this.id = id;
		this.name = name;
		this.riskExposure = riskExposure;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RiskExposure getRiskExposure() {
		return riskExposure;
	}
	public void setRiskExposure(RiskExposure riskExposure) {
		this.riskExposure = riskExposure;
	}

	public int getId() {
		return id;
	}

}
