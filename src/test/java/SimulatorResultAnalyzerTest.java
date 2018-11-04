import org.junit.Test;
import portfolio.simulator.SimulationResult;
import portfolio.simulator.SimulationResultSummary;
import portfolio.simulator.SimulatorResultAnalyzer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimulatorResultAnalyzerTest {


    @Test
    public void createSummary_handlesBadInflationValue() {
        // setup
        SimulatorResultAnalyzer fixture = new SimulatorResultAnalyzer();

        // execute
        Exception actual = null;
        try {
            fixture.createSummary(null, null, new BigDecimal("-1.00"));
        } catch (Exception e) {
            actual = e;
        }

        // verify
        assertNotNull(actual);
        assertEquals("Invalid value for annual inflation: -1.00 must be > -1.0", actual.getMessage());
    }

    @Test
    public void createSummary_doesNotNormalizeInflationIfAnnualInflationNull() {
        // setup
        SimulatorResultAnalyzer fixture = new SimulatorResultAnalyzer();

        List<BigDecimal> finalAmounts = new ArrayList<>();
        finalAmounts.add(new BigDecimal("100"));
        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setYearsSimulated(1);
        simulationResult.setFinalAmounts(finalAmounts);

        List<Integer> percentiles = new ArrayList<>();
        percentiles.add(100);

        // execute
        SimulationResultSummary actual = fixture.createSummary(simulationResult, percentiles, null);


        // verify
        assertEquals(1, actual.getSimulationStatistics().size());
        assertEquals(new Integer(100), actual.getSimulationStatistics().get(0).getKey());
        assertEquals(new BigDecimal("100").doubleValue(), actual.getSimulationStatistics().get(0).getValue().doubleValue(), 0.01);
    }

    @Test
    public void createSummary_normalizeInflationCorrectly_OneYear() {
        // setup
        SimulatorResultAnalyzer fixture = new SimulatorResultAnalyzer();

        List<BigDecimal> finalAmounts = new ArrayList<>();
        finalAmounts.add(new BigDecimal("100"));
        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setYearsSimulated(1);
        simulationResult.setFinalAmounts(finalAmounts);

        List<Integer> percentiles = new ArrayList<>();
        percentiles.add(100);

        // execute
        SimulationResultSummary actual = fixture.createSummary(simulationResult, percentiles, new BigDecimal("0.10"));


        // verify
        assertEquals(1, actual.getSimulationStatistics().size());
        assertEquals(new Integer(100), actual.getSimulationStatistics().get(0).getKey());
        assertEquals(new BigDecimal("90.91").doubleValue(), actual.getSimulationStatistics().get(0).getValue().doubleValue(), 0.01);
    }

    @Test
    public void createSummary_normalizeInflationCorrectly_TenYears() {
        // setup
        SimulatorResultAnalyzer fixture = new SimulatorResultAnalyzer();

        List<BigDecimal> finalAmounts = new ArrayList<>();
        finalAmounts.add(new BigDecimal("100"));
        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setYearsSimulated(10);
        simulationResult.setFinalAmounts(finalAmounts);

        List<Integer> percentiles = new ArrayList<>();
        percentiles.add(100);

        // execute
        SimulationResultSummary actual = fixture.createSummary(simulationResult, percentiles, new BigDecimal("0.10"));


        // verify
        assertEquals(1, actual.getSimulationStatistics().size());
        assertEquals(new Integer(100), actual.getSimulationStatistics().get(0).getKey());
        assertEquals(new BigDecimal("38.55").doubleValue(), actual.getSimulationStatistics().get(0).getValue().doubleValue(), 0.01);
    }

    @Test
    public void createSummary_normalizeDeflationCorrectly_OneYear() {
        // setup
        SimulatorResultAnalyzer fixture = new SimulatorResultAnalyzer();

        List<BigDecimal> finalAmounts = new ArrayList<>();
        finalAmounts.add(new BigDecimal("100"));
        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setYearsSimulated(1);
        simulationResult.setFinalAmounts(finalAmounts);

        List<Integer> percentiles = new ArrayList<>();
        percentiles.add(100);

        // execute
        SimulationResultSummary actual = fixture.createSummary(simulationResult, percentiles, new BigDecimal("-0.10"));


        // verify
        assertEquals(1, actual.getSimulationStatistics().size());
        assertEquals(new Integer(100), actual.getSimulationStatistics().get(0).getKey());
        assertEquals(new BigDecimal("111.11").doubleValue(), actual.getSimulationStatistics().get(0).getValue().doubleValue(), 0.01);
    }
}
