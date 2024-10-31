package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;

public final class ClosestInsertionTour implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        final int N = data.getNumberOfCities();

        var tools = new TourBuildTools();

        // todo : rename
        int[] closestCity = new int[N];
        boolean[] citiesInTour = new boolean[N];

        var tourList = new ArrayList<Integer>();
        int length = 0;

        // Ajout du premier sommet
        tourList.add(startCityIndex);
        citiesInTour[startCityIndex] = true;

        for(int i = 0; i < data.getNumberOfCities(); ++i) {
            if(i == startCityIndex)
                continue;
            closestCity[i] = tools.getClosestCityOnTour(i, data, citiesInTour, false);
        }

        while(tourList.size() < N) {
            // Recherche de la ville hors tournée la plus proche d'une ville dans la tournée
            var nextCity = tools.getClosestCityOfAll(data, closestCity, citiesInTour, false);
            citiesInTour[nextCity] = true;

            // Met à jour
            tools.updateClosestCity(nextCity, data, citiesInTour, closestCity, false);

            tools.insertNewCityAtBestIndex(nextCity, tourList, data);

            length += data.getDistance(nextCity, closestCity[nextCity]);
            observer.update(new TraversalIterator(tourList));
        }

        var tour = tools.convertTourListToArray(tourList);
        return new TspTour(data, tour, length);
    }
}
