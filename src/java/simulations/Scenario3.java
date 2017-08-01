package simulations;

import jason.stdlib.difference;

import java.math.BigDecimal;
import java.math.RoundingMode;

import models.Activity;
import models.Project;

/*
 *	Cenario 3 : atrasado no orçamento
 *
 *	A atividade 'B' vai atrasar o orçamento em 50% do que deveria estar a partir do último terço do cronograma  
 */

public class Scenario3 extends Simulate
{
	public Scenario3(Project p, String name)
	{
		super(p, name);
	}

	@Override
	public void prepare()
	{	
		// Atividades do Projeto.
		Activity A, B;
				
		A = new Activity(1, "A", 20, 50.0f);
		B = new Activity(2, "B", 15, 100.0f);
		
		// configuracao temporal
		A.setInstants(0, 20);
		B.setInstants(15, 30);
				
		// add as atividades no arrayList de atividades.
		p.clearActivities();
		p.addActivity(A);
		p.addActivity(B);
	}

	@Override
	public void simulationScenario(int[] idsActivitiesRunning, int instant)
	{
		double rateEvolutionTime, rateEvolutionCost;
		
		for (int id : idsActivitiesRunning)
		{	
			Activity a = p.getActivityById(id);
			int currentTime = a.getCurrentTime();
			float currentCost = a.getCurrentCost();
						
			rateEvolutionCost = ((a.getInstant(instant)+1.0) /  a.getEstimatedTime()) ;		
			rateEvolutionTime =   ( ( ( instant+1 * 100.0) / a.getEstimatedTime() ) / 100.0);				
			
			BigDecimal ret = new BigDecimal(rateEvolutionTime).setScale(10, RoundingMode.DOWN);
			BigDecimal rec = new BigDecimal(rateEvolutionCost).setScale(10, RoundingMode.DOWN);
			
			currentTime += ret.floatValue() *  a.getEstimatedTime();			
			
			//usar as taxas ideais até a metade do cronograma para a atividade B (id == 2)
			if  ((2*(p.getDuration()/3.0) > instant) || (id != 2)){				
				currentCost = rec.floatValue() * a.getEstimatedCost();
			}
			else{   			
				float difference =   ((rec.floatValue() * a.getEstimatedCost()) - currentCost) * 0.5f;
				currentCost = (rec.floatValue() * a.getEstimatedCost()) - difference;
			}	
			
			a.setCurrentTime(currentTime);
			a.setCurrentCost(currentCost);
		}		
	}	
}
