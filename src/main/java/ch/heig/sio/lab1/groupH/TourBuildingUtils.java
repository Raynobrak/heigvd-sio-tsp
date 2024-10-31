package ch.heig.sio.lab1.groupH;
import ch.heig.sio.lab1.tsp.TspData;
import java.util.ArrayList;

public class TourBuildingUtils {
    // Convertit une ArrayList d'entiers en tableau d'entiers
    static int[] convertTourListToArray(ArrayList<Integer> tourList) {
        return tourList.stream().mapToInt(Integer::intValue).toArray();
    }

    // Retourne le coût d'insertion du sommet "intermediate" entre les sommets first et second
    // En d'autres termes, le coût du détour impliqué par l'ajout d'un sommet intermédiaire entre deux autres sommets
    static int getInsertionCost(int first, int second, int intermediate, TspData data) {
        int distance = data.getDistance(first, second);
        int intermediateDistance1 = data.getDistance(first, intermediate);
        int intermediateDistance2 = data.getDistance(intermediate, second);
        return intermediateDistance1 + intermediateDistance2 - distance;
    }

    // Insère la ville donnée au meilleur endroit de la tournée
    // @return la distance en plus qu'implique l'ajout de la nouvelle ville
    static int insertNewCityAtBestIndex(int newCity, ArrayList<Integer> tourList, TspData data){
        int smallestInsertionCost = Integer.MAX_VALUE;
        int currentBestInsertionIndex = Integer.MAX_VALUE;
        int insertedCitiesCount = tourList.size();

        // todo : changer ce parcours par un itérateur sur tourList
        for(int current = 0; current < insertedCitiesCount; ++current) {
            int next = (current + 1) % insertedCitiesCount;

            int cost = getInsertionCost(tourList.get(current), tourList.get(next), newCity, data);
            if(cost < smallestInsertionCost) {
                smallestInsertionCost = cost;
                currentBestInsertionIndex = (current + 1) % insertedCitiesCount;
            }
        }

        tourList.add(currentBestInsertionIndex, newCity);
        return smallestInsertionCost;
    }

    // Retourne la ville hors-tournée la plus proche d'une ville de la tournée
    static int getClosestCityOfAll(TspData data, int[] closestCityInTour, boolean[] visited, boolean furthest) {
        int bestDistance = furthest ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestCityIndex = -1;

        for(int i = 0; i < closestCityInTour.length; ++i) {
            if(visited[i])
                continue;

            int distance = data.getDistance(i, closestCityInTour[i]);
            if(furthest && distance > bestDistance || !furthest && distance < bestDistance) {
                bestDistance = distance;
                bestCityIndex = i;
            }
        }
        return bestCityIndex;
    }

    // Met à jour le tableau des villes de la tournée les plus proches des villes hors tournées
    // `furthest = true` pour faire l'équivalent mais avec les villes les plus éloignées
    static void updateClosestCity(int addedCityIndex, TspData data, boolean[] visited, int[] closestCityInTour) {
        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            // On ne parcours que les villes hors-tournées
            if(visited[i])
                continue;

            // On regarde si la ville nouvellement ajoutée à la tournée est plus proche que la ville retenue précédemment
            int currentDistance = data.getDistance(i, closestCityInTour[i]);
            int distance = data.getDistance(i, addedCityIndex);

            // On met à jour la distance si c'est mieux
            if(distance < currentDistance)
                closestCityInTour[i] = addedCityIndex;
        }
    }
}
