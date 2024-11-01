package ch.heig.sio.lab1.groupH;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Arrays;

/// Constructeur de tournée basé sur la distance
/// Permet de construire une tournée avec une insertion "la plus proche" ou "la plus éloignée"
/// @author Group H - Ancay Rémi, Charbonnier Lucas
public final class DistanceBasedTourBuilder implements ObservableTspConstructiveHeuristic {
    private boolean furthestInsertion;
    public DistanceBasedTourBuilder(boolean furthestInsertion) {
        this.furthestInsertion = furthestInsertion; // Détermine si on veut une "Insertion la plus éloignée" ou "Insertion la plus proche"
    }

    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        final int N = data.getNumberOfCities(); // Nombre de villes
        var tools = new TourBuildingUtils(); // Outils pour la construction de la tournée
        int[] closestCityInTour = new int[N]; // Ville la plus proche de chaque ville dans la tournée
        boolean[] citiesInTour = new boolean[N]; // Villes déjà dans la tournée
        var tourList = new ArrayList<Integer>(); // Liste des villes de la tournée

        // Ajout de la première ville
        tourList.add(startCityIndex);
        citiesInTour[startCityIndex] = true;

        // Au départ, la ville de la tournée la plus proche est forcément startCityIndex (vu que c'est la seule)
        Arrays.fill(closestCityInTour, startCityIndex);

        int length = 0;
        while(tourList.size() < N) {
            // Recherche de la ville hors tournée la plus proche d'une ville dans la tournée
            var nextCity = tools.getClosestCityOfAll(data, closestCityInTour, citiesInTour, furthestInsertion);
            citiesInTour[nextCity] = true;

            // Ajout de la ville à l'endroit minimisant le coût
            length += tools.insertNewCityAtBestIndex(nextCity, tourList, data);

            // Mise à jour de la ville la plus proche à chaque ville hors-tournée
            tools.updateClosestCity(nextCity, data, citiesInTour, closestCityInTour);

            observer.update(new TraversalIterator(tourList));
        }

        var tour = tools.convertTourListToArray(tourList);
        return new TspTour(data, tour, length);
    }
}
