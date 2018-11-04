package portfolio.simulator;

import java.math.BigDecimal;
import java.util.List;

public interface ReturnsGenerator {

    List<BigDecimal> getReturns(int numValues);
}
