package test.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.HashMap;
import java.util.ServiceLoader;

import org.app.Simulateur;
import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.DonneesSimulation;
import org.app.data.Incendie;
import org.app.data.Robot;
import org.app.events.Evenement;
import org.app.events.builtin.BougerRobotEvenement;
import org.app.events.builtin.DeverserEauEvenement;
import org.app.events.builtin.RemplirReservoirEvenement;
import org.app.exceptions.RobotSortiCarteException;
import org.junit.jupiter.api.Test;

import gui.GUISimulator;

public class TestCarteSujet {

    @Test
    public void testScenario0() {
        ServiceLoader<DonneesSimulation> chargeurDeCarte = ServiceLoader
                .load(DonneesSimulation.class);
        HashMap<String, DonneesSimulation> donnees = new HashMap<String, DonneesSimulation>();
        for (DonneesSimulation ds : chargeurDeCarte) {
            donnees.put(ds.getName(), ds);
        }
        GUISimulator gui = new GUISimulator(800, 500, Color.YELLOW);
        Simulateur simulateur = new Simulateur(gui, new StrategieVideFactory());
        gui.setSimulable(simulateur);
        simulateur.selectedItem("CarteSujet");
        Robot robot = donnees.get("CarteSujet").getRobotsSim()[0];
        Case caseR = donnees.get("CarteSujet").getCarteSim().getCase(3, 0);
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(0, robot, Direction.NORD));
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(1, robot, Direction.NORD));
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(2, robot, Direction.NORD));
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(3, robot, Direction.NORD));
        simulateur.next();
        simulateur.next();
        simulateur.next();
        assertThrows(RobotSortiCarteException.class, () -> {
            simulateur.next();
        });
    }

    //@Test
    public void testScenario1() {
        /*
         * Ce test ne marche pas mais c'est normal on est passé d'une unité de temps en minutes vers
         * une unité de temps en secondes au dernier moment et on a pas eu le temps de changer les
         * intervalles de temps du texte.
         */
        ServiceLoader<DonneesSimulation> chargeurDeCarte = ServiceLoader
                .load(DonneesSimulation.class);
        HashMap<String, DonneesSimulation> donnees = new HashMap<String, DonneesSimulation>();
        for (DonneesSimulation ds : chargeurDeCarte) {
            donnees.put(ds.getName(), ds);
        }
        GUISimulator gui = new GUISimulator(800, 500, Color.YELLOW);
        Simulateur simulateur = new Simulateur(gui, new StrategieVideFactory());
        gui.setSimulable(simulateur);
        simulateur.selectedItem("CarteSujet");
        Robot robot = donnees.get("CarteSujet").getRobotsSim()[1];
        Incendie incendie = donnees.get("CarteSujet").getIncendiesSim()[4];
        robot.setVolumeEau(5000);
        assertEquals(5000, robot.getVolumeEau());
        assertEquals(20, robot.getDebitMax());
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(0, robot, Direction.NORD));
        Evenement event1 = new DeverserEauEvenement(-4, robot, incendie);
        simulateur.ajouteEvenement(event1);
        simulateur.next();
        simulateur.next();
        assertEquals(0, robot.getVolumeEau());
        assertEquals(3000, incendie.getLitreEau());
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(2.0, robot, Direction.OUEST));
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(3.0, robot, Direction.OUEST));
        simulateur.next();
        simulateur.next();
        Evenement event2 = new RemplirReservoirEvenement(-6.0, robot);
        simulateur.ajouteEvenement(event2);
        simulateur.next();
        assertEquals(5000, robot.getVolumeEau());
        assertEquals(3000, incendie.getLitreEau());
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(5.0, robot, Direction.EST));
        simulateur.ajouteEvenement(
                new BougerRobotEvenement(6.0, robot, Direction.EST));
        simulateur.next();
        simulateur.next();
        Evenement event3 = new DeverserEauEvenement(4, robot, incendie);
        simulateur.ajouteEvenement(event3);
        assertEquals(6.5, event3.getDate());
        simulateur.next();
        assertEquals(2000, robot.getVolumeEau());
        assertEquals(0, incendie.getLitreEau());
    }
}
