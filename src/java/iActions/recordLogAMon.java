package iActions;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;

import jason.asSemantics.*;
import jason.asSyntax.*;

public class recordLogAMon extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;
	
	private List<String> logAMon = new LinkedList<String>();
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
    {    
    	StringTerm cenario    = (StringTerm) args[0];
        NumberTerm k     = (NumberTerm) args[1];
        StringTerm label    = (StringTerm) args[2];
        NumberTerm estimedTime   = (NumberTerm) args[3];
        NumberTerm currentTime   = (NumberTerm) args[4];
        NumberTerm estimedCost   = (NumberTerm) args[5];
        NumberTerm currentCost   = (NumberTerm) args[6];
        NumberTerm plannedValue  = (NumberTerm) args[7];
        NumberTerm aggregateValue  = (NumberTerm) args[8];
        NumberTerm tes    = (NumberTerm) args[9];
        NumberTerm tef    = (NumberTerm) args[10];
        NumberTerm tls    = (NumberTerm) args[11];
        NumberTerm tlf    = (NumberTerm) args[12];
        NumberTerm gap    = (NumberTerm) args[13];
        
        NumberTerm variationPeriod    = (NumberTerm) args[14];
        NumberTerm realVariationCost   = (NumberTerm) args[15];
        NumberTerm costPerformanceIndex  = (NumberTerm) args[16];
        NumberTerm schedulePerformanceIndex = (NumberTerm) args[17];
        
        StringTerm rule1 = (StringTerm) args[18];
        StringTerm rule2 = (StringTerm) args[19];
        StringTerm rule3 = (StringTerm) args[20];
        StringTerm rule4 = (StringTerm) args[21];
    	
    	logAMon.add("-----------------\n");
    	logAMon.add("| Instante k: " + k + " |\n");
    	logAMon.add("-----------------\n");
		logAMon.add("Atividade: " + label + "\n");
		logAMon.add("Tempo estimado: " + estimedTime + "\n");
		logAMon.add("Tempo atual: " + currentTime + "\n");
		logAMon.add("Tempo de inicio cedo(Tes): " + tes + "\n");
		logAMon.add("Tempo de inicio tarde(Tef): " + tef + "\n");
		logAMon.add("Tempo de termino tarde(Tlf): " + tlf + "\n");
		logAMon.add("Tempo de termino cedo(Tls): " + tls + "\n");
		logAMon.add("Folga(GAP): " + gap + "\n");
		logAMon.add("Custo estimado: " + estimedCost + "\n");
		logAMon.add("Custo atual: " + currentCost + "\n\n");
		logAMon.add("---------- Estado Interno do AMon ----------\n");
		logAMon.add("Valor Planejado: " + plannedValue + "\n");
		logAMon.add("Valor Agregado: " + aggregateValue + "\n");
		logAMon.add("Variacao do periodo: " + variationPeriod + "\n");
		logAMon.add("Variacao do custo real: " + realVariationCost + "\n");
		logAMon.add("Indice de performance do custo: " + costPerformanceIndex + "\n");
		logAMon.add("Indice de performance agendada: " + schedulePerformanceIndex + "\n\n");
		logAMon.add("---------- Mensagens Preventivas ----------\n");
		logAMon.add(rule1 + "\n");
		logAMon.add(rule2 + "\n");
		logAMon.add(rule3 + "\n");
		logAMon.add(rule4 + "\n\n");
		
		/* Gravando LogAMon */
		
		FileWriter file = new FileWriter("./logs/logAMon_" + cenario.getString() + ".txt");
		PrintWriter record = new PrintWriter(file);
		
		for (int i = 0; i < logAMon.size(); i++)
			record.print(logAMon.get(i));
		
		file.close();
		
        return true;
    }
}
