package simulations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import models.Activity;
import models.Project;
import models.Risk;
import models.RiskExposure;

/*
 *	Cenario 1 : cenario ideal (sem atrasos ou extrapolos de cronograma e custo)
 *	
 *	Existem taxas de evolucao, relacionados a tempo e custo, ideais para cada atividade
 *	em execucao ate o ultimo instante do projeto. A partir dessas taxas, as atividades
 *	evoluem idealmente. Assim, os tempos e custos que as atividades consomem ate o fim
 *	do projeto sao exatamente os planejados, sem atrasos ou extrapolos.  
 */
public class Scenario1 extends Simulate
{	
	public Scenario1(Project p, String name)
	{
		super(p, name);
	}

	@Override
	public void prepare()
	{	
		// Atividades do Projeto.
		Activity A, B, C, D, E, F, G, H, I, J, K, L, M, N;
		// Risco
		Risk R1,R2,R3;
		
		A = new Activity(1, "A", 10, 700.0f);	B = new Activity(2, "B", 5, 350.0f);
		C = new Activity(3, "C", 5, 350.0f);	D = new Activity(4, "D", 15, 1050.0f);
		E = new Activity(5, "E", 25, 1750.0f);	F = new Activity(6, "F", 5, 350.0f);
		G = new Activity(7, "G", 5, 350.0f);	H = new Activity(8, "H", 10, 700.0f);
		I = new Activity(9, "I", 5, 350.0f);	J = new Activity(10, "J", 15, 1050.0f);
		K = new Activity(11, "K", 20, 1400.0f);	L = new Activity(12, "L", 10, 700.0f);
		M = new Activity(13, "M", 25, 1750.0f);	N = new Activity(14, "N", 5, 350.0f);
		
		// Antecessores e Sucessores de cada atividade.
		A.setPredecessorsAndSuccessors(new Activity[]{}, new Activity[]{C});
		B.setPredecessorsAndSuccessors(new Activity[]{}, new Activity[]{C, D});
		C.setPredecessorsAndSuccessors(new Activity[]{A, B}, new Activity[]{E, L});
		D.setPredecessorsAndSuccessors(new Activity[]{B}, new Activity[]{F});
		E.setPredecessorsAndSuccessors(new Activity[]{C}, new Activity[]{F});
		F.setPredecessorsAndSuccessors(new Activity[]{D, E}, new Activity[]{I, G, H});
		G.setPredecessorsAndSuccessors(new Activity[]{F}, new Activity[]{J});
		H.setPredecessorsAndSuccessors(new Activity[]{F}, new Activity[]{K});
		I.setPredecessorsAndSuccessors(new Activity[]{F}, new Activity[]{K});
		J.setPredecessorsAndSuccessors(new Activity[]{G}, new Activity[]{K});
		K.setPredecessorsAndSuccessors(new Activity[]{H, I, J}, new Activity[]{N});
		L.setPredecessorsAndSuccessors(new Activity[]{C}, new Activity[]{M});
		M.setPredecessorsAndSuccessors(new Activity[]{L}, new Activity[]{N});
		N.setPredecessorsAndSuccessors(new Activity[]{K, M}, new Activity[]{});
		
		// configuracao temporal
		A.setInstants(0, 10);	B.setInstants(5, 10);
		C.setInstants(12, 17);	D.setInstants(11, 26);
		E.setInstants(18, 43);	F.setInstants(45, 50);
		G.setInstants(46, 51);	H.setInstants(46, 56);
		I.setInstants(46, 51);	J.setInstants(52, 67);
		K.setInstants(68, 88);	L.setInstants(18, 28);
		M.setInstants(19, 44);	N.setInstants(89, 94);
		
		p.clearActivities();
		p.addActivity(A);	p.addActivity(B);
		p.addActivity(C);	p.addActivity(D);
		p.addActivity(E);	p.addActivity(F);
		p.addActivity(G);	p.addActivity(H);
		p.addActivity(I);	p.addActivity(J);
		p.addActivity(K);	p.addActivity(L);
		p.addActivity(M);	p.addActivity(N);
		
		
		R1 = new Risk(1, "Definicao do Escopo", new RiskExposure(0, 0, 0, 0, 0.3, 3));
		p.addRisk(R1);
		
		
		ArrayList<Activity> activities = p.getActivities();
		// Calcula todos os Tes e Tef das atividades.
		for(int i = 0; i < activities.size(); i++)
			activities.get(i).compute_Tes_Tef();
		
		// Calcula todos os Tlf e Tls das atividades.
		for(int j = (activities.size() - 1); j >= 0; j--)
			activities.get(j).compute_Tlf_Tls();
		
		// Calcula todas as folgas das atividades.
		for(int k = 0; k < activities.size(); k++)
			activities.get(k).compute_GAP();
	}
	
	@Override
	public void simulationScenario(int [] idsActivitiesRunning, int instant)
	{
		double rateEvolutionTime, rateEvolutionCost;		
		
		for (int id : idsActivitiesRunning)
		{	
			Activity a = p.getActivityById(id);
			int currentTime = a.getCurrentTime();
			float currentCost = a.getCurrentCost();
			
			// Calculo das taxas.
			rateEvolutionTime =   ( ( ( instant+1 * 100.0) / a.getEstimatedTime() ) / 100.0);
			rateEvolutionCost = (a.getInstant(instant)+1.0) /  a.getEstimatedTime();
			
			// Arredondamento das das taxas.
			BigDecimal ret = new BigDecimal(rateEvolutionTime).setScale(10, RoundingMode.DOWN);
			BigDecimal rec = new BigDecimal(rateEvolutionCost).setScale(10, RoundingMode.DOWN);
			
			// Calculo do tempo e custo corrente.
			currentTime += ret.floatValue() *  a.getEstimatedTime();			
			currentCost =  rec.floatValue() * a.getEstimatedCost();
			
			a.setCurrentTime(currentTime);
			a.setCurrentCost(currentCost);
		}		
	}
}
