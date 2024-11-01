package ch.heig.sio.lab1.groupH;

import java.io.FileNotFoundException;

import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspParsingException;

public final class Analyze {
  public static final long RNG_SEED = 0x134DA73;

  public static void main(String[] args) throws TspParsingException, FileNotFoundException {
    // TODO
    //  - Renommer le package ;
    //  - Implémenter les différentes heuristiques en écrivant le code dans ce package, et uniquement celui-ci
    //    (sous-packages et packages de tests ok) ;
    //  - Factoriser le code commun entre les différentes heuristiques ;
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.

    // Longueurs optimales :
    // pcb442 : 50778
    // att532 : 86729
    // u574 : 36905
    // pcb1173   : 56892
    // nrw1379  : 56638
    // u1817 : 57201

    // Exemple de lecture d'un jeu de données :
    //TspData data = TspData.fromFile("data/att532.dat");
    
     long optimales[] = {50778, 86729, 36905, 56892, 56638, 57201};
     String[] datas = {"pcb442", "att532", "u574", "pcb1173", "nrw1379", "u1817"};

     // Pour chaque jeu de données
     for (int j = 0; j < datas.length; j++) {
      TspData data = TspData.fromFile("data/" + datas[j] + ".dat");

      long minLength = Integer.MAX_VALUE;
      long maxLength = Integer.MIN_VALUE;
      long meanLength = 0;

      int N = data.getNumberOfCities(); // Nombre de villes

      // Calcul de la longueur minimale, maximale et moyenne de l'heuristic RandomTourBuilder pour chaque ville de départ
      for (int i = 0; i < N; i++) {
        RandomTourBuilder randomTourBuilder = new RandomTourBuilder();
        long length = randomTourBuilder.computeTour(data, i).length();

        meanLength += length;
        if (length < minLength) {
          minLength = length;
        }

        if (length > maxLength) {
          maxLength = length;
        }
      }

      meanLength /= N;

      // Statistiques
      double relativeMin = ((minLength - optimales[j]) / (double) optimales[j]) * 100;

      // Affichage des résultats
      System.out.println("RandomTourBuilder: " + datas[j]);
      System.out.println("Min: " + minLength);
      System.out.println("Max: " + maxLength);
      System.out.println("Mean: " + meanLength);
      System.out.println("Optimal: " + optimales[j]);
      System.out.println("Ecart relatif: " + relativeMin + "%");
      System.out.println();
     }
  }

}
