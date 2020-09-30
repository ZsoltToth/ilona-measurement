package uni.miskolc.ips.ilona.measurement.persist.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySQLPositionDAO implements PositionDAO {

  @Override
  public void createPosition(Position position) throws InsertionException {

  }

  @Override
  public Position getPosition(UUID id) throws RecordNotFoundException {
    return null;
  }

  @Override
  public Collection<Position> readPositions() {
    return null;
  }

  @Override
  public void updatePosition(Position position) throws RecordNotFoundException {

  }

  @Override
  public void deletePosition(Position position) throws RecordNotFoundException {

  }
}
