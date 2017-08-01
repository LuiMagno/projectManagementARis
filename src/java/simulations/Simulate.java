package simulations;

import models.Project;

public abstract class Simulate
{	
	protected Project p;	
	private String name;
	
	public Simulate(Project p, String name){
		this.p = p;
		this.name = name;
	}	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	/*
	 *	Prepara as atividades do projeto antes de simular
	 *	o cenario especificado. Define os dados de cada
	 *	atividade, como id, tempo e custo, assim como a
	 *	configuracao temporal.
	 */
	public abstract void prepare();
	
	/*
	 *	Cada atividade em execucao tem uma taxa de evolucao
	 *	relacionada a tempo e a custo. Essa taxa define se
	 *	a atividade vai atrasar ou extrapolar em cronograma
	 *	e/ou custo. 
	 */
	public abstract void simulationScenario(int [] idsActivitiesRunning , int instant);
}
