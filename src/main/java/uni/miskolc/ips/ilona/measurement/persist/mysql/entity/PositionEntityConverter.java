package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;

import java.util.UUID;

public class PositionEntityConverter {
    public static PositionEntity convertModelToEntity(Position position) {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(position.getUuid().toString());
        if (position.getCoordinate() != null) {
            positionEntity.setCoord_X(position.getCoordinate().getX());
            positionEntity.setCoord_Y(position.getCoordinate().getY());
            positionEntity.setCoord_Z(position.getCoordinate().getZ());
        }
        if (position.getZone() != null) {
            positionEntity.setZone(ZoneEntityConverter.convertModelToEntity(position.getZone()));
        }
        return positionEntity;
    }

    public static Position convertEntityToModel(PositionEntity positionEntity) {
        Position position = new Position();
        position.setUuid(UUID.fromString(positionEntity.getId()));
        Coordinate coordinate = new Coordinate(
                positionEntity.getCoord_X(),
                positionEntity.getCoord_Y(),
                positionEntity.getCoord_Z()
        );
        position.setCoordinate(coordinate);
        position.setZone(ZoneEntityConverter.convertEntityToModel(positionEntity.getZone()));
        return position;
    }
}
