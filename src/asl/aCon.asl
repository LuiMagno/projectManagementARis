// Agent aCon in project projectManagement

/* Initial beliefs and rules */

internalStateAMon(null).
internalStateACon(null).

/* Initial goals */

!look.
!create.

/* Plans */

+!look : true <-
	?myArtifact(ArtifactId). // descobrir artefato.

+!create : true <-
	?artifactSetup(ArtifactAuxId). // construir artefato auxiliar.

+?myArtifact(ArtifactId) : true <- 
	lookupArtifact("EnvProject", ArtifactId);
	focus(ArtifactId).
	
+?artifactSetup(AuxId) : true <-
	makeArtifact("AuxControl", "workspaces.AuxControlArtifact", [], AuxId);
	focus(AuxId);
	.println("Artefato auxiliar criado!").

-?myArtifact(ArtifactId) : true <-
	.wait(10);
	!look.
	
-?artifactSetup(AuxId) : true <-
	.wait(10);
	!create.

/* Message */
+!kqml_received(Sender, tell, Variables, Response) : true  <-
 		 
 	-+internalStateAMon(Variables);
	!controllingActivities.
	
+!controllingActivities : internalStateAMon(ISAMon) <-
	
	.length(ISAMon, LengthISAMon);
	.nth(LengthISAMon-10, ISAMon, EstimedTime);		.nth(LengthISAMon-9, ISAMon, CurrentTime);
	.nth(LengthISAMon-8, ISAMon, EstimedCost);		.nth(LengthISAMon-7, ISAMon, CurrentCost);
	.nth(LengthISAMon-5, ISAMon, AggregateValue);	.nth(LengthISAMon-2, ISAMon, CPI);
	.nth(LengthISAMon-1, ISAMon, SPI);
	
	iActions.internalStateACon(EstimedTime, CurrentTime, EstimedCost, CurrentCost, AggregateValue, CPI, SPI, InternalState);
							   
	-+internalStateACon(InternalState);
	
	!correctiveActions.
	
