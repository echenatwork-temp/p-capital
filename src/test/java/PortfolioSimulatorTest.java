import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import portfolio.simulator.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PortfolioSimulatorTest {

    // one year, ten percent gain
    @Test
    public void simulate_oneRunOneYear() {
        // setup
        PortfolioSimulator fixture = new PortfolioSimulator();

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setNumSimulations(1);
        simulationRequest.setIterations(1);
        simulationRequest.setInitialAmount(new BigDecimal("100"));

        ReturnsGenerator testReturnsGenerator = Mockito.mock(ReturnsGenerator.class);
        List<BigDecimal> percentChanges = new ArrayList<BigDecimal>();
        percentChanges.add(new BigDecimal("0.10"));
        Mockito.when(testReturnsGenerator.getReturns(Matchers.anyInt())).thenReturn(percentChanges);

        simulationRequest.setReturnsGenerator(testReturnsGenerator);

        // execute
        SimulationResult actual = fixture.simulate(simulationRequest);

        // verify
        assertEquals(1, actual.getYearsSimulated());
        assertEquals(1, actual.getFinalAmounts().size());
        assertEquals(new BigDecimal("110").doubleValue(), actual.getFinalAmounts().get(0).doubleValue(), 0.01);
    }

    @Test
    public void simulate_oneRunTenYears() {
        // setup
        PortfolioSimulator fixture = new PortfolioSimulator();

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setNumSimulations(1);
        simulationRequest.setIterations(10);
        simulationRequest.setInitialAmount(new BigDecimal("100"));

        ReturnsGenerator testReturnsGenerator = Mockito.mock(ReturnsGenerator.class);
        List<BigDecimal> percentChanges = new ArrayList<BigDecimal>();
        for (int i = 0; i < 10; i++) {
            percentChanges.add(new BigDecimal("0.10"));
        }
        Mockito.when(testReturnsGenerator.getReturns(Matchers.anyInt())).thenReturn(percentChanges);

        simulationRequest.setReturnsGenerator(testReturnsGenerator);

        // execute
        SimulationResult actual = fixture.simulate(simulationRequest);

        // verify
        assertEquals(10, actual.getYearsSimulated());
        assertEquals(1, actual.getFinalAmounts().size());
        assertEquals(new BigDecimal("259.37").doubleValue(), actual.getFinalAmounts().get(0).doubleValue(), 0.01);
    }

    @Test
    public void simulate_twoRunsOneYear() {
        // setup
        PortfolioSimulator fixture = new PortfolioSimulator();

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setNumSimulations(2);
        simulationRequest.setIterations(1);
        simulationRequest.setInitialAmount(new BigDecimal("100"));

        ReturnsGenerator testReturnsGenerator = Mockito.mock(ReturnsGenerator.class);
        List<BigDecimal> percentChanges = new ArrayList<BigDecimal>();
        percentChanges.add(new BigDecimal("0.10"));

        Mockito.when(testReturnsGenerator.getReturns(Matchers.anyInt())).thenReturn(percentChanges);

        simulationRequest.setReturnsGenerator(testReturnsGenerator);

        // execute
        SimulationResult actual = fixture.simulate(simulationRequest);

        // verify
        assertEquals(1, actual.getYearsSimulated());
        assertEquals(2, actual.getFinalAmounts().size());
        assertEquals(new BigDecimal("110").doubleValue(), actual.getFinalAmounts().get(0).doubleValue(), 0.01);
        assertEquals(new BigDecimal("110").doubleValue(), actual.getFinalAmounts().get(1).doubleValue(), 0.01);
    }

}
