package iActions;

import java.util.LinkedList;
import java.util.List;

import jason.asSemantics.*;
import jason.asSyntax.*;

public class ruleBaseAMon extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;
	
	private List<String> rules = new LinkedList<String>();
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception
    {
    	NumberTerm VP = (NumberTerm) args[0];
    	NumberTerm RVC = (NumberTerm) args[1];
    	NumberTerm CPI = (NumberTerm) args[2];
    	NumberTerm SPI = (NumberTerm) args[3];
    	
    	/********************************************************
						INDICE DE DESEMPENHO DE CUSTO
    	 ********************************************************/
    	if(CPI.solve() == 1.0)
		{
			rules.add("Dentro do Orcamento.");
		}
		else if(CPI.solve() > 1.0)
		{
			rules.add("Abaixo do Orcamento (Economia).");
		}
		else if(CPI.solve() < 1.0)
		{
			rules.add("Acima do Orcamento (Estouro).");
		}

		/********************************************************
						INDICE DE DESEMPENHO DE PRAZOS
		********************************************************/    	
		if(SPI.solve() == 1.0)
		{
			rules.add("Dentro do Cronograma.");
		}
		else if(SPI.solve() > 1.0)
		{
			rules.add("Adiantado no Cronograma.");
		}
		else if(SPI.solve() < 1.0)
		{
			rules.add("Atrasado no Cronograma.");
		}
    	
    	/********************************************************
    	 					VARIACAO DE PRAZO
    	 ********************************************************/
    	if(VP.solve() == 0.0)
    	{
    		rules.add("Dentro do Cronograma.");
    	} 
    	else if(VP.solve() > 0.0)
    	{
    		rules.add("R$ " + VP.solve() + " a frente do cronograma.");
    	}
    	else if(VP.solve() < 0.0)
    	{
    		rules.add("R$ " + VP.solve() + " reais atras do cronograma.");
    	}
    	
    	/********************************************************
		  					VARIACAO DE CUSTO
    	 ********************************************************/
    	if(RVC.solve() == 0.0)
    	{
    		rules.add("Dentro do Orcamento.");
    	}
    	else if(RVC.solve() > 0.0)
    	{
    		rules.add("R$ " + RVC.solve() + " reais abaixo do Orcamento.");
    	}
    	else if(RVC.solve() < 0.0)
    	{
    		rules.add("R$ " + RVC.solve() + " reais acima do Orcamento.");
    	}
    	
    	ListTerm result = new ListTermImpl();
    	for (String i : rules)
    	{
    		Term t = new StringTermImpl(i);
    		result.add(t);
		}
    	
        return un.unifies(result, args[4]);
    }
}
