package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WifiRssiEntityConverter {
    public static WifiRssi convertEntityToModel(Collection<WifiRssiEntity> wifiRssiEntity) {
        WifiRssi wifiRssi = new WifiRssi();
        Map<String, Double> wifiRssiMap = new HashMap<>();
        for (WifiRssiEntity entity : wifiRssiEntity) {
            wifiRssiMap.put(entity.getId().getSsid(), entity.getRssi());
        }
        wifiRssi.setRssiValues(wifiRssiMap);
        return wifiRssi;
    }

    public static Collection<WifiRssiEntity> convertModelToEntity(Measurement measurement) {
        Collection<WifiRssiEntity> wifiRssiEntities = new ArrayList<>();
        for (Map.Entry<String, Double> wifiRssi : measurement.getWifiRssi().getRssiValues().entrySet()) {
            MeasurementEntity measurementEntity = new MeasurementEntity();
            measurementEntity.setId(measurement.getId().toString());
            WifiRssiEntity wifiRssiEntity = new WifiRssiEntity();
            WifiRssiEntityId id = new WifiRssiEntityId(wifiRssi.getKey(), measurement.getId().toString());
            wifiRssiEntity.setId(id);
            wifiRssiEntity.setRssi(wifiRssi.getValue());
            wifiRssiEntity.setMeasurement(measurementEntity);
            wifiRssiEntities.add(wifiRssiEntity);
        }
        return wifiRssiEntities;
    }
}
