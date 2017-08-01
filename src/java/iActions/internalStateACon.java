package iActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;

public class internalStateACon extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;
	
	private List<Double> GVA = new ArrayList<Double>();

	@Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
    {
    	NumberTerm estimedTime 				= (NumberTerm) args[0];
    	NumberTerm currentTime 				= (NumberTerm) args[1];
    	NumberTerm estimedCost 				= (NumberTerm) args[2];
    	NumberTerm currentCost 				= (NumberTerm) args[3];
    	NumberTerm aggregateValue 			= (NumberTerm) args[4];
    	NumberTerm costPerformanceIndex 	= (NumberTerm) args[5];
    	NumberTerm schedulePerformanceIndex = (NumberTerm) args[6];
    	
    	// Estimativas relacionadas a tempo.
    	double estimateEndTime 				= 0; // estimativa no termino relacionada ao tempo.
    	double estimateToEndTime 			= 0; // estimativa para terminar relacionada ao tempo.
    	double variationEndTime 			= 0; // variacao na conclusao da atividade relacionada ao tempo.
    	double percentVariationEndTime 		= 0; // percentual da variacao na conclusao da atividade relacionado ao tempo.    	
    	
    	double div = estimedCost.solve() / estimedTime.solve();
    	if(div != 0 && schedulePerformanceIndex.solve() != 0 )
    		estimateEndTime = ( ( estimedCost.solve() / schedulePerformanceIndex.solve() ) / div );
    	
    	estimateToEndTime = estimateEndTime - currentTime.solve();
    	variationEndTime = estimedTime.solve() - estimateEndTime;
    	
    	if(estimedTime.solve() != 0)
    		percentVariationEndTime = variationEndTime / estimedTime.solve();
    	
    	// Estimativas relacionadas a custo.
    	double estimateEndCost 				= 0; // estimativa no termino relacionada ao custo.
    	double estimateToEndCost 			= 0; // estimativa para terminar relacionada ao custo.
    	double variationEndCost 			= 0; // variacao na conclusao da atividade relacionada ao custo.
    	double percentVariationEndCost 		= 0; // percentual da variacao na conclusao da atividade relacionado ao custo.
    	double ToEndPerformanceIndex 		= 0; // indice de desempenho para terminar a atividade.
    	
    	if(costPerformanceIndex.solve() != 0)
    		estimateEndCost = estimedCost.solve() / costPerformanceIndex.solve();
    	
    	estimateToEndCost = estimateEndCost - currentCost.solve();
    	variationEndCost = estimedCost.solve() - estimateEndCost;
    	
    	if(estimedCost.solve() != 0)
    		percentVariationEndCost = variationEndCost / estimedCost.solve();
    	
    	double diff = estimedCost.solve() - currentCost.solve();
    	if(diff != 0)
    		ToEndPerformanceIndex = ( ( estimedCost.solve() - aggregateValue.solve() ) / diff );
    	
    	GVA.add(estimateEndTime);
    	GVA.add(estimateToEndTime);
    	GVA.add(variationEndTime);
    	GVA.add(percentVariationEndTime);
    	GVA.add(estimateEndCost);
    	GVA.add(estimateToEndCost);
    	GVA.add(variationEndCost);
    	GVA.add(percentVariationEndCost);
    	GVA.add(ToEndPerformanceIndex);
    	
    	ListTerm result = new ListTermImpl();
    	for (Double i : GVA)
    	{
    		Term t = new NumberTermImpl(i);
    		result.add(t);
		}
    	
    	return un.unifies(result, args[7]);
    }
}
