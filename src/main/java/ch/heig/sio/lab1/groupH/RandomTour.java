package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public final class RandomTour implements ObservableTspConstructiveHeuristic {
    private Random rng = new Random(Analyze.RNG_SEED);

    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    private int randomCityIndex(int citiesCount) {
        return rng.nextInt(citiesCount);
    }

    private int selectRandomCity(boolean[] availableCities) {
        int index = 0;
        do {
            index = randomCityIndex(availableCities.length);
        } while(!availableCities[index]);

        availableCities[index] = false;
        return index;
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        var availableCities = new boolean[data.getNumberOfCities()];
        Arrays.fill(availableCities, true);

        var insertionOrder = new ArrayList<Integer>();
        for(int i = 0; i < availableCities.length; ++i)
            insertionOrder.add(i);
        Collections.shuffle(insertionOrder);

        int insertedCitiesCount = 0;
        var tourList = new ArrayList<Integer>();
        int length = 0;

        for(var cityIndex : insertionOrder) {
            // Ajouter les 3 premiers sommets aléatoirement
            if(insertedCitiesCount == 0) {
                tourList.add(cityIndex);
            }
            else {
                // Coût de l'insertion du nouveau sommet entre le i'ème et le i+1'ème sommet de la tournée actuelle
                int smallestInsertionCost = Integer.MAX_VALUE;
                int currentBestInsertionIndex = 0;
                for(int i = 0; i < insertedCitiesCount; ++i) {
                    var current = i;
                    var next = i + 1;
                    if(next == insertedCitiesCount)
                        next = 0;

                    var distance = data.getDistance(tour[current], tour[next]);
                    var intermediateDistance1 = data.getDistance(tour[current], cityIndex);
                    var intermediateDistance2 = data.getDistance(cityIndex, tour[next]);
                    var cost = intermediateDistance1 + intermediateDistance2 - distance;

                    if(cost < smallestInsertionCost) {
                        smallestInsertionCost = cost;
                        currentBestInsertionIndex = i;
                    }
                }



            }

            ++insertedCitiesCount;
        }

        return null;
    }
}
