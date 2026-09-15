package org.app;

import java.awt.Color;
import java.util.ServiceLoader;

import org.app.ai.ChefPompierFactory;

import gui.GUISimulator;

public final class Main {

    private Main() {
    }

    /**
     * Largeur de la fenêtre.
     */
    private static final int WIDTH = 800;

    /**
     * Hauteur de la fenêtre.
     */
    private static final int HEIGHT = 500;

    /**
     * Temps d'attente lors de la boucle.
     */
    private static final int WAIT_TIME = 10;

    /**
     * Simulateur principal du programme.
     */
    private static Simulateur simulateur;

    /**
     * The main gui of the program.
     */
    private static GUISimulator gui;

    /**
     * Fonction principale du programme.
     * @param args Arguments passés au programme.
     */
    public static void main(final String[] args) {
        gui = new GUISimulator(WIDTH, HEIGHT, Color.WHITE);
        ServiceLoader<ChefPompierFactory> stratLoader = ServiceLoader
                .load(ChefPompierFactory.class);
        ChefPompierFactory factory = null;
        if (args.length == 0) {
            factory = stratLoader.findFirst().get();
        } else {
            factory = stratLoader.stream()
                    .filter(chef -> chef.get().getNomChef().equals(args[0]))
                    .findFirst().get().get();
        }
        simulateur = new Simulateur(gui, factory);
        gui.setSimulable(simulateur);
        while (true) {
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
