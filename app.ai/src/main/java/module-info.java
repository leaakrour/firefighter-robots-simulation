import org.app.ai.ChefPompierFactory;

module app.ai {
	requires app.data;
	requires gui;

	exports org.appai.event;
	exports org.appai.strategie;
	
	provides ChefPompierFactory with org.appai.strategie.ChefElementaireFactory,
		org.appai.strategie.ChefElementaireAmelioreFactory;
}
