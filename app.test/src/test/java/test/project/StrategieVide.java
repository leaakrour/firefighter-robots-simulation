package test.project;

import org.app.ai.ChefPompier;
import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public class StrategieVide extends ChefPompier {

    public StrategieVide(DonneesSimulation donneesSimulation,
            ConsommateurEvenement cons) {
        super(donneesSimulation, cons);
    }

    @Override
    public void strategie() {

    }

}
