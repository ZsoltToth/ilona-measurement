package uni.miskolc.ips.ilona.measurement.model.measurement.wifi;

import lombok.extern.slf4j.Slf4j;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssi;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssiDistanceCalculator;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class VectorUnionWifiRssiDistance implements WifiRssiDistanceCalculator {

    private final double unknownValue;

    public VectorUnionWifiRssiDistance(double unknownValue) {
        super();
        this.unknownValue = unknownValue;
    }

    @Override
    public double distance(WifiRssi rssA, WifiRssi rssB) {
        double result = 0.0;
        Set<String> union = new HashSet<String>();
        union.addAll(rssA.getRssiValues().keySet());
        union.addAll(rssB.getRssiValues().keySet());
        if (union.isEmpty()) {
            return WifiRssiDistanceCalculator.UNKOWN_DISTANCE;
        }
        if (rssA.getRssiValues().isEmpty() || rssB.getRssiValues().isEmpty()) {
            return 1.0;
        }

        for (String each : union) {
            double valA =
                    rssA.getRssiValues().containsKey(each) ? rssA.getRssiValues().get(each) : unknownValue;
            double valB =
                    rssB.getRssiValues().containsKey(each) ? rssB.getRssiValues().get(each) : unknownValue;
            result += Math.pow(valA - valB, 2.0);
        }
        result = Math.sqrt(result);
        log.info(
                String.format(
                        "Distance between %s and %s is %f", rssA.toString(), rssB.toString(), result));
        return result;
    }
}
