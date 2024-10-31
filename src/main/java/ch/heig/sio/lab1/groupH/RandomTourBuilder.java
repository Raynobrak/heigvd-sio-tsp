package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class RandomTourBuilder implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        Random rng = new Random(Analyze.RNG_SEED);

        // Génération d'un ordre aléatoire mais qui commence par startCityIndex
        var insertionOrder = new ArrayList<Integer>();
        for(int i = 0; i < data.getNumberOfCities(); ++i)
            if(i != startCityIndex)
                insertionOrder.add(i);
        Collections.shuffle(insertionOrder, rng);
        insertionOrder.add(0, startCityIndex);

        // Liste contenant les villes de la tournée, dans l'ordre
        var tourList = new ArrayList<Integer>();

        // Ajout séquentiel de toutes les villes, selon l'ordre d'insertion
        int length = 0;
        for(var cityIndex : insertionOrder) {
            // Ajout de la ville à l'endroit minimisant le coût
            length += new TourBuildingUtils().insertNewCityAtBestIndex(cityIndex, tourList, data);
            observer.update(new TraversalIterator(tourList));
        }

        int[] tour = new TourBuildingUtils().convertTourListToArray(tourList);
        return new TspTour(data, tour, length);
    }
}
