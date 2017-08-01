package iActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class recordLogACon extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	private List<String> logACon = new LinkedList<String>();
	private List<String> instantAndId = new LinkedList<String>();

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
	{
		NumberTerm numSimulation = (NumberTerm) args[0];
		StringTerm cenario		 = (StringTerm) args[1];
		NumberTerm k 			 = (NumberTerm) args[2];
		StringTerm label		 = (StringTerm) args[3];

		if(numSimulation.solve() == 1)
		{
			NumberTerm VEndCost		 = (NumberTerm) args[4];
			NumberTerm VEndTime		 = (NumberTerm) args[5];
			NumberTerm TEPI			 = (NumberTerm) args[6];
			NumberTerm CPI			 = (NumberTerm) args[7];

			NumberTerm newTls 			= (NumberTerm) args[8];
			NumberTerm newTlf 			= (NumberTerm) args[9];
			NumberTerm newECost 		= (NumberTerm) args[10];
			NumberTerm newETime 		= (NumberTerm) args[11];
			NumberTerm newEGAP			= (NumberTerm) args[12];
			NumberTerm idSuccessor 		= (NumberTerm) args[13];
			StringTerm labelSuccessor 	= (StringTerm) args[14];
			NumberTerm oldTls 			= (NumberTerm) args[15];
			NumberTerm oldTlf 			= (NumberTerm) args[16];
			NumberTerm oldECost 		= (NumberTerm) args[17];
			NumberTerm oldETime 		= (NumberTerm) args[18];
			NumberTerm oldGAP 			= (NumberTerm) args[19];

			if (!(logACon.contains("| Instante k: " + k + " |\n")))	//Adicionar apenas se o instante NÃO estiver sido adicionado
			{

				logACon.add("-----------------\n");
				logACon.add("| Instante k: " + k + " |\n");
				logACon.add("-----------------\n");
				logACon.add("Atividade: " + label + "\n\n");

				logACon.add("************** ACOES PARA ATIVIDADES SEPARADAS **************\n\n");

				logACon.add("-------------- Acoes Preventivas --------------\n");
				logACon.add("# Variacao do Tempo (VEtime) ao final da Atividade: " + VEndTime + "\n");
				logACon.add("# Variacao do Custo (VEcost) ao final da Atividade: " + VEndCost.solve() * -1 + "\n");
				logACon.add("# O Indice de Desempenho de Custo (CPI) que vale " + CPI + ", deve melhorar para " + TEPI + " (TEPI)\n");
				logACon.add("# O esforco deve aumentar em " + (TEPI.solve() - CPI.solve()) + "\n\n");

				logACon.add("-------------- Acoes Corretivas --------------\n");
				logACon.add("* Compensacao de Orcamento(Custo) e Cronograma(Tempo) entre as duas atividades sucessoras(fora do caminho critico) com maior Folga\n\n");

				logACon.add("# Atividades Sucessoras da Atividade corrente fora do Caminho Critico:" + "\n\n");

			}									

			logACon.add("# Atividade (id=" + idSuccessor + "): " + labelSuccessor  + "\n");
			logACon.add("	- Tls: " + oldTls + "\n");
			logACon.add("	- Tlf: " + oldTlf + "\n");
			logACon.add("	- Tempo Estimado: " + oldETime + "\n");
			logACon.add("	- Custo Estimado: " + oldECost + "\n");
			logACon.add("	- Folga: " + oldGAP + "\n\n");

			logACon.add("-------------- Novas Estimativas para Atividade " + labelSuccessor + "--------------\n");
			logACon.add("	- Novo Tempo: " + newETime + "\n");
			logACon.add("	- Novo Custo: " + newECost + "\n");
			logACon.add("	- Nova Folga: " + newEGAP + "\n");
			logACon.add("	- Novo Tls: " + newTls + "\n");
			logACon.add("	- Novo Tlf: " + newTlf + "\n\n");
		}    		    		    		

		if(numSimulation.solve() == 2)
		{
			NumberTerm VEndCost		 = (NumberTerm) args[4];
			NumberTerm VEndTime		 = (NumberTerm) args[5];
			NumberTerm TEPI			 = (NumberTerm) args[6];
			NumberTerm CPI			 = (NumberTerm) args[7];

			NumberTerm newTls 			= (NumberTerm) args[8];
			NumberTerm newTlf 			= (NumberTerm) args[9];
			NumberTerm newECost 		= (NumberTerm) args[10];
			NumberTerm newETime 		= (NumberTerm) args[11];
			NumberTerm newEGAP			= (NumberTerm) args[12];
			NumberTerm idSuccessor 		= (NumberTerm) args[13];
			StringTerm labelSuccessor 	= (StringTerm) args[14];
			NumberTerm oldTls 			= (NumberTerm) args[15];
			NumberTerm oldTlf 			= (NumberTerm) args[16];
			NumberTerm oldECost 		= (NumberTerm) args[17];
			NumberTerm oldETime 		= (NumberTerm) args[18];
			NumberTerm oldGAP 			= (NumberTerm) args[19];

			if (!(logACon.contains("| Instante k: " + k + " |\n")))	//Adicionar apenas se o instante NÃO estiver sido adicionado
			{

				logACon.add("-----------------\n");
				logACon.add("| Instante k: " + k + " |\n");
				logACon.add("-----------------\n");
				logACon.add("Atividade: " + label + "\n\n");

				logACon.add("************** ACOES PARA ATIVIDADES SEPARADAS **************\n\n");

				logACon.add("-------------- Acoes Preventivas --------------\n");
				logACon.add("# Variacao do Tempo (VEtime) ao final da Atividade: " + VEndTime + "\n");
				logACon.add("# Variacao do Custo (VEcost) ao final da Atividade: " + VEndCost.solve() * -1 + "\n");
				logACon.add("# O Indice de Desempenho de Custo (CPI) que vale " + CPI + ", deve melhorar para " + TEPI + " (TEPI)\n");
				logACon.add("# O esforco deve aumentar em " + (TEPI.solve() - CPI.solve()) + "\n\n");

				logACon.add("-------------- Acoes Corretivas --------------\n");
				logACon.add("* Hora-extra\n\n");

				logACon.add("# Atividades Sucessoras da Atividade corrente fora do Caminho Critico:" + "\n\n");

			}						

			logACon.add("# Atividade (id=" + idSuccessor + "): " + labelSuccessor  + "\n");
			logACon.add("	- Tls: " + oldTls + "\n");
			logACon.add("	- Tlf: " + oldTlf + "\n");
			logACon.add("	- Tempo Estimado: " + oldETime + "\n");
			logACon.add("	- Custo Estimado: " + oldECost + "\n");
			logACon.add("	- Folga: " + oldGAP + "\n\n");

			double hoursAvailable = VEndCost.solve() / 70;
			double compensation = hoursAvailable + VEndTime.solve();

			if(compensation < 0)
			{
				if(newEGAP.solve() < 0)
				{
					logACon.add("* Compensar Folga da proxima Atividade Sucessora!\n");
					//newEGAP.apply(null);
				}
				else if(newEGAP.solve() == 0)
				{
					logACon.add("* Verficar se existe um novo Caminho Critico!\n");
				}

				logACon.add("-------------- Novas Estimativas para Atividade " + labelSuccessor + "--------------\n");
				logACon.add("	- Novo Tempo: " + newETime + "\n");
				logACon.add("	- Novo Custo: " + newECost + "\n");
				logACon.add("	- Nova Folga: " + newEGAP + "\n");
				logACon.add("	- Novo Tls: " + newTls + "\n");
				logACon.add("	- Novo Tlf: " + newTlf + "\n\n");
			}
			else
			{
				logACon.add("* Compensacao Tempo x Custo pode ser realizado na propria atividade corrente\n\n");
			}
		}

		if(numSimulation.solve() == 3)
		{
			StringTerm labelAP 		= (StringTerm) args[4];
			NumberTerm overCost 	= (NumberTerm) args[5];
			NumberTerm idSuccAP 	= (NumberTerm) args[6];
			StringTerm labelSuccAP 	= (StringTerm) args[7];
			NumberTerm numSuccAP 	= (NumberTerm) args[8];
			NumberTerm ECostSuccAP 	= (NumberTerm) args[9];

			if (!(logACon.contains("| Instante k: " + k + " |\n")))	//Adicionar apenas se o instante NÃO estiver sido adicionado
			{
				logACon.add("-----------------\n");
				logACon.add("| Instante k: " + k + " |\n");
				logACon.add("-----------------\n");
				logACon.add("Atividades: " + label + " e " + labelAP + "\n\n");

				logACon.add("************** ACOES PARA ATIVIDADES CONJUNTAS **************\n\n");

				logACon.add("-------------- Acao Preventiva --------------\n");
				logACon.add("# No instante atual, temos R$ " + overCost + " acima do orcamento\n\n");

				logACon.add("-------------- Acao Corretiva --------------\n");
				logACon.add("* Compensacao de Custo entre as Atividades Sucessoras fora do Caminho Critico\n\n");

				logACon.add("# Atividades Sucessoras das atividades " + label + " e " + labelAP + " que estao fora do Caminho Critico:" + "\n\n");
			}

			//Verificar se neste instante, a atividade já foi inserida
			if (!instantAndId.contains(k + " e " + idSuccAP)){
				instantAndId.add(k + " e " + idSuccAP);
				logACon.add("# Atividade (id=" + idSuccAP + "): " + labelSuccAP  + "\n");
				logACon.add("	- Custo Estimado: " + ECostSuccAP + "\n\n");

				double quotient = (overCost.solve() / numSuccAP.solve()) * -1;
				double newECostSuccAP = ECostSuccAP.solve() - quotient;

				logACon.add("-------------- Novas Estimativas para Atividade " + labelSuccAP + "--------------\n");
				logACon.add("	- Novo Custo: " + newECostSuccAP + "\n\n");
			}


		}

		/* Gravando LogACon */

		FileWriter file = new FileWriter("./logs/logACon_" + cenario.getString() + ".txt");
		PrintWriter record = new PrintWriter(file);

		for (int i = 0; i < logACon.size(); i++)
			record.print(logACon.get(i));

		file.close();

		return true;
	}
}
