package uni.miskolc.ips.ilona.measurement.persist;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Zsolt Toth
 */
public interface ZoneDao {

    void createZone(Zone zone) throws InsertionException;

    Collection<Zone> readZones();

    Collection<Zone> readZones(String zoneName);

    Zone readZone(UUID id) throws RecordNotFoundException;

    void deleteZone(Zone zone) throws RecordNotFoundException;
}
