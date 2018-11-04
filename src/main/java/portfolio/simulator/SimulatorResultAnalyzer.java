package portfolio.simulator;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimulatorResultAnalyzer {

    // Assuming we don't want higher precision 0.01
    private static final int PRECISION_SCALE = 2;

    /**
     *
     * @param simulationResult
     * @param percentiles specified percentiles (10 -> 10th percentile, 50 -> 50th percentile, etc.) Should be between 1 and 100 inclusive
     * @param annualInflation should be expressed as 5% inflation -> 0.05, 200% inflation -> 2.00, 5% deflation -> -0.05 . Set as null to skip normalizing values
     * @return
     */
    public SimulationResultSummary createSummary(SimulationResult simulationResult, List<Integer> percentiles, BigDecimal annualInflation) {

        // 100% deflation or greater doesn't make sense - results would go to zero, also prevents divide by zero error
        if (annualInflation != null && annualInflation.compareTo(BigDecimal.ONE.negate()) <= 0) {
            throw new RuntimeException("Invalid value for annual inflation: " + annualInflation.toPlainString() + " must be > -1.0");
        }
        List<BigDecimal> rawAmounts = simulationResult.getFinalAmounts();
        rawAmounts.sort(Comparator.naturalOrder());

        SimulationResultSummary simulationResultSummary = new SimulationResultSummary();
        simulationResultSummary.setSimulationStatistics(new ArrayList<>());

        for  (Integer percentile : percentiles) {
            int percentileIndex = (int)Math.ceil(percentile * rawAmounts.size()/100.0) - 1;
            if (percentileIndex < 0 || percentileIndex >= rawAmounts.size()) {
                throw new RuntimeException("Percentiles should be between 1 and 100 inclusive");
            }

            BigDecimal rawAmount = rawAmounts.get(percentileIndex);
            BigDecimal percentileValue = normalizeForInflation(rawAmount, annualInflation, simulationResult.getYearsSimulated());
            simulationResultSummary.getSimulationStatistics().add(new Pair<>(percentile, percentileValue));
        }

        return simulationResultSummary;
    }



    private BigDecimal normalizeForInflation(BigDecimal rawValue, BigDecimal annualInflation, int years) {
        if (annualInflation == null) {
            return rawValue;
        }
        BigDecimal normalizationFactor = (annualInflation.add(BigDecimal.ONE)).pow(years);
        return rawValue.divide(normalizationFactor, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    }

}
