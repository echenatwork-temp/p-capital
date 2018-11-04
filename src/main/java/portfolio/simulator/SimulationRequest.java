package portfolio.simulator;

import java.math.BigDecimal;

public class SimulationRequest {

    private ReturnsGenerator returnsGenerator;
    private int iterations;
    private int numSimulations;
    private BigDecimal initialAmount;

    public ReturnsGenerator getReturnsGenerator() {
        return returnsGenerator;
    }

    public void setReturnsGenerator(ReturnsGenerator returnsGenerator) {
        this.returnsGenerator = returnsGenerator;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getNumSimulations() {
        return numSimulations;
    }

    public void setNumSimulations(int numSimulations) {
        this.numSimulations = numSimulations;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }
}
