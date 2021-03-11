package uni.miskolc.ips.ilona.measurement.model.measurement;

import java.util.HashMap;
import java.util.Map;

public class WifiRssi {

    private Map<String, Double> rssiValues;

    public WifiRssi() {
        super();
        this.rssiValues = new HashMap<>();
    }

    public WifiRssi(Map<String, Double> rssiValues) {
        super();
        this.rssiValues = rssiValues;
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

    @Override
    public String toString() {
        return "WiFiRSSI rssiValues = " + rssiValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifiRssi wifiRssi = (WifiRssi) o;

        return rssiValues != null
                ? rssiValues.equals(wifiRssi.rssiValues)
                : wifiRssi.rssiValues == null;
    }

    @Override
    public int hashCode() {
        return rssiValues != null ? rssiValues.hashCode() : 0;
    }
}
