package simulations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import models.Activity;
import models.Project;

/*
 *	Cenario 2 : atrasado no cronograma
 *
 * 	A atividade 'A' vai atrasar em 60% no cronograma (atrasado no cronograma) a partir da metade do projeto e,
 * 	consequentemente, extrapolar o orçamento (acima do orcamento). Isso também acarretará na extensão do prazo do projeto.
 */

public class Scenario2 extends Simulate
{
	public Scenario2(Project p, String name)
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
			
			rateEvolutionTime =   ( ( ( instant+1 * 100.0) / a.getEstimatedTime() ) / 100.0);
			rateEvolutionCost = ((a.getInstant(instant)+1.0) /  a.getEstimatedTime()) ;			
			
			BigDecimal ret = new BigDecimal(rateEvolutionTime).setScale(10, RoundingMode.DOWN);
			BigDecimal rec = new BigDecimal(rateEvolutionCost).setScale(10, RoundingMode.DOWN);
									
			currentCost = rec.floatValue() * a.getEstimatedCost();
			currentTime += ret.floatValue() *  a.getEstimatedTime();
			
			//Em uma dessas situações adiciona-se uma "tempo extra" na atividade, pois ela vai atrasar.
			double timeToStop =  a.getEstimatedTime() * 0.6;
			if (! ((id != 1) || (instant < p.getDuration()/2) || (instant >= (p.getDuration()/2) + timeToStop))){				
				a.setInstants(a.getRealTime(), a.getRealTime()+1);
				a.addRealTime(1);
			}
			a.setCurrentTime(currentTime);
			a.setCurrentCost(currentCost);
		}		
	}	
}
