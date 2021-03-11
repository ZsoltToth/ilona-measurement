package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.measurement.BluetoothTags;
import uni.miskolc.ips.ilona.measurement.model.measurement.GpsCoordinate;
import uni.miskolc.ips.ilona.measurement.model.measurement.Magnetometer;
import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.measurement.RfidTags;
import uni.miskolc.ips.ilona.measurement.model.measurement.WifiRssi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MeasurementEntityConverter {
    public static MeasurementEntity convertModelToEntity(Measurement measurement) {
        MeasurementEntity measurementEntity = new MeasurementEntity();
        measurementEntity.setId(measurement.getId().toString());
        measurementEntity.setMeasTimestamp(measurement.getTimestamp());
        measurementEntity.setMagnetometerX(measurement.getMagnetometer().getxAxis());
        measurementEntity.setMagnetometerY(measurement.getMagnetometer().getyAxis());
        measurementEntity.setMagnetometerZ(measurement.getMagnetometer().getzAxis());
        measurementEntity.setMagnetometerRadian(measurement.getMagnetometer().getRadian());
        measurementEntity.setGpsLatitude(measurement.getGpsCoordinates().getLatitude());
        measurementEntity.setGpsLongitude(measurement.getGpsCoordinates().getLongitude());
        measurementEntity.setGpsAltitude(measurement.getGpsCoordinates().getAltitude());
        measurementEntity.setPosition(PositionEntityConverter.convertModelToEntity(measurement.getPosition()));
        measurementEntity.setBluetoothTags(BluetoothTagEntityConverter.convertModelToEntity(measurement));
        measurementEntity.setWifiRssi(WifiRssiEntityConverter.convertModelToEntity(measurement));
        measurementEntity.setRfidTags(RfidTagEntityConverter.convertModelToEntity(measurement));
        return measurementEntity;
    }

    public static Measurement convertEntityToModel(MeasurementEntity measurementEntity) {
        Measurement measurement = new Measurement();
        measurement.setId(UUID.fromString(measurementEntity.getId()));
        measurement.setTimestamp(measurementEntity.getMeasTimestamp());
        if (measurementEntity.getMagnetometerX() != null &&
                measurementEntity.getMagnetometerY() != null &&
                measurementEntity.getMagnetometerZ() != null &&
                measurementEntity.getMagnetometerRadian() != null
        ) {
            measurement.setMagnetometer(new Magnetometer(
                    measurementEntity.getMagnetometerX(),
                    measurementEntity.getMagnetometerY(),
                    measurementEntity.getMagnetometerZ(),
                    measurementEntity.getMagnetometerRadian()
            ));
        }
        if (measurementEntity.getGpsAltitude() != null &&
                measurementEntity.getGpsLatitude() != null &&
                measurementEntity.getGpsLongitude() != null
        ) {
            measurement.setGpsCoordinates(new GpsCoordinate(
                    measurementEntity.getGpsLatitude(),
                    measurementEntity.getGpsLongitude(),
                    measurementEntity.getGpsAltitude()
            ));
        }
        measurement.setPosition(PositionEntityConverter.convertEntityToModel(measurementEntity.getPosition()));
        if (measurementEntity.getBluetoothTags() != null) {
            BluetoothTags bluetoothTags = new BluetoothTags();
            Set<String> bluetoothTagSet = new HashSet<>();
            for (BluetoothTagEntity bluetoothTag : measurementEntity.getBluetoothTags()) {
                bluetoothTagSet.add(bluetoothTag.getId().getBtDeviceId());
            }
            bluetoothTags.setTags(bluetoothTagSet);
            measurement.setBluetoothTags(bluetoothTags);
        }
        if (measurementEntity.getWifiRssi() != null) {
            WifiRssi wifiRssi = new WifiRssi();
            Map<String, Double> wifiRssiMap = new HashMap<>();
            for (WifiRssiEntity wifiRssiEntity : measurementEntity.getWifiRssi()) {
                wifiRssiMap.put(wifiRssiEntity.getId().getSsid(), wifiRssiEntity.getRssi());
            }
            wifiRssi.setRssiValues(wifiRssiMap);
            measurement.setWifiRssi(wifiRssi);
        }
        if (measurementEntity.getRfidTags().size() != 0) {
            RfidTags rfidTags = new RfidTags();
            for (RfidTagEntity tag : measurementEntity.getRfidTags()) {
                rfidTags.addTag(tag.getId().getRfidTag());
            }
            measurement.setRfidtags(rfidTags);
        }
        return measurement;
    }
}
