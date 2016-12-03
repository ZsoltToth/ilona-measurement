package uni.miskolc.ips.ilona.measurement.service.impl;

import java.util.Collection;

import java.util.UUID;

import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.service.PositionService;
/**
 * The PositionManagerServiceImpl class is used to Manage Positions.
 * 
 * @author tamas13
 *
 */
public class PositionManagerServiceImpl implements PositionService {
	/**
	 * PositionDAO provides an abstract interface to database of
	 * Positions.
	 */
	private PositionDAO positionDao;

	/**
	 * The constructor of PositionManagerServiceImpl with the positionDAO parameter.
	 * @param positionDao provides an abstract interface to database of Positions.
	 */
	public PositionManagerServiceImpl(final PositionDAO positionDao) {
		super();
		this.positionDao = positionDao;
	}

	/**
	 * The createPosition method insert the given position into the
	 * database.
	 * 
	 * @param position
	 *            is the given position which should be recorded.
	 */
	public final void createPosition(final Position position) {
		try {
			positionDao.createPosition(position);
		} catch (InsertionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * The readPositions method get the Positions from the database.
	 * 
	 * @return with the collection of the Positions read from the database.
	 */
	public final Collection<Position> readPositions() {
		return positionDao.readPositions();
	}
	
	
	/**
	 * The updatePosition method modify the given Position in the database based on the UUID of the Position.
	 * @param position is the Position to update, which contains the UUID.
	 */
	public final void updatePosition(final 
			Position position) {
		try {
			positionDao.updatePosition(position);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * getPosition method return the Position with the given posid.
	 * @param posid is the UUID of the searched Position.
	 * @return with the Position with the given UUID.
	 */
	public final Position getPosition(final UUID posid) {
		Position result = null;
		try {
			result = positionDao.getPosition(posid);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * The deletePositin method delete the Position from the database, which is the same Position with the given.
	 * @param position which position should be deleted
	 */
	public final void deletePosition(final Position position) {
		try {
			positionDao.deletePosition(position);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
