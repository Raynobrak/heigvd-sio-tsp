package ch.heig.sio.lab1.groupH;

import java.io.FileNotFoundException;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspParsingException;

public final class Analyze {
  public static final long RNG_SEED = 0x134DA73; // Graine pour le générateur de nombres aléatoires

  /// Analyse les différentes heuristiques de construction de tournée pour différents jeux de données
  /// Affiche certaines statistiques sur les tournées générées
  /// @author Group H - Ancay Rémi, Charbonnier Lucas
  public static void main(String[] args) throws TspParsingException, FileNotFoundException {   
    long optimales[] = {50778, 86729, 36905, 56892, 56638, 57201}; // Longueurs optimales des tournées
    String[] datas = {"pcb442", "att532", "u574", "pcb1173", "nrw1379", "u1817"}; // Jeux de données
    String[] builderNames = {"RandomTourBuilder", "NearestInsertion", "FurthestInsertion"}; // Constructeurs de tournée

    // Pour chaque jeu de données
    for (int j = 0; j < datas.length; j++) {
        TspData data = TspData.fromFile("data/" + datas[j] + ".dat");
        int N = data.getNumberOfCities(); // Nombre de villes dans le jeu de données
    
        // Boucle sur les différents constructeurs de tournée
        for (String builderName : builderNames) {
            long minLength = Integer.MAX_VALUE; // Longueur minimale
            long maxLength = Integer.MIN_VALUE; // Longueur maximale
            long meanLength = 0; // Longueur moyenne
            long[] lengths = new long[N]; // Stocke les longueurs de chaque tournée pour calculer l'écart-type
    
            // Sélection du constructeur de tournée
            ObservableTspConstructiveHeuristic builder;
            if (builderName.equals("RandomTourBuilder")) {
                builder = new RandomTourBuilder();
            } else if (builderName.equals("NearestInsertion")) {
                builder = new DistanceBasedTourBuilder(false);
            } else {
                builder = new DistanceBasedTourBuilder(true);
            }
    
            // Calcul de la longueur minimale, maximale et moyenne pour chaque ville de départ
            for (int i = 0; i < N; i++) {
                long length = builder.computeTour(data, i).length();
                lengths[i] = length;

                meanLength += length;
    
                if (length < minLength)
                  minLength = length;
    
                if (length > maxLength) 
                  maxLength = length;
            }
    
            // Calcul de la longueur moyenne
            meanLength /= N;
    
            // Calcul de l'écart-type
            double sumSquaredDifferences = 0.0;
            for (int i = 0; i < N; i++) {
                sumSquaredDifferences += Math.pow(lengths[i] - meanLength, 2);
            }
            double variance = sumSquaredDifferences / N;
            double sd = Math.sqrt(variance); // Ecart-type
    
            // Calcul de l'écart relatif
            double relativeMin = ((minLength - optimales[j]) / (double) optimales[j]) * 100;
    
            // Affichage des résultats
            System.out.println(builderName + ": " + datas[j]);
            System.out.println("Min: " + minLength);
            System.out.println("Max: " + maxLength);
            System.out.println("Mean: " + meanLength);
            System.out.println("Optimal: " + optimales[j]);
            System.out.println("Ecart relatif: " + relativeMin + "%");
            System.out.println("Ecart-type: " + sd);
            System.out.println();
        }
    }
  }
}
