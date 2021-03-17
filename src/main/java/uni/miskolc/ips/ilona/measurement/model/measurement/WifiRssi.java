package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class WifiRssi {

    private Map<String, Double> rssiValues;

    public WifiRssi() {
        super();
        this.rssiValues = new HashMap<>();
    }

    public void setRssi(String ssid, double rssi) {
        this.rssiValues.put(ssid, rssi);
    }

    public double getRssi(String ssid) {
        return this.rssiValues.get(ssid);
    }

    public void removeSsid(String ssid) {
        this.rssiValues.remove(ssid);
    }

    public Map<String, Double> getRssiValues() {
        return rssiValues;
    }

    public void setRssiValues(Map<String, Double> rssiValues) {
        this.rssiValues = rssiValues;
    }
}
