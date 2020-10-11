package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;

import java.util.ArrayList;
import java.util.Collection;

public class RfidTagEntityConverter {
    public static Collection<RfidTagEntity> convertModelToEntity(Measurement measurement) {
        Collection<RfidTagEntity> rfidTagEntities = new ArrayList<>();
        for (byte[] tag : measurement.getRfidtags().getTags()) {
            MeasurementEntity measurementEntity = new MeasurementEntity();
            measurementEntity.setId(measurement.getId().toString());
            RfidTagEntity rfidTag = new RfidTagEntity();
            rfidTag.setId(new RfidTagId(tag, measurement.getId().toString()));
            rfidTag.setMeasurement(measurementEntity);
            rfidTagEntities.add(rfidTag);
        }
        return rfidTagEntities;
    }
}
