// Agent aMon in project project_management

/* Initial beliefs and rules */

internalState(null).
rules(null).

/* Initial goals */

!monitoring.

/* Plans */

+!monitoring : true <-
	?myArtifact(ArtifactId); // descobrir artefato.
	!environmentSimulate.

+?myArtifact(ArtifactId) : true <-
	lookupArtifact("EnvProject", ArtifactId);
	focus(ArtifactId).

-?myArtifact(ArtifactId) : true <-
	.wait(10);
	!monitoring.

+!environmentSimulate: true <- simulate.

+!monitoringActivities : instant(K) & idsActivitiesRunning(Ids) & cenario(Cenario) <-

	for(.member(Id, Ids))
	{
		getDataActivities(Id, Label, EstimedTime, CurrentTime, EstimedCost, CurrentCost, TimeStoped, Tes, Tef, Tls, Tlf, GAP);	
		
		PlannedValue = EstimedCost * (CurrentTime / EstimedTime);
		// TimeStoped auxilia na contabilização do % Real Completa.
		AggregateValue = EstimedCost * (CurrentTime / (EstimedTime + TimeStoped));
		/**************************************************************************************************************
			Valor planejado, Valor agregado, Custo Real, Feedback para estado interno
			O Valor Planejado deve levar em conta o cronograma, ou seja, o quanto deveria ter sido gasto até o momento
			O Valor Agregado deve considerar a REAL COMPLETA, ou seja, o que a equipe REALMENTE recebeu do orçamento.
		***************************************************************************************************************/
		iActions.internalStateAMon(PlannedValue, AggregateValue , CurrentCost, InternalState);
		-+internalState(InternalState);
		
		.length(InternalState, LengthIS);
		.nth(LengthIS-4, InternalState, VariationPeriod);		.nth(LengthIS-3, InternalState, RealVariationCost);
		.nth(LengthIS-2, InternalState, CostPerformanceIndex);	.nth(LengthIS-1, InternalState, SchedulePerformanceIndex);
		
		iActions.ruleBaseAMon(VariationPeriod, RealVariationCost, CostPerformanceIndex, SchedulePerformanceIndex, Rules);
		
		.length(Rules, LengthRules);
		.nth(LengthRules-4, Rules, R1);	.nth(LengthRules-3, Rules, R2);
		.nth(LengthRules-2, Rules, R3);	.nth(LengthRules-1, Rules, R4);

		iActions.recordLogAMon(Cenario, K, Label, EstimedTime, CurrentTime, EstimedCost, CurrentCost, PlannedValue, AggregateValue,
			Tes, Tef, Tls, Tlf, GAP, VariationPeriod, RealVariationCost, CostPerformanceIndex, SchedulePerformanceIndex, R1, R2, R3, R4);
		
		iActions.dataPackageAMon(K, Id, Label, EstimedTime, CurrentTime, EstimedCost, CurrentCost, PlannedValue, AggregateValue,
								 VariationPeriod, RealVariationCost, CostPerformanceIndex, SchedulePerformanceIndex, DataPackage);
								 
		// Enviando Estado Interno para o ACon.
		.send(aCon, tell, DataPackage);
	}.

/* Perceptions */

+tick : instant(K) & durationProject(D) <- 
	!monitoringActivities;
	if(K == D - 1)
	{
		.println("Monitoramento Concluido - analise o log do AMon.");
	}.

+idProject(ID) <- .println("Monitorando Projeto ", ID).