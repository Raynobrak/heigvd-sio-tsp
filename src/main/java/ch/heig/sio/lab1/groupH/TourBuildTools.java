package ch.heig.sio.lab1.groupH;
import ch.heig.sio.lab1.tsp.TspData;
import java.util.ArrayList;

public class TourBuildTools {

    TourBuildTools() {}
    
    int getInsertionCost(int first, int second, int intermediate, TspData data) {
        int distance = data.getDistance(first, second);
        int intermediateDistance1 = data.getDistance(first, intermediate);
        int intermediateDistance2 = data.getDistance(intermediate, second);
        return intermediateDistance1 + intermediateDistance2 - distance;
    }

    //Retourne le cout
    int insertNewCityAtBestIndex(int cityIndex, ArrayList<Integer> tourList, TspData data){
        int smallestInsertionCost = Integer.MAX_VALUE;
        int currentBestInsertionIndex = Integer.MAX_VALUE;
        int insertedCitiesCount = tourList.size();

        for(int i = 0; i < insertedCitiesCount; ++i) {
            var current = i;
            var next = i + 1;
            if(next == insertedCitiesCount)
                next = 0;

            var distance = data.getDistance(tourList.get(current), tourList.get(next));
            var intermediateDistance1 = data.getDistance(tourList.get(current), cityIndex);
            var intermediateDistance2 = data.getDistance(cityIndex, tourList.get(next));
            var cost = intermediateDistance1 + intermediateDistance2 - distance;

            if(cost < smallestInsertionCost) {
                smallestInsertionCost = cost;
                currentBestInsertionIndex = (i + 1) % insertedCitiesCount;
            }
        }

        tourList.add(currentBestInsertionIndex, cityIndex);

        return smallestInsertionCost;
    }
}
