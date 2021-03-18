package uni.miskolc.ips.ilona.measurement.model.measurement.wifi;

import lombok.extern.slf4j.Slf4j;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssi;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssiDistanceCalculator;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class VectorIntersectionWifiRssiDistance implements WifiRssiDistanceCalculator {

    public static final double UNKNOWN_DISTANCE = -1;

    @Override
    public double distance(WifiRssi rssA, WifiRssi rssB) {
        double result = UNKNOWN_DISTANCE;
        Set<String> intersection = new HashSet<String>(rssA.getRssiValues().keySet());
        intersection.retainAll(rssB.getRssiValues().keySet());
        if (intersection.isEmpty()) {
            return result;
        }
        result = 1 - Math.abs(correlation(rssA, rssB, intersection));
        log.info(
                String.format(
                        "Distance between %s and %s is %f", rssA.toString(), rssB.toString(), result));
        return result;
    }

    protected double correlation(WifiRssi rssA, WifiRssi rssB, Set<String> intersection) {
        double result = 0;

        double avgRssA = this.avg(rssA, intersection);
        double avgRssB = this.avg(rssB, intersection);
        double devRssA = deviance(rssA, intersection);
        double devRssB = deviance(rssB, intersection);
        double nominator = 0.0;

        for (String each : intersection) {
            nominator += (rssA.getRssi(each) - avgRssA) * (rssB.getRssi(each) - avgRssB);
        }
        if (intersection.size() > 1) {
            result = nominator / ((intersection.size() - 1) * (devRssA * devRssB));
            return result;
        }
        return 0;
    }

    protected double deviance(WifiRssi wifi, Set<String> intersection) {
        double result = 0.0;
        final double avg = this.avg(wifi, intersection);
        for (String each : intersection) {
            result += Math.pow(wifi.getRssi(each) - avg, 2.0);
        }
        result = result / ((double) intersection.size() - 1);
        return Math.sqrt(result);
    }

    protected double avg(WifiRssi wifi, Set<String> intersection) {
        double result = 0.0;

        for (String each : intersection) {
            result += wifi.getRssi(each);
        }

        return result / (double) intersection.size();
    }
}
