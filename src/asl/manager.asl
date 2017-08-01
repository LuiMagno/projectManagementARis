// Agent manager in project projectManagement

/* Initial beliefs and rules */

/* Initial goals */

!create_and_use.
!monitoringEnvChanges.

/* Plans */

+!create_and_use : true
	<-	?artifactSetup(ArtifactId).

+?artifactSetup(Id) : true
	<-	
		makeArtifact("EnvProject", "workspaces.EnvironmentProject", [], Id);
		focus(Id);
		.println("Artefato criado!").

-?artifactSetup(Id) : true
	<-	
		.wait(30);
		!create_and_use.
		
+!monitoringEnvChanges: true <-
	?myEnvironment(CRId).

+?myEnvironment(CRId) : true
	<-	
	lookupArtifact("EnvChanges",  IdCR);
	focus(IdCR).		

-?myEnvironment(CRId) : true
	<-	
		.wait(100);
		!monitoringEnvChanges.		
		

+tick : instant(K) & activities(A) <-                                      //Solicitações de mudanças para o cenário 1 (SBQS) 
	if (K == 50){		
		cartago.invoke_obj(A, get(8), TaskI);
		cartago.invoke_obj(A, get(7), TaskH);
		cartago.invoke_obj("models.TypeChange", getAddCost, Cost);
		cartago.invoke_obj("models.TypeChange", getAddTime, Time);
		cartago.invoke_obj("models.StateOfChange", getRequested, State);
				
		cartago.new_obj("models.Change", [1,"Mudanças no custo e no tempo", K, TaskI, State], Ci);		
		cartago.invoke_obj(Ci, addRequest(Cost, 11.903));
		cartago.invoke_obj(Ci, addRequest(Time, 11.8));
		cartago.invoke_obj(TaskI, getLabel, LabelI);
		println("Solicitando no instante ", K, " um acréscimo de 11,9% no custo e de 11,8% no tempo para a atividade ", LabelI);		
		requestChange(Ci);
		
		cartago.new_obj("models.Change", [2,"Mudanças no custo e no tempo", K, TaskH, State], Ch);		
		cartago.invoke_obj(Ch, addRequest(Cost, 5.95));
		cartago.invoke_obj(Ch, addRequest(Time, 5.9));
		cartago.invoke_obj(TaskH, getLabel, LabelH);
		println("Solicitando no instante ", K, " um acréscimo de 5.9% no custo e de 5.9% no tempo para a atividade ", LabelH);		
		requestChange(Ch);		
	}. /*
	
	if (K == 6){                                                                                      //Cenário 2 (SBQS)		
		cartago.invoke_obj(A, get(11), TaskL);
		cartago.invoke_obj(A, get(12), TaskM);
		cartago.invoke_obj(A, get(8), TaskI);
		cartago.invoke_obj(A, get(7), TaskH);
		cartago.invoke_obj(A, get(3), TaskD);
		cartago.invoke_obj("models.TypeChange", getRemCost, Cost);
		cartago.invoke_obj("models.StateOfChange", getRequested, State);
				
		cartago.new_obj("models.Change", [1,"mudanças no custo ", K, TaskL, State], Cl);		
		cartago.invoke_obj(Cl, addRequest(Cost, 2.22));
		cartago.invoke_obj(TaskL, getLabel, LabelL);
		println("Solicitando no instante ", K, " um decréscimo de 2.22% no custo para a atividade ", LabelL);		
		requestChange(Cl);
				
		cartago.new_obj("models.Change", [2,"mudanças no custo ", K, TaskM, State], Cm);		
		cartago.invoke_obj(Cm, addRequest(Cost, 0.889));
		cartago.invoke_obj(TaskM, getLabel, LabelM);
		println("Solicitando no instante ", K, " um decréscimo de 0.889% no custo para a atividade ", LabelM);		
		requestChange(Cm);
		
		cartago.new_obj("models.Change", [3,"mudanças no custo ", K, TaskI, State], Ci);		
		cartago.invoke_obj(Ci, addRequest(Cost, 4.445));
		cartago.invoke_obj(TaskI, getLabel, LabelI);
		println("Solicitando no instante ", K, " um decréscimo de 4.445% no custo para a atividade ", LabelI);		
		requestChange(Ci);
		
		cartago.new_obj("models.Change", [4,"mudanças no custo ", K, TaskH, State], Ch);		
		cartago.invoke_obj(Ch, addRequest(Cost, 2.22));
		cartago.invoke_obj(TaskH, getLabel, LabelH);
		println("Solicitando no instante ", K, " um decréscimo de 2.22% no custo para a atividade ", LabelH);		
		requestChange(Ch);
		
		cartago.new_obj("models.Change", [5,"mudanças no custo ", K, TaskD, State], Cd);		
		cartago.invoke_obj(Cd, addRequest(Cost, 1.481));
		cartago.invoke_obj(TaskD, getLabel, LabelD);
		println("Solicitando no instante ", K, " um decréscimo de 1.481% no custo para a atividade ", LabelD);		
		requestChange(Cd);
	}.*/