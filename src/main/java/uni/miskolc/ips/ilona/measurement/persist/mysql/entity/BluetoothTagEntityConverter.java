package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;

import java.util.ArrayList;
import java.util.Collection;

public class BluetoothTagEntityConverter {
    public static Collection<BluetoothTagEntity> convertModelToEntity(Measurement measurement) {
        Collection<BluetoothTagEntity> bluetoothTagEntities = new ArrayList<>();
        for (String tag : measurement.getBluetoothTags().getTags()) {
            MeasurementEntity measurementEntity = new MeasurementEntity();
            measurementEntity.setId(measurement.getId().toString());
            BluetoothTagEntity bluetoothTag = new BluetoothTagEntity();
            bluetoothTag.setId(new BluetoothTagId(tag, measurement.getId().toString()));
            bluetoothTag.setMeasurement(measurementEntity);
            bluetoothTagEntities.add(bluetoothTag);
        }
        return bluetoothTagEntities;
    }
}
