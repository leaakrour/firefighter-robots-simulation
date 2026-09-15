module app.test {
    requires java.base;
    requires java.desktop;
    requires gui;
    requires app.data;
    requires app.main;
    requires org.junit.jupiter.api;
    opens test.project to org.junit.platform.commons;

    uses org.app.data.DonneesSimulation;
}
