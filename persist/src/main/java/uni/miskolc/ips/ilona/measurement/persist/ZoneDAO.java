package uni.miskolc.ips.ilona.measurement.persist;

import java.util.Collection;
import java.util.UUID;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;


/**
 * 
 * @author Zsolt Toth
 *
 */
public interface ZoneDAO {

	public void createZone(Zone zone) throws InsertionException;

	public Collection<Zone> readZones();

	public Collection<Zone> readZones(String zoneName);

	public Zone readZone(UUID id) throws RecordNotFoundException;

	public void deleteZone(Zone zone) throws RecordNotFoundException;
}
