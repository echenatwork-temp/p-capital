package portfolio.simulator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GaussianRandomNumberGenerator implements ReturnsGenerator {

    public Random random;
    private BigDecimal mean;
    private BigDecimal standardDeviation;

    public GaussianRandomNumberGenerator(long seed, BigDecimal mean, BigDecimal standardDeviation) {
        this.random = new Random(seed);
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public List<BigDecimal> getReturns(int numValues) {
        List<BigDecimal> values = new ArrayList<BigDecimal>(numValues);
        for (int i = 0; i < numValues; i++) {
            BigDecimal value = standardDeviation.multiply(new BigDecimal(random.nextGaussian())).add(mean);
            values.add(value);
        }
        return values;
    }

}
