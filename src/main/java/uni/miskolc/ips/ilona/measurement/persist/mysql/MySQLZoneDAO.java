package uni.miskolc.ips.ilona.measurement.persist.mysql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntityConverter;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySQLZoneDAO implements ZoneDAO {
  private final ZoneRepository repository;

  @Override
  public void createZone(Zone zone) throws InsertionException {}

  @Override
  public Collection<Zone> readZones() {
    ArrayList<Zone> zones = new ArrayList<>();
    repository.findAll().forEach(zone -> zones.add(ZoneEntityConverter.convertEntityToModel(zone)));
    return zones;
  }

  @Override
  public Collection<Zone> readZones(String zoneName) {
    return null;
  }

  @Override
  public Zone readZone(UUID id) throws RecordNotFoundException {
    return null;
  }

  @Override
  public void deleteZone(Zone zone) throws RecordNotFoundException {}
}
