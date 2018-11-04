package portfolio.simulator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PortfolioSimulator {


    /**
     * returns the non-inflation adjusted simulation results
     */
    public SimulationResult simulate(SimulationRequest simulationRequest) {
        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setYearsSimulated(simulationRequest.getIterations());

        ReturnsGenerator randomNumberGenerator = simulationRequest.getReturnsGenerator();

        List<BigDecimal> finalAmounts = new ArrayList<BigDecimal>(simulationRequest.getNumSimulations());


        for (int i = 0; i < simulationRequest.getNumSimulations(); i++) {
            BigDecimal currentAmount = simulationRequest.getInitialAmount();
            List<BigDecimal> changePercentages = randomNumberGenerator.getReturns(simulationRequest.getIterations());
            for (BigDecimal changePercentage : changePercentages) {
                currentAmount = currentAmount.multiply(changePercentage.add(BigDecimal.ONE));
            }
            finalAmounts.add(currentAmount);
        }
        simulationResult.setFinalAmounts(finalAmounts);

        return simulationResult;
    }

}
