package uni.miskolc.ips.ilona.measurement.persist;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Zsolt Toth
 */
public interface MeasurementDAO {

    // Create
    void createMeasurement(Measurement measurement) throws InsertionException;

    // Read
    Collection<Measurement> readMeasurements();

    Measurement readMeasurement(UUID uuid);

    // Update
    void updateMeasurement(Measurement measurement)
            throws RecordNotFoundException, InsertionException;

    // Delete
    void deleteMeasurement(Date timestamp) throws RecordNotFoundException;

    void deleteMeasurement(Measurement measurement) throws RecordNotFoundException;
}
