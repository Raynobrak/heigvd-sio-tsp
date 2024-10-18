package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class RandomTour implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        Random rng = new Random(Analyze.RNG_SEED);

        var insertionOrder = new ArrayList<Integer>();
        for(int i = 0; i < data.getNumberOfCities(); ++i)
            insertionOrder.add(i);
        Collections.shuffle(insertionOrder, rng);

        int insertedCitiesCount = 0;
        var tourList = new ArrayList<Integer>();
        int length = 0;

        for(var cityIndex : insertionOrder) {
            // Ajout du premier sommet
            if(insertedCitiesCount == 0) {
                tourList.add(cityIndex);
            }
            else {
                length += new TourBuildTools().insertNewCityAtBestIndex(cityIndex, tourList, data);
                observer.update(new TraversalIterator(tourList));
            }
            ++insertedCitiesCount;
        }

        // Création du tableau final de la tournée
        var tour = new int[tourList.size()];
        for(int i = 0; i < tour.length; ++i)
            tour[i] = tourList.get(i);

        return new TspTour(data, tour, length);
    }
}
