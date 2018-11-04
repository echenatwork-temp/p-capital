package portfolio.simulator.main;

import javafx.util.Pair;
import portfolio.simulator.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Main method to run to gather the requested statistics
     *
     * I elected to have the median be treated as the 50th percentile, because in large data sets it should roughly similar to finding the precise median
     *  and I didn't need to special case the median vs other percentile calculations
     *
     */
    public static void main(String[] args) {

        // seed can be set to a set number for debugging
        ReturnsGenerator aggressiveRandomNumberGenerator = new GaussianRandomNumberGenerator(System.currentTimeMillis(), new BigDecimal(".094324"), new BigDecimal(".15675"));
        ReturnsGenerator conservativeRandomNumberGenerator = new GaussianRandomNumberGenerator(System.currentTimeMillis(), new BigDecimal("0.06189"), new BigDecimal("0.063438"));

        BigDecimal initialAmount = new BigDecimal("100000");
        int years = 20;
        int numSimulations = 10000;
        PortfolioSimulator portfolioSimulator = new PortfolioSimulator();


        // executing the simulations
        SimulationRequest aggressiveRequest = createSimulationRequest(aggressiveRandomNumberGenerator, initialAmount, years, numSimulations);
        SimulationResult aggressiveResult = portfolioSimulator.simulate(aggressiveRequest);

        SimulationRequest conservativeRequest = createSimulationRequest(conservativeRandomNumberGenerator, initialAmount, years, numSimulations);
        SimulationResult conservativeResult = portfolioSimulator.simulate(conservativeRequest);

        // analyzing the results
        BigDecimal inflation = new BigDecimal("0.035");
        List<Integer> percentiles = new ArrayList<>();
        percentiles.add(50);
        percentiles.add(90);
        percentiles.add(10);

        SimulatorResultAnalyzer resultAnalyzer = new SimulatorResultAnalyzer();
        SimulationResultSummary aggressiveSummary = resultAnalyzer.createSummary(aggressiveResult, percentiles, inflation);
        SimulationResultSummary conservativeSummary = resultAnalyzer.createSummary(conservativeResult, percentiles, inflation);

        // printing out the results
        printResults(aggressiveSummary, "Aggressive Portfolio - Normalized for Inflation");
        printResults(conservativeSummary, "Very Conservative Portfolio - Normalized for Inflation");
    }

    private static SimulationRequest createSimulationRequest(ReturnsGenerator numberGenerator, BigDecimal initialAmount, int years, int numSimulations) {
        SimulationRequest request = new SimulationRequest();
        request.setInitialAmount(initialAmount);
        request.setIterations(years);
        request.setNumSimulations(numSimulations);
        request.setReturnsGenerator(numberGenerator);
        return request;
    }

    private static void printResults(SimulationResultSummary summary, String title) {
        System.out.println(title);
        for (Pair<Integer, BigDecimal> statistic : summary.getSimulationStatistics()) {
            System.out.println(statistic.getKey() + "th Percentile: " + Math.round(statistic.getValue().doubleValue()));
        }
        System.out.println("-----");
    }


}
