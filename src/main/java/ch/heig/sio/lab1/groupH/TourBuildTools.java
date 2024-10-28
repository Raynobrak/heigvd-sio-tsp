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

    Distance getClosestCityOnTour(int cityIndex, TspData data, boolean[] visited) {
        int closestCity = -1;
        int closestDistance = Integer.MAX_VALUE;

        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            if(i == cityIndex || !visited[i])
                continue;

            int distance = data.getDistance(cityIndex, i);
            if(distance < closestDistance) {
                closestDistance = distance;
                closestCity = i;
            }
        }

        return new Distance(closestCity, closestDistance);
    }

    Distance getSmallestDistance(Distance[] distances, boolean[] visited)
    {
        int smallestDistance = Integer.MAX_VALUE;
        int smallestIndex = -1;

        for(int i = 0; i < distances.length; ++i) {
            if(visited[i])
                continue;

            if(distances[i].distance() < smallestDistance) {
                smallestDistance = distances[i].distance();
                smallestIndex = i;
            }
        }

        return new Distance(smallestIndex, smallestDistance);
    }

    void updateClosestCity(int addedCityIndex, TspData data, boolean[] visited, Distance[] closestCity) {
        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            if(i == addedCityIndex || visited[i])
                continue;

            int distance = data.getDistance(addedCityIndex, i);
            if(distance < closestCity[i].distance()) {
                closestCity[i] = new Distance(addedCityIndex, distance);
            }
        }
    }
}
