package uni.miskolc.ips.ilona.measurement.model.position;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class ZoneTest {

	@Test
	public void testEqualsDifferent() {
		UUID uuid= UUID.randomUUID();
		String name = "Lobby";
		Zone zone = new Zone();
		zone.setId(uuid);
		zone.setName(name);
		if(zone.getId().compareTo(uuid)==0 && zone.getName().equals(name)){
			String zoneString= zone.toString();
			zone.addInternalZone(Zone.UNKNOWN_POSITION);
		}
			zone.addInternalZone(null);
		
		assertFalse(zone.equals(uuid));
		

	}

}
