// Agent aRis in project projectManagement

/* Initial beliefs and rules */

/* Initial goals */
!monitoring.
!create.
/* Plans */

+!monitoring : true 	<-	
		?myArtifact(ArtifactId). // descobrir artefato.
		
+!create: true <-
	?myEnvironment(AuxR).

+?myArtifact(ArtifactId) : true
	<-	lookupArtifact("EnvProject", ArtifactId);
		focus(ArtifactId).		

-?myArtifact(ArtifactId) : true
	<-	
		.wait(100);
		!monitoring.		

+?myEnvironment(AuxR) : true
	<-	
	makeArtifact("EnvRiskControl", "workspaces.EnvironmentRiskControl", [], AuxR);
	.print("Criei!");
	focus(AuxR).		

-?myEnvironment(CRId) : true
	<-	
		.wait(100);
		!create.	

+!monitoringRisks : risks(RiskList) <-

	if (RiskList \== null){
		cartago.invoke_obj(RiskList, size, Size);
		
		for(.range(I, 0, Size-1)){
			cartago.invoke_obj(RiskList, get(I), Risk);
			-+risk(Risk);
			cartago.invoke_obj(Risk, getId, Id);
			cartago.invoke_obj(Risk, getName, Name);
			cartago.invoke_obj(Risk, getRiskExposure, RE);
			-+riskExposure(RE); //RiskExposure Object!
			
//			!calculateImpact(DRemTime, ImpactTime);
//			!calculateUrgence(U);

			cartago.invoke_obj(RE, getCostP, CP);
			-+costP(CP);
			cartago.invoke_obj(RE, getCostI, CI);
			-+costI(CI);
			cartago.invoke_obj(RE, getTimeP, TP);
			-+timeP(TP);
			cartago.invoke_obj(RE, getCostP, TI);
			-+timeI(TI);
			cartago.invoke_obj(RE, getScopeP, SP);
			-+scopeP(SP);   
			cartago.invoke_obj(RE, getScopeI, SI);
			-+scopeI(SI);    	
			!calculateRiskExposure(Id);
			
			!recordLog(Id, Name, CP, CI, TP, TI, SP, SI, "Informações do Risco.");
				
		};
	}.
	
+tick : instant(K) <-
	!monitoringRisks.
	
+project(P) <- 
	cartago.invoke_obj(P, getId, IdProject);
	setProject(P);
	.print("ARis observando o Projeto ", IdProject).

+!calculateRiskExposure(Id): costP(CP) & costI(CI) & timeP(TP) & timeI(TI) & scopeP(SP) & scopeI(SI) <-
//	.print(CP); 
//	.print(CI); 
//	.print(TP); 
//	.print(TI); 
//	.print(SP); 
//	.print(SI); 
	TotalRiskExposure = (CP*CI)+(TP*TI)+(SP*SI);	//COMO JOGAR ESSA (TRE) DENTRO DO RISCO NA VARIÁVEL TotalRiskExposure???
	.print("Risk = ",Id," RE = ",TotalRiskExposure); 
	-+totalRiskEsposure(TotalRiskExposure).	
	
+!recordLog(Id, Name, CP, CI, TP, TI, SP, SI, Msg): project(P) & instant(K) & cenario(Cenario) & totalRiskEsposure(TotalRiskExposure) <-
	iActions.recordLogARis(P, Id, Cenario, K, Name, CP, CI, TP, TI, SP, SI, TotalRiskExposure, Msg).
	//.concat(Str, ". Risk: ", Name);
	//+msg(Msg).