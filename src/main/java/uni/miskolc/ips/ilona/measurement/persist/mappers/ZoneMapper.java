package uni.miskolc.ips.ilona.measurement.persist.mappers;

import java.util.Collection;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;

/**
 * 
 * @author bogdandy
 *
 */
public interface ZoneMapper {
	/**
	 * Selects all zones from the collection of zones. Returns all zones, or an
	 * empty collection.
	 * 
	 * @return a collection of zones, or an empty collection.
	 */
	Collection<Zone> selectZones();

	/**
	 * Selects a zone based on the name provided as a parameter.
	 * 
	 * @param name
	 *            The name of the searched zone.
	 * @return A collection of zones with the name.
	 */
	Collection<Zone> selectZonesByName(String name);

	/**
	 * Selects a zone based on the UUID provided as a parameter.
	 * 
	 * @param id
	 *            The ID of the searched zone.
	 * @return A zone with the searched UUID.
	 */
	Zone selectZoneByUUID(@Param("id") UUID id);

	/**
	 * Inserts a data into a zone object.
	 * @param zone The measured data that is to be inserted into the object.
	 */
	void insertZone(Zone zone);

	/**
	 * Deletes a zone object from the collection.
	 * @param zone The zone object that is to be deleted from the collection.
	 */
	void deleteZone(Zone zone);
}
