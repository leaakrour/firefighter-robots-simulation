package test.project;

import org.app.ai.ChefPompier;
import org.app.ai.ChefPompierFactory;
import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public class StrategieVideFactory extends ChefPompierFactory {

    @Override
    public ChefPompier getChefPompier(DonneesSimulation donneesSimulation,
            ConsommateurEvenement cons) {
        return new StrategieVide(donneesSimulation, cons);
    }

    @Override
    public String getNomChef() {
        return "StrategieVide";
    }

}
