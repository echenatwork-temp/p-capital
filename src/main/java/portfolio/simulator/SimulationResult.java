package portfolio.simulator;

import java.math.BigDecimal;
import java.util.List;

public class SimulationResult {

    private int yearsSimulated;
    private List<BigDecimal> finalAmounts;

    public int getYearsSimulated() {
        return yearsSimulated;
    }

    public void setYearsSimulated(int yearsSimulated) {
        this.yearsSimulated = yearsSimulated;
    }

    public List<BigDecimal> getFinalAmounts() {
        return finalAmounts;
    }

    public void setFinalAmounts(List<BigDecimal> finalAmounts) {
        this.finalAmounts = finalAmounts;
    }
}
