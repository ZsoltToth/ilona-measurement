package uni.miskolc.ips.ilona.measurement.persist;

import java.util.Collection;
import java.util.Date;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;


/**
 * 
 * @author Zsolt Toth
 *
 */
public interface MeasurementDAO {

	// Create
	public void createMeasurement(Measurement measurement) throws InsertionException;

	// Read
	public Collection<Measurement> readMeasurements();

	// Update
	public void updateMeasurement(Measurement measurement) throws RecordNotFoundException;

	// Delete
	public void deleteMeasurement(Date timestamp) throws RecordNotFoundException;

	public void deleteMeasurement(Measurement measurement) throws RecordNotFoundException;

}
