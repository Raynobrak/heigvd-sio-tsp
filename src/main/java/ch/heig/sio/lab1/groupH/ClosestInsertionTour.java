package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class ClosestInsertionTour implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        final int citiesCount = data.getNumberOfCities();

        var closestCities = new ArrayList<ArrayList<Distance>>(citiesCount);
        for(int city = 0; city < citiesCount; ++city) {
            var newList = new ArrayList<Distance>(citiesCount);
            for(int otherCity = 0; otherCity < citiesCount; ++otherCity)
                newList.add(new Distance(otherCity, data.getDistance(city, otherCity)));
            newList.sort(new Comparator<Distance>() {
                @Override
                public int compare(Distance lhs, Distance rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.distance() - rhs.distance();
                }
            });
        }

        // todo : use an array instead
        var insertionOrder = new ArrayList<Integer>();
        for(int i = 0; i < citiesCount; ++i) {
            if(insertionOrder.isEmpty())
                insertionOrder.add(startCityIndex);

            // todo : ajouter la ville la plus proche
        }

        return null;
    }
}
