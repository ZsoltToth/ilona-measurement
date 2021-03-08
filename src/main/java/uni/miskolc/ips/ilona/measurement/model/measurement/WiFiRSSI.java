package uni.miskolc.ips.ilona.measurement.model.measurement;

import java.util.HashMap;
import java.util.Map;

public class WiFiRSSI {

    private Map<String, Double> rssiValues;

    public WiFiRSSI() {
        super();
        this.rssiValues = new HashMap<>();
    }

    public WiFiRSSI(Map<String, Double> rssiValues) {
        super();
        this.rssiValues = rssiValues;
    }

    public void setRSSI(String ssid, double rssi) {
        this.rssiValues.put(ssid, rssi);
    }

    public double getRSSI(String ssid) {
        return this.rssiValues.get(ssid);
    }

    public void removeSSID(String ssid) {
        this.rssiValues.remove(ssid);
    }

    public Map<String, Double> getRssiValues() {
        return rssiValues;
    }

    public void setRssiValues(Map<String, Double> rssiValues) {
        this.rssiValues = rssiValues;
    }

    @Override
    public String toString() {
        return "WiFiRSSI rssiValues = " + rssiValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WiFiRSSI wiFiRSSI = (WiFiRSSI) o;

        return rssiValues != null
                ? rssiValues.equals(wiFiRSSI.rssiValues)
                : wiFiRSSI.rssiValues == null;
    }

    @Override
    public int hashCode() {
        return rssiValues != null ? rssiValues.hashCode() : 0;
    }
}