+!correctiveActions : cenario(Cenario) & internalStateAMon(ISAMon) & internalStateACon(ISACon) & listActivitiesOutCP(ListOutCP)
					  & numActivitiesRunning(NumActvsRunning) & idsActivitiesRunning(IdsActivsRunning) <-
	
	.length(ISAMon, LengthISAMon);
	.nth(LengthISAMon-13, ISAMon, K);	.nth(LengthISAMon-12, ISAMon, Id);	.nth(LengthISAMon-11, ISAMon, Label);
	.nth(LengthISAMon-4, ISAMon, VP);	.nth(LengthISAMon-3, ISAMon, VC);	.nth(LengthISAMon-2, ISAMon, CPI);
	.nth(LengthISAMon-1, ISAMon, SPI);
	
	.length(ISACon, LengthISACon);
	.nth(LengthISACon-7, ISACon, VEtime);	.nth(LengthISACon-3, ISACon, VEcost);	.nth(LengthISACon-1, ISACon, TEPI);
	
	if(Cenario == "SBQS_Cenario_2" & NumActvsRunning > 1)
	{
		for(.member(IdP, IdsActivsRunning))
		{
			setParallel(Id, IdP);
		}
		getParallel(IdParallel);
		getActivity(IdParallel, ParallelActiv);
		
		cartago.invoke_obj(ParallelActiv, getLabel, IdLabelAP);
		cartago.invoke_obj(ParallelActiv, getEstimatedTime, ETimeAP);
		cartago.invoke_obj(ParallelActiv, getCurrentTime, CTimeAP);
		cartago.invoke_obj(ParallelActiv, getEstimatedCost, ECostAP);
		cartago.invoke_obj(ParallelActiv, getCurrentCost, CCostAP);
		cartago.invoke_obj(ParallelActiv, getTimeStopped, TStopAP);
		
		PVap = ECostAP * (CTimeAP / ETimeAP);
		AVap = ECostAP * (CTimeAP / (ETimeAP + TStopAP));
		
		iActions.internalStateAMon(PVap, AVap , CCostAP, ISAMonAP);
		
		.length(ISAMonAP, SizeISAMonAP);
		.nth(SizeISAMonAP-2, ISAMonAP, CPIap);	.nth(SizeISAMonAP-1, ISAMonAP, SPIap);
		
		iActions.internalStateACon(ETimeAP, CTimeAP, ECostAP, CCostAP, AVap, CPIap, SPIap, ISAConAP);
		
		.length(ISAConAP, SizeISAConAP);
		.nth(SizeISAConAP-7, ISAConAP, VEtimeAP);	.nth(SizeISAConAP-3, ISAConAP, VEcostAP);
		
		OverCost = VEcost + VEcostAP;
		OverTime = VEtime + VEtimeAP;
		
		if(OverCost < 0 & OverTime > 0)
		{
			getSuccessorsActivitiesOutCP(Id, SuccOutCPforAC);
			
			getSuccessorsActivitiesOutCP(IdParallel, SuccOutCPforAP);
			
			union(SuccOutCPforAC, SuccOutCPforAP, Union);
			.length(Union, SizeUnion);
			
			if(SizeUnion > 1)
			{
				for(.member(U, Union))
				{
					cartago.invoke_obj(U, getId, IdActivSucc);
					cartago.invoke_obj(U, getLabel, IdLabelActivSucc);
					cartago.invoke_obj(U, getEstimatedCost, ECostActivSucc);
					
					//.println("	- Activ Succ: ", IdLabelActivSucc, " --> Custo: ", ECostActivSucc);
					iActions.recordLogACon(3, Cenario, K, Label, IdLabelAP, OverCost, IdActivSucc, IdLabelActivSucc, SizeUnion, ECostActivSucc);
				}
			}
		}
		
		if(OverCost < 0 & OverTime < 0)
		{
			.println("OverCost < 0 & OverTime < 0");
		}
	}
	
	if(Cenario == "SBQS_Cenario_1")
	{
		QuotientCost = ( (VEcost / 2) * -1 );
		QuotientTime = (QuotientCost / 70);
		Ratio = (QuotientTime / 2);
		
		if(VP > 0 & VC < 0 & CPI < 1 & SPI > 1)	// Adiantado no Cronograma e Acima do Orcamento.
		{
			getSuccessorsActivitiesOutCP(Id, SuccOutCP);
			.length(SuccOutCP, Size);
		
			if(Size > 1)
			{
				for(.member(RefActivity, SuccOutCP))
				{
					sendActivity(RefActivity);
				}
				
				cartago.invoke_obj(ListOutCP, size, SizeOutCP);
				for( .range(I, 0, SizeOutCP-(SizeOutCP-1)) ) // percorre somente as duas primeiras atividades (maior folga).
				{
					cartago.invoke_obj(ListOutCP, get(I), ActivOutCP);
					cartago.invoke_obj(ActivOutCP, getId, ID);
					cartago.invoke_obj(ActivOutCP, getLabel, IdLabel);
					cartago.invoke_obj(ActivOutCP, getTlateStart, Tls);
					cartago.invoke_obj(ActivOutCP, getTlateFinish, Tlf);
					cartago.invoke_obj(ActivOutCP, getEstimatedCost, EstCost);
					cartago.invoke_obj(ActivOutCP, getEstimatedTime, EstTime);
					cartago.invoke_obj(ActivOutCP, getGAP, GAP);
					
					//.println("Atividade Sucessora(", ID,"):", IdLabel);
					
					NewTls = Tls - Ratio;
					NewTlf = Tlf - Ratio;
					NewEstimatedCost = EstCost - QuotientCost;
					NewEstimatedTime = EstTime - QuotientTime;
					NewEstimatedGAP = GAP - QuotientTime;
					
					iActions.recordLogACon(1, Cenario, K, Label, VEcost, VEtime, TEPI, CPI, NewTls, NewTlf,
						NewEstimatedCost, NewEstimatedTime, NewEstimatedGAP, ID, IdLabel, Tls, Tlf, EstCost, EstTime, GAP);
				}
				clearList;
			}
		}
		
		if(VP < 0 & VC > 0 & CPI > 1 & SPI < 1)	// Atrasado no Cronograma e Abaixo do Orcamento.
		{
			getSuccessorsActivitiesOutCP(Id, SuccOutCP);
			.length(SuccOutCP, Size);
		
			if(Size > 1)
			{
				for(.member(RefActivity, SuccOutCP))
				{
					sendActivity(RefActivity);
				}
				
				cartago.invoke_obj(ListOutCP, size, SizeOutCP);
				for( .range(I, 0, SizeOutCP-(SizeOutCP-1)) ) // percorre somente as duas primeiras atividades (maior folga).
				{
					cartago.invoke_obj(ListOutCP, get(I), ActivOutCP);
					cartago.invoke_obj(ActivOutCP, getId, ID);
					cartago.invoke_obj(ActivOutCP, getLabel, IdLabel);			
					cartago.invoke_obj(ActivOutCP, getTlateStart, Tls);
					cartago.invoke_obj(ActivOutCP, getTlateFinish, Tlf);
					cartago.invoke_obj(ActivOutCP, getEstimatedCost, EstCost);
					cartago.invoke_obj(ActivOutCP, getEstimatedTime, EstTime);
					cartago.invoke_obj(ActivOutCP, getGAP, GAP);
					
					//.println("Atividade Sucessora(", ID,"):", IdLabel);
					
					NewTls = Tls - Ratio;
					NewTlf = Tlf - Ratio;
					NewEstimatedCost = EstCost - QuotientCost;
					NewEstimatedTime = EstTime - QuotientTime;
					NewEstimatedGAP = GAP - QuotientTime;
					
					iActions.recordLogACon(2, Cenario, K, Label, VEcost, VEtime, TEPI, CPI, NewTls, NewTlf,
						NewEstimatedCost, NewEstimatedTime, NewEstimatedGAP, ID, IdLabel, Tls, Tlf, EstCost, EstTime, GAP);
				}
				clearList;
			}
		}
	}.
	
/* Perceptions */

+tick : instant(K) & durationProject(D) <-
	if(K == D - 1)
	{
		.println("Controle Concluido - analise o log do ACon.");
	}.

+project(P) <- setProject(P).

+statusOutCP(S) : S == "newActivOutCP" & activityOutCP(ActivOutCP)/* <-	
	cartago.invoke_obj(ActivOutCP, getLabel, Label);
	.println("Percebi a Atividade ", Label, " fora do Caminho Critico!")*/.
