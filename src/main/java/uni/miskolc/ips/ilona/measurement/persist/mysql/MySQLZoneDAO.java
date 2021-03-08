package uni.miskolc.ips.ilona.measurement.persist.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntity;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntityConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySQLZoneDAO implements ZoneDAO {
    private final ZoneRepository repository;

    @Override
    public void createZone(Zone zone) throws InsertionException {
        if (readZones().contains(zone)) {
            throw new InsertionException();
        }
        try {
            repository.save(ZoneEntityConverter.convertModelToEntity(zone));
        } catch (Exception ex) {
            throw new InsertionException();
        }
    }

    @Override
    public Collection<Zone> readZones() {
        ArrayList<Zone> zones = new ArrayList<>();
        repository.findAll().forEach(zone -> zones.add(ZoneEntityConverter.convertEntityToModel(zone)));
        return zones;
    }

    @Override
    public Collection<Zone> readZones(String zoneName) {
        ArrayList<Zone> zones = new ArrayList<>();
        repository.findAllByName(zoneName).forEach(zone -> zones.add(ZoneEntityConverter.convertEntityToModel(zone)));
        return zones;
    }

    @Override
    public Zone readZone(UUID id) throws RecordNotFoundException {
        Optional<ZoneEntity> zoneOptional = repository.findById(id.toString());
        if (zoneOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return ZoneEntityConverter.convertEntityToModel(zoneOptional.get());
    }

    @Override
    public void deleteZone(Zone zone) throws RecordNotFoundException {
        if (repository.findById(zone.getId().toString()).isEmpty()) {
            throw new RecordNotFoundException();
        }
        repository.deleteById(zone.getId().toString());
    }
}
