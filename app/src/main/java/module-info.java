module app.main {
    requires java.base;
    requires java.desktop;
    requires gui;
    requires app.data;
    
    opens org.app to app.test;
    exports org.app to app.test;

    uses org.app.data.DonneesSimulation;
    uses org.app.ai.ChefPompierFactory;
}
