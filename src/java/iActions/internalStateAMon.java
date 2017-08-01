package iActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class internalStateAMon extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;

	private List<Double> GVA = new ArrayList<Double>();
	
	private int roundValue = 2;
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
    {
    	NumberTerm plannedValue = (NumberTerm) args[0];
    	NumberTerm aggregateValue = (NumberTerm) args[1];
    	NumberTerm realCost = (NumberTerm) args[2];
    	
    	//Arredondamento
    	BigDecimal pv = new BigDecimal(plannedValue.solve()).setScale(roundValue, RoundingMode.DOWN);
		BigDecimal av = new BigDecimal(aggregateValue.solve()).setScale(roundValue, RoundingMode.DOWN);
		BigDecimal rc = new BigDecimal(realCost.solve()).setScale(roundValue, RoundingMode.DOWN);

    	double costPerformanceIndex = 0;
    	double schedulePerformanceIndex = 0;    	
    	
    	//Com arredondamento
    	double variationPeriod = new BigDecimal(av.doubleValue() - pv.doubleValue()).setScale(roundValue, RoundingMode.DOWN).doubleValue();
    	double realVariationCost = new BigDecimal(av.doubleValue() - rc.doubleValue()).setScale(roundValue, RoundingMode.DOWN).doubleValue();    	
    	
    	if(realCost.solve() != 0)
    		costPerformanceIndex =  new BigDecimal( av.doubleValue() / rc.doubleValue()).setScale(roundValue, RoundingMode.DOWN).doubleValue();
    	
    	if(plannedValue.solve() != 0)
    		schedulePerformanceIndex = new BigDecimal(av.doubleValue() / pv.doubleValue()).setScale(roundValue, RoundingMode.DOWN).doubleValue();    		
    	
    	GVA.add(variationPeriod);
    	GVA.add(realVariationCost);
    	GVA.add(costPerformanceIndex);
    	GVA.add(schedulePerformanceIndex);
    	
    	ListTerm result = new ListTermImpl();
    	for (Double i : GVA)
    	{
    		Term t = new NumberTermImpl(i);
    		result.add(t);
		}
    	
    	return un.unifies(result, args[3]);
    }
}
