package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;

import java.util.UUID;

public class ZoneEntityConverter {
    public static ZoneEntity convertModelToEntity(Zone zone) {
        ZoneEntity zoneEntity = new ZoneEntity();
        zoneEntity.setId(zone.getId().toString());
        zoneEntity.setName(zone.getName());
        return zoneEntity;
    }

    public static Zone convertEntityToModel(ZoneEntity zoneEntity) {
        Zone zone = new Zone();
        zone.setId(UUID.fromString(zoneEntity.getId()));
        zone.setName(zoneEntity.getName());
        return zone;
    }
}
