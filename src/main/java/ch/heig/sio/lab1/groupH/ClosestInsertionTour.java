package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public final class ClosestInsertionTour implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        final int N = data.getNumberOfCities();

        var tools = new TourBuildTools();

        int[] closestCityInTour = new int[N];
        boolean[] citiesInTour = new boolean[N];

        var tourList = new ArrayList<Integer>();

        // Ajout de la première ville
        tourList.add(startCityIndex);
        citiesInTour[startCityIndex] = true;

        // Au départ, la ville de la tournée la plus proche est forcément startCityIndex (vu que c'est la seule)
        Arrays.fill(closestCityInTour, startCityIndex);

        int length = 0;
        while(tourList.size() < N) {
            // Recherche de la ville hors tournée la plus proche d'une ville dans la tournée
            var nextCity = tools.getClosestCityOfAll(data, closestCityInTour, citiesInTour, false);
            citiesInTour[nextCity] = true;

            // Ajout de la ville à l'endroit minimisant le coût
            tools.insertNewCityAtBestIndex(nextCity, tourList, data);

            length += data.getDistance(nextCity, closestCityInTour[nextCity]);
            observer.update(new TraversalIterator(tourList));

            // Mise à jour de la ville la plus proche à chaque ville hors-tournée
            tools.updateClosestCity(nextCity, data, citiesInTour, closestCityInTour, false);
        }

        var tour = tools.convertTourListToArray(tourList);
        return new TspTour(data, tour, length);
    }
}
