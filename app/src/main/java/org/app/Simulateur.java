package org.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.app.ai.ChefPompier;
import org.app.ai.ChefPompierFactory;
import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;
import org.app.events.Evenement;
import org.app.events.builtin.InvoquerStrategieEvenement;

import gui.GUISimulator;

public final class Simulateur extends ConsommateurEvenement {

    /**
     * Donnee de simulation.
     */
    private DonneesSimulation donneesDeSimulation;

    /**
     * Registre de donnees de simulation.
     */
    private HashMap<String, DonneesSimulation> registry;

    /**
     * The main gui of the program.
     */
    private GUISimulator guiDeSimulation;

    /**
     * Le Chef de pompier gérant la stratégie de l'application.
     */
    private ChefPompier chef;

    /**
     * Le chargeur de carte à l'aide du système de modules (Java 9).
     * @see java.util.ServiceLoader
     */
    private ServiceLoader<DonneesSimulation> chargeurDeCarte;

    /**
     * La Factory de la stratégie de chef de pompier (permet de régénérer la
     * stratégie dynamiquement).
     */
    private ChefPompierFactory strategieFactory;

    /**
     * Créer un simulateur (gère la logique de l'application).
     * @param gui : La gui rattachée au simulateur.
     * @param simulateurStrategieFactory : La factory de la stratégie.
     */
    public Simulateur(final GUISimulator gui,
            final ChefPompierFactory simulateurStrategieFactory) {
        super();
        this.strategieFactory = simulateurStrategieFactory;
        this.guiDeSimulation = gui;
        // Charge dynamiquement toutes les cartes.
        this.chargeurDeCarte = ServiceLoader.load(DonneesSimulation.class);
        // Range les cartes disponibles.
        this.registry = new HashMap<String, DonneesSimulation>();
        for (DonneesSimulation ds : chargeurDeCarte) {
            this.registry.put(ds.getName(), ds);
        }
        guiDeSimulation.reset();
        // Affiche le selecteur de cartes.
        Iterator<String> keys = this.registry.keySet().iterator();
        String firstName = keys.next();
        gui.addItemToList(firstName);
        while (keys.hasNext()) {
            String name = keys.next();
            gui.addItemToList(name);
        }
        this.selectedItem(firstName);
    }

    /**
     * Effectue une réinitialisation de toutes les cartes et du chef pompier.
     */
    private void reloadAllMaps() {
        chargeurDeCarte.reload();
        this.registry.clear();
        for (DonneesSimulation ds : chargeurDeCarte) {
            this.registry.put(ds.getName(), ds);
        }
        if (this.registry.containsKey(this.donneesDeSimulation.getName())) {
            this.donneesDeSimulation = this.registry
                    .get(this.donneesDeSimulation.getName());
            guiDeSimulation.reset();
            this.guiDeSimulation.addGraphicalElement(donneesDeSimulation);
            this.chef = this.strategieFactory
                    .getChefPompier(donneesDeSimulation, this);
            this.ajouteEvenement(
                    new InvoquerStrategieEvenement(0, this.chef, this));
        }
    }

    @Override
    public void next() {
        if (!this.simulationTerminee()) {
            while (evenements.peek().getDate() <= this.currentDate) {
                Evenement ev = evenements.poll();
                ev.execute();
                if (this.simulationTerminee()) {
                    break;
                }
            }
            this.incrementeDate();
        }
    }

    @Override
    public void restart() {
        this.currentDate = 0;
        this.evenements.clear();
        this.reloadAllMaps();
    }

    @Override
    public void selectedItem(final String name) {
        if (registry.containsKey(name)) {
            this.donneesDeSimulation = registry.get(name);
            this.restart();
        }
    }

}
