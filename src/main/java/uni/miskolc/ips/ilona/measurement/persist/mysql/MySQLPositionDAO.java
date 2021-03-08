package uni.miskolc.ips.ilona.measurement.persist.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.PositionEntity;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.PositionEntityConverter;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntityConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySQLPositionDAO implements PositionDAO {
    private final PositionRepository repository;

    @Override
    public void createPosition(Position position) throws InsertionException {
        if (readPositions().contains(position)) {
            throw new InsertionException();
        }
        try {
            repository.save(PositionEntityConverter.convertModelToEntity(position));
        } catch (Exception ex) {
            throw new InsertionException();
        }
    }

    @Override
    public Position getPosition(UUID id) throws RecordNotFoundException {
        Optional<PositionEntity> positionOptional = repository.findById(id.toString());
        if (positionOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return PositionEntityConverter.convertEntityToModel(positionOptional.get());
    }

    @Override
    public Collection<Position> readPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        repository.findAll().forEach(position -> positions.add(PositionEntityConverter.convertEntityToModel(position)));
        return positions;
    }

    @Override
    public void updatePosition(Position position) throws RecordNotFoundException {
        Optional<PositionEntity> oldPosition = repository.findById(position.getUUID().toString());
        if (oldPosition.isPresent()) {
            oldPosition.get().setCoord_X(position.getCoordinate().getX());
            oldPosition.get().setCoord_Y(position.getCoordinate().getY());
            oldPosition.get().setCoord_Z(position.getCoordinate().getZ());
            if (position.getZone() != null) {
                oldPosition.get().setZone(ZoneEntityConverter.convertModelToEntity(position.getZone()));
            }
            repository.save(oldPosition.get());
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void deletePosition(Position position) throws RecordNotFoundException {
        if (repository.findById(position.getUUID().toString()).isEmpty()) {
            throw new RecordNotFoundException();
        }
        repository.deleteById(position.getUUID().toString());
    }
}
