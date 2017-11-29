/**
 * This package domain contains the measurement service class hierarchy.
 */

package uni.miskolc.ips.ilona.measurement.service;

import java.util.Collection;
import java.util.Date;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentMeasurementException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentPositionException;
import uni.miskolc.ips.ilona.measurement.service.exception.PositionNotfoundException;
import uni.miskolc.ips.ilona.measurement.service.exception.TimeStampNotFoundException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

/**
 * This interface declares methods for measurement specific tasks with exception handling.
 * 
 * @author teket
 */

public interface MeasurementService {
	
	/**
	 * Records the measured data into the database.
	 * 
	 * @param measurement 
	 * 			Is the measured data, that will be inserted into the database.
	 * @throws DatabaseUnavailableException
	 * 			Triggers only if the connection with the database cannot be established.
	 * @throws InconsistentMeasurementException
	 * 			Triggers if the integrity of the data is not valid.
	 */
	void recordMeasurement(Measurement measurement) throws DatabaseUnavailableException, InconsistentMeasurementException;

	/**
	 * Retrieves a collection of measurement data from the database.
	 *  
	 * @return 
	 * 			Returns the collection of measurement data. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers only if the connection with the database cannot be established.
	 */
	Collection<Measurement> readMeasurements() throws DatabaseUnavailableException;

	/**
	 * Retrieves a collection of measurement data from the database.
	 * 
	 * @param zone
	 * 			Represents pars of buildings, in terms of rooms.
	 * @return
	 * 			Returns the collection of measurement data. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers only if the connection with the database cannot be established.
	 * @throws ZoneNotFoundException
	 * 			Occurs when a zone does not exists.
	 * 			
	 */
	Collection<Measurement> readMeasurements(Zone zone) throws DatabaseUnavailableException, ZoneNotFoundException;
	
	/**
	 * Retrieves a collection of measurement data from the database.
	 * 
	 * @param position
	 * 			Represents the position based on coordinate, zone and UUD attributes. 
	 * @return
	 * 			Returns the collection of measurement data. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers only if the connection with the database cannot be established.
	 * @throws InconsistentPositionException
	 * 			Triggers if the integrity of the provided data is not valid.
	 * @throws PositionNotfoundException
	 * 			Occurs when a position does not exists.
	 */
	Collection<Measurement> readMeasurements(Position position) throws DatabaseUnavailableException, InconsistentPositionException, PositionNotfoundException;

	/**
	 * It deletes measurement data from the database based on a specific time stamp.
	 * 
	 * @param timestamp
	 * 			Represents a specific capture of time, with millisecond precision.
	 * @throws DatabaseUnavailableException
	 * 			Triggers only if the connection with the database cannot be established.
	 * @throws TimeStampNotFoundException
	 * 			Occurs when the time stamp does not exists.
	 */
	void deleteMeasurement(Date timestamp) throws DatabaseUnavailableException, TimeStampNotFoundException;

}
