package org.app.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import org.app.data.Carte;
import org.app.data.Case;
import org.app.data.DonneesSimulation;
import org.app.data.Incendie;
import org.app.data.NatureTerrain;
import org.app.data.Robot;
import org.app.data.robots.RoboAPattes;
import org.app.data.robots.RobotARoues;
import org.app.data.robots.RobotChenilles;
import org.app.data.robots.RobotDrone;

/**
 * Lecteur de cartes au format spectifié dans le sujet. Les données sur les
 * cases, robots puis incendies sont lues dans le fichier, puis simplement
 * affichées. A noter: pas de vérification sémantique sur les valeurs numériques
 * lues.
 * IMPORTANT:
 * Cette classe ne fait que LIRE les infos et les afficher. A vous de modifier
 * ou d'ajouter des méthodes, inspirées de celles présentes (ou non), qui CREENT
 * les objets au moment adéquat pour construire une instance de la classe
 * DonneesSimulation à partir d'un fichier.
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues: public static DonneesSimulation
 * creeDonnees(String fichierDonnees); Et faire des méthode creeCase(),
 * creeRobot(), ... qui lisent les données, créent les objets adéquats et les
 * ajoutent ds l'instance de DonneesSimulation.
 */
public final class LecteurDonnees {

    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases, robots et
     * incendies). Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     * @return les données de simulation contenues dans le fichier
     */
    public static DonneesSimulation lire(final String fichierDonnees) {
        LecteurDonnees lecteur;
        try {
            lecteur = new LecteurDonnees(fichierDonnees);
            Carte carte = lecteur.lireCarte();
            Incendie[] incendie = lecteur.lireIncendies(carte);
            Robot[] robots = lecteur.lireRobots(carte);
            scanner.close();
            return new DonneesSimulation("", carte, incendie, robots);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return new DonneesSimulation("", null, null, null);
    }

    /**
     * Lit et affiche le contenu d'un stream de donnees (cases, robots et
     * incendies).
     * @param input Le stream utilisé.
     * @return les données de simulation contenues dans le stream;
     */
    public static DonneesSimulation lire(final InputStream input) {
        LecteurDonnees lecteur;
        try {
            lecteur = new LecteurDonnees(input);
            Carte carte = lecteur.lireCarte();
            Incendie[] incendie = lecteur.lireIncendies(carte);
            Robot[] robots = lecteur.lireRobots(carte);
            scanner.close();
            return new DonneesSimulation("", carte, incendie, robots);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return new DonneesSimulation("", null, null, null);
    }

    /**
     * Lecteur global de la classe.
     */
    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur.
     * @param fichierDonnees nom du fichier a lire
     * @throws FileNotFoundException
     */
    private LecteurDonnees(final String fichierDonnees)
            throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur.
     * @param inputStream le stream d'entrée.
     * @throws FileNotFoundException
     */
    private LecteurDonnees(final InputStream inputStream)
            throws FileNotFoundException {
        scanner = new Scanner(inputStream);
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     * @return les données de la carte
     */
    private Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt(); // en m
            Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col, carte);
                }
            }
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    /**
     * Lit et affiche les donnees d'une case.
     * @param lig ligne de la case
     * @param col colonne de la case
     * @param carte carte dans laquelle on va stocker les cases.
     */
    private void lireCase(final int lig, final int col, final Carte carte)
            throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        // NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            // NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            carte.getCarte()[col][lig] = new Case(lig, col,
                    NatureTerrain.valueOf(chaineNature));

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

    }

    /**
     * Lit et affiche les donnees des incendies.
     * @param carte carte sur laquelle on va localiser les incendies
     * @return la liste de tous les incendies de la carte
     */
    private Incendie[] lireIncendies(final Carte carte)
            throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            Incendie[] incendie = new Incendie[nbIncendies];
            for (int i = 0; i < nbIncendies; i++) {
                incendie[i] = lireIncendie(i, carte);
            }
            return incendie;

        } catch (NoSuchElementException e) {
            throw new DataFormatException(
                    "Format invalide. " + "Attendu: nbIncendies");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i le numéro de l'incendie
     * @param carte la carte dans laquelle on a les incendies
     * @return l'incendie numéro i
     */
    private Incendie lireIncendie(final int i, final Carte carte)
            throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            Incendie incendie = new Incendie(carte.getCase(col, lig),
                    intensite);
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            return incendie;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Lit et affiche les donnees des robots.
     * @param carte Carte sur laquelle on a les robots
     * @return liste de robot
     */
    private Robot[] lireRobots(final Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robot[] robots = new Robot[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                robots[i] = lireRobot(i, carte);
            }
            return robots;
        } catch (NoSuchElementException e) {
            throw new DataFormatException(
                    "Format invalide. " + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i le robot numéro i
     * @param carte Carte sur laquelle est le robot
     * @return le robot numéro i
     */
    private Robot lireRobot(final int i, final Carte carte)
            throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();
            String lineFound = scanner.findInLine("(\\d+)");
            Robot robot = switch (type) {
            case "DRONE" -> lineFound == null
                    ? new RobotDrone(carte.getCase(col, lig), carte)
                    : new RobotDrone(carte.getCase(col, lig),
                            Integer.parseInt(lineFound), carte);
            case "ROUES" -> new RobotARoues(carte.getCase(col, lig), carte);
            case "PATTES" -> new RoboAPattes(carte.getCase(col, lig), carte);
            case "CHENILLES" -> lineFound == null
                    ? new RobotChenilles(carte.getCase(col, lig), carte)
                    : new RobotChenilles(carte.getCase(col, lig),
                            Integer.parseInt(lineFound), carte);
            default -> null;
            };

            verifieLigneTerminee();

            return robot;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /** Ignore toute (fin de) ligne commencant par '#'. */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
