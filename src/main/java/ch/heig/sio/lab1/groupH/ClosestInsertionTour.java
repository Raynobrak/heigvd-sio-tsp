package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

public final class ClosestInsertionTour implements ObservableTspConstructiveHeuristic {
    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        return null;
    }
}
