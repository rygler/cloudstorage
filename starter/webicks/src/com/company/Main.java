package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int[] planets = new int[] {2, 4, 6, 3, 4};
        List<Integer> planetList = new ArrayList<>(planets.length);
        for (int planet: planets) {
            planetList.add(planet);
        }
        System.out.println("List: " + planetList);
        System.out.println(getPlanetToDestroy(planetList));
    }

    public static int getPlanetToDestroy(List<Integer> planets) {
        // Write your code here
        int sumOfOddPlanets = 0, sumOfEvenPlanets = 0;
        for (int i = 0; i < planets.size(); i++) {
            int currentPlanetValue = planets.get(i);
            if (i % 2 == 0) {
                sumOfEvenPlanets += currentPlanetValue;
            } else {
                sumOfOddPlanets += currentPlanetValue;
            }
        }
        if (Math.abs(sumOfEvenPlanets - sumOfOddPlanets) == 0) {
            return -1;
        }

        int indexOfPlanetToDestroy = -1;
        int currentMinDiff = -1;
        List<Integer> tempPlanets;
        for (int i = 0; i < planets.size(); i++) {
            tempPlanets = new ArrayList<>(planets);
            Collections.copy(tempPlanets, planets);
            tempPlanets.remove(i);
            for (int j = 0; j < tempPlanets.size(); j++) {
                int currentPlanetValue = tempPlanets.get(j);
                if (j % 2 == 0) {
                    sumOfEvenPlanets += currentPlanetValue;
                } else {
                    sumOfOddPlanets += currentPlanetValue;
                }
            }

            int localMinDiff = Math.abs(sumOfEvenPlanets - sumOfOddPlanets);
            if (i == 0 || localMinDiff < currentMinDiff) {
                currentMinDiff = localMinDiff;
                indexOfPlanetToDestroy = i;
            }
            sumOfEvenPlanets = 0;
            sumOfOddPlanets = 0;
        }

        return indexOfPlanetToDestroy + 1;
    }
}
