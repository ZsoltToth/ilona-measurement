package uni.miskolc.ips.ilona.measurement.persist;

import java.util.Collection;
import java.util.UUID;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

/** @author Zsolt Toth */
public interface PositionDAO {
  void createPosition(Position position) throws InsertionException;

  Position getPosition(UUID id) throws RecordNotFoundException;

  Collection<Position> readPositions();

  void updatePosition(Position position) throws RecordNotFoundException;

  void deletePosition(Position position) throws RecordNotFoundException;
}
