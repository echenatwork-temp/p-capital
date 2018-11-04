package portfolio.simulator;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.List;

public class SimulationResultSummary {

    private List<Pair<Integer, BigDecimal>> simulationStatistics;

    public List<Pair<Integer, BigDecimal>> getSimulationStatistics() {
        return simulationStatistics;
    }

    public void setSimulationStatistics(List<Pair<Integer, BigDecimal>> simulationStatistics) {
        this.simulationStatistics = simulationStatistics;
    }
}
