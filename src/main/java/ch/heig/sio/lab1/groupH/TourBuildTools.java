package ch.heig.sio.lab1.groupH;
import ch.heig.sio.lab1.tsp.TspData;
import java.util.ArrayList;

public class TourBuildTools {

    TourBuildTools() {}

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
}
