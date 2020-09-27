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
    public void createMeasurement(Measurement measurement) throws InsertionException;

    // Read
    public Collection<Measurement> readMeasurements();

    Measurement readMeasurement(UUID uuid);

    // Update
    public void updateMeasurement(Measurement measurement) throws RecordNotFoundException, InsertionException;

    // Delete
    public void deleteMeasurement(Date timestamp) throws RecordNotFoundException;

    public void deleteMeasurement(Measurement measurement) throws RecordNotFoundException;

}
