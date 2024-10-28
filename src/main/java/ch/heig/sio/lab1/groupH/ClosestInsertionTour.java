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
        
        TourBuildTools tools = new TourBuildTools();

        Distance[] closestCity = new Distance[data.getNumberOfCities()];
        boolean[] visited = new boolean[data.getNumberOfCities()];


        var tourList = new ArrayList<Integer>();
        int length = 0;

        // Ajout du premier sommet
        tourList.add(startCityIndex);
        visited[startCityIndex] = true;

        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            if(i == startCityIndex)
                continue;

            closestCity[i] = tools.getClosestCityOnTour(startCityIndex, data, visited);
        }


        for (int i = 0; i < data.getNumberOfCities() - 1; ++i) {
            var nextCity = tools.getSmallestDistance(closestCity, visited);
            visited[nextCity.cityIndex()] = true;

            tools.updateClosestCity(nextCity.cityIndex(), data, visited, closestCity);

            tools.insertNewCityAtBestIndex(nextCity.cityIndex(), tourList, data);

            length += nextCity.distance();
            observer.update(new TraversalIterator(tourList));
        }

        // Création du tableau final de la tournée
        var tour = new int[tourList.size()];
        for(int i = 0; i < tour.length; ++i)
            tour[i] = tourList.get(i);

        return new TspTour(data, tour, length);


    }
}
