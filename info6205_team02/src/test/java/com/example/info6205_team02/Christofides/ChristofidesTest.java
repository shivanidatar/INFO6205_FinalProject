package com.example.info6205_team02.Christofides;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChristofidesTest {
    private static final String NAME_OF_OLD_TOUR = "src/test/java/com/example/info6205_team02/Christofides/test-input-";
    private static final String END_OF_OLD_TOUR_2OPT = ".txt.2opt.tour";

    private static final String END_OF_OLD_TOUR_3OPT = ".txt.3opt.tour";
    private static final String NAME_OF_INPUT_FILE = "src/test/java/com/example/info6205_team02/Christofides/test-input-";
    private static final String END_OF_FILE = ".txt";

    @Test
    public void resultsFromFirstTourAreTheSame2OPT() throws IOException {
        for (int i = 1; i < 3; i++) {
            String oldTourFile = NAME_OF_OLD_TOUR + i + END_OF_OLD_TOUR_2OPT;
            String newTourFile = NAME_OF_INPUT_FILE + i + END_OF_FILE;
            System.out.println(oldTourFile);
            ChristofidesTour oldChristofidesTour = parseAnswer(oldTourFile);
            ChristofidesTour newChristofidesTour = Driver.christofidesAlgorithmTwoOpt(newTourFile, 0);
            System.out.println(Math.round(oldChristofidesTour.getTourCost()));
            System.out.println(Math.round(newChristofidesTour.getTourCost()));
           // System.out.println();
            assertEquals(Math.round(oldChristofidesTour.getTourCost()), Math.round(newChristofidesTour.getTourCost()));
            assertEqualsArray(oldChristofidesTour.getFinalTour(), newChristofidesTour.getFinalTour());
        }
    }
@Test
    public void resultsFromFirstTourAreTheSame3OPT() throws IOException {
        for (int i = 1; i < 3; i++) {
            String oldTourFile = NAME_OF_OLD_TOUR + i + END_OF_OLD_TOUR_3OPT;
            String newTourFile = NAME_OF_INPUT_FILE + i + END_OF_FILE;
            System.out.println(oldTourFile);
            ChristofidesTour oldChristofidesTour = parseAnswer(oldTourFile);
            ChristofidesTour newChristofidesTour = Driver.christofidesAlgorithmThreeOpt(newTourFile, 0);
            System.out.println(Math.round(oldChristofidesTour.getTourCost()));
            System.out.println(Math.round(newChristofidesTour.getTourCost()));
            // System.out.println();
            assertEquals(Math.round(oldChristofidesTour.getTourCost()), Math.round(newChristofidesTour.getTourCost()));
            assertEqualsArray(oldChristofidesTour.getFinalTour(), newChristofidesTour.getFinalTour());
        }
    }
    private static void assertEqualsArray(List<Integer> listOne, List<Integer> listTwo) {
        for (int i = 1; i < listOne.size(); i++) {
            assertTrue(listOne.get(i).equals(listTwo.get(i)));
        }
        for (int i = 1; i < listTwo.size(); i++) {
            assertTrue(listOne.get(i).equals(listTwo.get(i)));
        }
    }

    private static ChristofidesTour parseAnswer(String filePath) throws IOException {
        try(Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<Double>  finalTour = stream.map(Double::parseDouble)
                    .collect(Collectors.toCollection(ArrayList::new));
            double totalDistance = (double)finalTour.get(0);
            finalTour.remove(0);
            List<Integer> finalTour2 = new ArrayList<>();
            for(int i=0;i<finalTour.size();i++){
                finalTour2.add((int)(Math.round(finalTour.get(i))));
            }
            return new ChristofidesTour(finalTour2, totalDistance);
        }


    }

}
