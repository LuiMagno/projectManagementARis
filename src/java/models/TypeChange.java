package models;

public class TypeChange {	
	
	private static final int ADD_COST = 1;	
	private static final int ADD_TIME = 2;
	
	private static final int REM_COST = 3; 	
	private static final int REM_TIME = 4;
		
	
	public static int getAddCost() {
		return ADD_COST;
	}



	public static int getAddTime() {
		return ADD_TIME;
	}



	public static int getRemCost() {
		return REM_COST;
	}



	public static int getRemTime() {
		return REM_TIME;
	}

	public static String getDescription(int tc){
		switch (tc){
		case ADD_COST: return "Solicitação de acréscimo no custo";
		case ADD_TIME: return "Solicitação de acréscimo no tempo";
		case REM_COST: return "Solicitação de decréscimo no custo";
		case REM_TIME: return "Solicitação de decréscimo no tempo";
		default: return null;
		}
	}	
	
}
