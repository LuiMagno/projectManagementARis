// Internal action code for project projectManagement

package iActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

public class dataPackageAMon extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
       
		// dados do ambiente
		NumberTerm k = (NumberTerm) args[0];
    	NumberTerm id = (NumberTerm) args[1];
    	StringTerm label = (StringTerm) args[2];
    	NumberTerm estimedTime = (NumberTerm) args[3];
    	NumberTerm currentTime = (NumberTerm) args[4];
    	NumberTerm estimedCost = (NumberTerm) args[5];
    	NumberTerm currentCost = (NumberTerm) args[6];
    	NumberTerm plannedValue = (NumberTerm) args[7];
    	NumberTerm aggregateValue = (NumberTerm) args[8];
    	
    	// dados do estado interno do AMon
    	NumberTerm variationPeriod = (NumberTerm) args[9];
    	NumberTerm realVariationCost = (NumberTerm) args[10];
    	NumberTerm costPerformanceIndex = (NumberTerm) args[11];
    	NumberTerm schedulePerformanceIndex = (NumberTerm) args[12];
		
    	ListTerm result = new ListTermImpl();
    	result.add(k);
    	result.add(id);
    	result.add(label);
    	result.add(estimedTime);
    	result.add(currentTime);
    	result.add(estimedCost);
    	result.add(currentCost);
    	result.add(plannedValue);
    	result.add(aggregateValue);
    	result.add(variationPeriod);
    	result.add(realVariationCost);
    	result.add(costPerformanceIndex);
    	result.add(schedulePerformanceIndex);    	    
    	
    	return un.unifies(result, args[13]);
    }
}
