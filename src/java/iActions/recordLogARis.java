// Internal action code for project projectManagement

package iActions;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

public class recordLogARis extends DefaultInternalAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> logARis = new LinkedList<String>();
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
//    	  if(args[0]!= null){
//    		  NumberTerm projectId   = (NumberTerm) args[0]; //IdProject 
//    	  }
    	  NumberTerm riskId      = (NumberTerm) args[1]; //Id
		  StringTerm cenario     = (StringTerm) args[2]; //Cenario
		  NumberTerm k           = (NumberTerm) args[3]; //K
		  StringTerm name        = (StringTerm) args[4]; //Name   
		  NumberTerm costP                = (NumberTerm) args[5]; //CP
		  NumberTerm costI                = (NumberTerm) args[6]; //CI
		  NumberTerm timeP                = (NumberTerm) args[7]; //TP
		  NumberTerm timeI                = (NumberTerm) args[8]; //TI
		  NumberTerm scopeP                = (NumberTerm) args[9]; //SP
		  NumberTerm scopeI                = (NumberTerm) args[10]; //SI
		  NumberTerm totalRiskExposure     = (NumberTerm) args[11]; //TotalRiskExposure
		  StringTerm msg                   = (StringTerm) args[12]; //Msg
    	
		  if (!logARis.contains("----------| Instante k: " + k + " |-------\n")){
			  logARis.add("\n-------------------------------------------------\n");
			  logARis.add("----------| Instante k: " + k + " |-------\n");
			  logARis.add("-----------------------------------------------------\n");												
		  }
		  
		  if (!logARis.contains(k + " e " + riskId)){
				//instantAndId.add(k + " e " + id);
				logARis.add("-----------------\n");		
				logARis.add("Risk(ID): " + riskId + "\n");
				logARis.add("Name: " + name + "\n");
				logARis.add("Cost Probability: " + costP + "\n");
				logARis.add("Cost Impact: " + costI + "\n");
				logARis.add("Time Probability: " + timeP + "\n");
				logARis.add("Time Impact: " + timeI + "\n");
				logARis.add("Scope Probability: " + scopeP + "\n");
				logARis.add("Scope Impact: " + scopeI + "\n");
				logARis.add("totalRiskExposure: " + totalRiskExposure + "\n");
				logARis.add("-----------------\n");
				
				logARis.add("---------- Estado Interno do ARis ----------\n");
				logARis.add("Colocar atibutos do Estado Interno.\n");
				logARis.add("---------- Mensagens ----------\n");
				logARis.add(msg + "\n");
		
			}
			else{
				logARis.add(msg + "\n");
			}		
    	
		  /* Gravando LogARis */

			FileWriter file = new FileWriter("./logs/logARis" + cenario.getString() + ".txt");
			PrintWriter record = new PrintWriter(file);

			for (int i = 0; i < logARis.size(); i++)
				record.print(logARis.get(i));		

			file.close();

			return true;
    }
}
