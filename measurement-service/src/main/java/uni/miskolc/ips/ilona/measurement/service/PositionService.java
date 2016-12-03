/**
 * This package domain contains the measurement service class hierarchy.
 */

package uni.miskolc.ips.ilona.measurement.service;

import java.util.Collection;
import java.util.UUID;

import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentPositionException;
import uni.miskolc.ips.ilona.measurement.service.exception.PositionNotfoundException;

/**
 * This interface declares methods for position specific tasks.
 * 
 * @author teket *
 */

public interface PositionService {

	/**
	 * Inserts a given position into the database.
	 * 
	 * @param position
	 * 			Represents the position based on coordinate, zone and UUD attributes. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers if the connection with the database cannot be established.
	 * @throws InconsistentPositionException
	 * 			Triggers if the integrity of the data is not valid.
	 */
	void createPosition(Position position) throws DatabaseUnavailableException, InconsistentPositionException;
	
	/**
	 * Retrieves a collection of position data from the database.
	 * 
	 * @return
	 * 			Returns the collection of position data.
	 * @throws DatabaseUnavailableException
	 * 			Triggers if the connection with the database cannot be established.
	 */
	Collection<Position> readPositions() throws DatabaseUnavailableException;
	
	/**
	 * Updates the database based on a given position data.
	 * 
	 * @param position
	 * 			Represents the position based on coordinate, zone and UUD attributes. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers if the connection with the database cannot be established.
	 * @throws InconsistentPositionException
	 * 			Triggers if the integrity of the data is not valid.
	 * @throws PositionNotfoundException
	 * 			Occurs, when the position does not exists.	 			
	 */	
	void updatePosition(Position position) throws DatabaseUnavailableException, InconsistentPositionException, PositionNotfoundException;
	
	/**
	 * Retrieves the position data from the database.
	 * 
	 * @param posid
	 * 			An unique uniform identifier for the position.
	 * @return
	 * 			Position data.
	 * @throws DatabaseUnavailableException
	 * 			Triggers if the connection with the database cannot be established.
	 * @throws PositionNotfoundException
	 * 			Occurs, when the position does not exists.
	 */
	Position getPosition(UUID posid) throws DatabaseUnavailableException, PositionNotfoundException;
	
	/**
	 * Deletes the position data from the database.
	 * 
	 * @param position
	 * 			Represents the position based on coordinate, zone and UUD attributes. 
	 * @throws DatabaseUnavailableException
	 * 			Triggers if the connection with the database cannot be established.
	 * @throws InconsistentPositionException
	 * 			Triggers if the integrity of the data is not valid.
	 * @throws PositionNotfoundException
	 * 			Occurs, when the position does not exists.	 	
	 */
	void deletePosition(Position position) throws DatabaseUnavailableException, InconsistentPositionException, PositionNotfoundException;
}
