package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSI;

import java.util.*;

public class WifiRssiEntityConverter {
    public static WiFiRSSI convertEntityToModel(Collection<WifiRSSIEntity> wifiRSSIEntity) {
        WiFiRSSI wiFiRSSI = new WiFiRSSI();
        Map<String, Double> wifiRssiMap = new HashMap<>();
        for (WifiRSSIEntity entity : wifiRSSIEntity) {
            wifiRssiMap.put(entity.getId().getSsid(), entity.getRssi());
        }
        wiFiRSSI.setRssiValues(wifiRssiMap);
        return wiFiRSSI;
    }

    public static Collection<WifiRSSIEntity> convertModelToEntity(Measurement measurement) {
        Collection<WifiRSSIEntity> wifiRSSIEntities = new ArrayList<>();
        for (Map.Entry<String, Double> wifiRssi : measurement.getWifiRSSI().getRssiValues().entrySet()) {
            MeasurementEntity measurementEntity = new MeasurementEntity();
            measurementEntity.setId(measurement.getId().toString());
            WifiRSSIEntity wifiRSSIEntity = new WifiRSSIEntity();
            WifiRSSIEntityId id = new WifiRSSIEntityId(wifiRssi.getKey(), measurement.getId().toString());
            wifiRSSIEntity.setId(id);
            wifiRSSIEntity.setRssi(wifiRssi.getValue());
            wifiRSSIEntity.setMeasurement(measurementEntity);
            wifiRSSIEntities.add(wifiRSSIEntity);
        }
        return wifiRSSIEntities;
    }
}
