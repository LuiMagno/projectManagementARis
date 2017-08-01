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

public class recordLogAMud extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;

	private List<String> logAMud = new LinkedList<String>();
	private List<String> instantAndId = new LinkedList<String>();

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
	{    
		NumberTerm id      = (NumberTerm) args[0];
		  StringTerm cenario     = (StringTerm) args[1];
		  NumberTerm k           = (NumberTerm) args[2];
		  StringTerm label      = (StringTerm) args[3];     
		  NumberTerm valueAC        = (NumberTerm) args[4];
		  NumberTerm valueRC      = (NumberTerm) args[5];
		  NumberTerm valueAT      = (NumberTerm) args[6];
		  NumberTerm valueRT      = (NumberTerm) args[7];     
		  Term state = null;        
		  if (args[8].isNumeric())
		   state        = (NumberTerm) args[8];
		  else if (args[8].isLiteral())
		   state                   = (StringTerm) args[8];
		  NumberTerm IC                = (NumberTerm) args[9];
		  NumberTerm IT                = (NumberTerm) args[10];
		  NumberTerm U                = (NumberTerm) args[11];
		  NumberTerm P                = (NumberTerm) args[12];     
		  StringTerm msg      = (StringTerm) args[13];

		if (!logAMud.contains("----------| Instante k: " + k + " |-------\n")){
			logAMud.add("\n-------------------------------------------------\n");
			logAMud.add("----------| Instante k: " + k + " |-------\n");
			logAMud.add("-----------------------------------------------------\n");												
		}
		
		if (!instantAndId.contains(k + " e " + id)){
			instantAndId.add(k + " e " + id);
			logAMud.add("-----------------\n");		
			logAMud.add("Solicitação(ID): " + id + "\n");
			logAMud.add("Atividade: " + label + "\n");
			logAMud.add("-----------------\n");

			if (valueAC.solve() > 0)		
				logAMud.add("Acréscimo no custo: " + valueAC + "\n");
			if (valueAT.solve() > 0)		
				logAMud.add("Acréscimo no tempo: " + valueAT + "\n");
			if (valueRC.solve() > 0)		
				logAMud.add("Decréscimo no custo: " + valueRC + "\n");
			if (valueRT.solve() > 0)		
				logAMud.add("Decréscimo no tempo: " + valueRT + "\n");

			logAMud.add("Estado atual da solicitação: " + state + "\n");
			
			logAMud.add("---------- Estado Interno do AMud ----------\n");
			logAMud.add("Impacto no tempo: " + IT + "\n");
			logAMud.add("Impacto no custo: " + IC + "\n");
			logAMud.add("Urgência: " + U + "\n");
			logAMud.add("Prioridade: " + P + "\n");		
			logAMud.add("---------- Mensagens ----------\n");
			logAMud.add(msg + "\n");
		}
		else{
			logAMud.add(msg + "\n");
		}			

		/* Gravando LogAMud */

		FileWriter file = new FileWriter("./logs/logAMud_" + cenario.getString() + ".txt");
		PrintWriter record = new PrintWriter(file);

		for (int i = 0; i < logAMud.size(); i++)
			record.print(logAMud.get(i));		

		file.close();

		return true;
	}
}
