package uni.miskolc.ips.ilona.measurement.persist.mappers;

import java.util.Collection;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import uni.miskolc.ips.ilona.measurement.model.position.Position;

/**
 * 
 * @author bogdandy
 *
 */
public interface PositionMapper {
	/**
	 * Selects all positions from the database. It returns a collection of
	 * positions, or an empty list if no position has been found
	 * 
	 * @return a collection of positions, or an empty list if no position has
	 *         been found
	 */
	Collection<Position> selectPositions();

	/**
	 * Selects a position from the database based on an UUID. It returns a
	 * position, or an empty position if it can't find the position based on the
	 * UUID
	 * 
	 * @param posId
	 *            The UUID the selection is based on.
	 * @return a position based on the UUID.
	 */
	Position selectPositionByUUID(@Param("id") UUID posId);

	/**
	 * * Inserts data into a position object, based on the measuring process.
	 * 
	 * @param position The saved position.
	 */
	void insertPosition(Position position);

	/**
	 * Updates a position's data with new measurements.
	 * 
	 * @param position
	 *            The measurements of the new data of the position.
	 */
	void updatePosition(Position position);

	/**
	 * Deletes a position from the collection, based on a position.
	 * 
	 * @param position
	 *            The position that is to be deleted.
	 */
	void deletePosition(Position position);
}
