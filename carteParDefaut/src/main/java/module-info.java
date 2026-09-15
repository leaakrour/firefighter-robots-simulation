open module app.carteParDefaut {
    requires app.data;
    
    provides org.app.data.DonneesSimulation
            with org.carteSujet.CarteSujet,
            org.carteSujet.DesertOfTheDeath,
            org.carteSujet.MushroomOfTheHell,
            org.carteSujet.SpiralOfMadness;
}
