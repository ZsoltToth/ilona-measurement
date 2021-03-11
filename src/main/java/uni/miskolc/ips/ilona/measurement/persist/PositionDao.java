package uni.miskolc.ips.ilona.measurement.persist;

import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Zsolt Toth
 */
public interface PositionDao {
    void createPosition(Position position) throws InsertionException;

    Position getPosition(UUID id) throws RecordNotFoundException;

    Collection<Position> readPositions();

    void updatePosition(Position position) throws RecordNotFoundException;

    void deletePosition(Position position) throws RecordNotFoundException;
}
