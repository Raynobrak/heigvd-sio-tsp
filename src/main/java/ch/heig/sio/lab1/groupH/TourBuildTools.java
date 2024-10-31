package ch.heig.sio.lab1.groupH;
import ch.heig.sio.lab1.tsp.TspData;
import java.util.ArrayList;

public class TourBuildTools {

    TourBuildTools() {}

    int[] convertTourListToArray(ArrayList<Integer> tourList) {
        return tourList.stream().mapToInt(Integer::intValue).toArray();
    }

    // Retourne le coût d'insertion du sommet "intermediate" entre les sommets first et second
    int getInsertionCost(int first, int second, int intermediate, TspData data) {
        int distance = data.getDistance(first, second);
        int intermediateDistance1 = data.getDistance(first, intermediate);
        int intermediateDistance2 = data.getDistance(intermediate, second);
        return intermediateDistance1 + intermediateDistance2 - distance;
    }

    // Insère la ville donnée au meilleur endroit de la tournée et retourne la
    // distance supplémentaire du détour.
    int insertNewCityAtBestIndex(int cityIndex, ArrayList<Integer> tourList, TspData data){
        int smallestInsertionCost = Integer.MAX_VALUE;
        int currentBestInsertionIndex = Integer.MAX_VALUE;
        int insertedCitiesCount = tourList.size();

        // todo : changer ce parcours par un itérateur sur tourList
        for(int current = 0; current < insertedCitiesCount; ++current) {
            int next = (current + 1) % insertedCitiesCount;

            int cost = getInsertionCost(tourList.get(current), tourList.get(next), cityIndex, data);
            if(cost < smallestInsertionCost) {
                smallestInsertionCost = cost;
                currentBestInsertionIndex = (current + 1) % insertedCitiesCount;
            }
        }

        tourList.add(currentBestInsertionIndex, cityIndex);

        return smallestInsertionCost;
    }

    // todo : refactor
    int getClosestCityOnTour(int cityIndex, TspData data, boolean[] visited, boolean furthest) { //furthest ne sert à rien
        int closestCityDistance = furthest ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int closestCityIndex = -1;

        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            if(i == cityIndex || !visited[i])
                continue;

            int distance = data.getDistance(cityIndex, i);
            if(furthest && distance > closestCityDistance || !furthest && distance < closestCityDistance) {
                closestCityDistance = distance;
                closestCityIndex = i;
            }
        }

        return closestCityIndex;
    }

    int getClosestCityOfAll(TspData data, int[] closestCityInTour, boolean[] visited, boolean furthest)
    {
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
    void updateClosestCity(int addedCityIndex, TspData data, boolean[] visited, int[] closestCityInTour, boolean furthest) { //furthest ne sert à rien
        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            // On ne parcours que les villes hors-tournées
            if(visited[i])
                continue;

            // On regarde si la ville nouvellement ajoutée à la tournée est plus proche que la ville retenue précédemment dans l'index
            int currentDistance = data.getDistance(i, closestCityInTour[i]);
            int distance = data.getDistance(addedCityIndex, i);
            if(furthest && distance > currentDistance || !furthest && distance < currentDistance) {
                closestCityInTour[i] = addedCityIndex;
            }
        }
    }
}
