package uni.miskolc.ips.ilona.measurement.persist.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.persist.MeasurementDao;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.BluetoothTagEntityConverter;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.MeasurementEntity;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.MeasurementEntityConverter;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.PositionEntityConverter;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.RfidTagEntityConverter;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.WifiRssiEntityConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySqlMeasurementDao implements MeasurementDao {
    private final MeasurementRepository repository;

    @Override
    @Transactional
    public void createMeasurement(Measurement measurement) throws InsertionException {
        try {
            repository.save(MeasurementEntityConverter.convertModelToEntity(measurement));
        } catch (Exception ex) {
            throw new InsertionException();
        }
    }

    @Override
    @Transactional
    public Collection<Measurement> readMeasurements() {
        ArrayList<Measurement> measurements = new ArrayList<>();
        repository.findAll().forEach(measurement -> measurements.add(
                MeasurementEntityConverter.convertEntityToModel(measurement)
        ));
        return measurements;
    }

    @Override
    @Transactional
    public Measurement readMeasurement(UUID uuid) {
        Optional<MeasurementEntity> measurementOptional = repository.findById(uuid.toString());
        return measurementOptional.map(MeasurementEntityConverter::convertEntityToModel).orElse(null);
    }

    @Override
    @Transactional
    public void updateMeasurement(Measurement measurement) throws RecordNotFoundException, InsertionException {
        Optional<MeasurementEntity> oldMeasurement = repository.findById(measurement.getId().toString());
        if (oldMeasurement.isPresent()) {
            oldMeasurement.get().setMeasTimestamp(new Date(System.currentTimeMillis()));
            if (measurement.getMagnetometer() != null) {
                oldMeasurement.get().setMagnetometerX(measurement.getMagnetometer().getxAxis());
                oldMeasurement.get().setMagnetometerY(measurement.getMagnetometer().getyAxis());
                oldMeasurement.get().setMagnetometerZ(measurement.getMagnetometer().getzAxis());
                oldMeasurement.get().setMagnetometerRadian(measurement.getMagnetometer().getRadian());
            }
            if (measurement.getGpsCoordinates() != null) {
                oldMeasurement.get().setGpsLatitude(measurement.getGpsCoordinates().getLatitude());
                oldMeasurement.get().setGpsLongitude(measurement.getGpsCoordinates().getLongitude());
                oldMeasurement.get().setGpsAltitude(measurement.getGpsCoordinates().getAltitude());
            }
            if (measurement.getPosition() != null) {
                oldMeasurement.get().setPosition(
                        PositionEntityConverter.convertModelToEntity(measurement.getPosition())
                );
            }
            if (measurement.getBluetoothTags() != null) {
                oldMeasurement.get().getBluetoothTags().clear();
                oldMeasurement.get().getBluetoothTags().addAll(
                        BluetoothTagEntityConverter.convertModelToEntity(measurement)
                );
            }
            if (measurement.getWifiRssi() != null) {
                oldMeasurement.get().getWifiRssi().clear();
                oldMeasurement.get().getWifiRssi().addAll(
                        WifiRssiEntityConverter.convertModelToEntity(measurement)
                );
            }
            if (measurement.getRfidtags() != null) {
                oldMeasurement.get().getRfidTags().clear();
                oldMeasurement.get().getRfidTags().addAll(
                        RfidTagEntityConverter.convertModelToEntity(measurement)
                );
            }
            try {
                repository.save(oldMeasurement.get());
            } catch (Exception ex) {
                throw new InsertionException();
            }
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteMeasurement(Date timestamp) throws RecordNotFoundException {
        if (repository.findAllByMeasTimestamp(timestamp).isEmpty()) {
            throw new RecordNotFoundException();
        }
        repository.deleteAllByMeasTimestamp(timestamp);
    }

    @Override
    public void deleteMeasurement(Measurement measurement) throws RecordNotFoundException {
        if (repository.findById(measurement.getId().toString()).isEmpty()) {
            throw new RecordNotFoundException();
        }
        repository.deleteById(measurement.getId().toString());
    }
}
