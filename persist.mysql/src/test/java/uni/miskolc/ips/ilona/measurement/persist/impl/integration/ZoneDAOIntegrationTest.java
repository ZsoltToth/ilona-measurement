package uni.miskolc.ips.ilona.measurement.persist.impl.integration;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;

public class ZoneDAOIntegrationTest extends SetupIntegrationTest {

	private ZoneDAO dao;

	@Before
	public void setUp() {
		super.setUp();
		try {
			this.dao = new MySQLZoneDAO(MYBATIS_CONFIG_FILE, HOST, PORT,
					DATABASE, USER, PASSWORD);
		} catch (FileNotFoundException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void testCreateZone() throws InsertionException, RecordNotFoundException {
		Zone expected = new Zone("lobby");
		this.dao.createZone(expected);
		Zone actual = dao.readZone(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected = InsertionException.class)
	public void testCreateExistingZone() throws InsertionException, RecordNotFoundException {
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
		this.dao.createZone(bathroom);
		
	}

	@Test
	public void testReadZones() {
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
		Zone kitchen = new Zone("kitchen");
		kitchen.setId(UUID.fromString("743d2365-2eaa-412f-8324-6b6b1361ba5b"));
		Zone bedroom = new Zone("bedroom");
		bedroom.setId(UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8"));

		Collection<Zone> expected = Arrays.asList(bathroom, kitchen, bedroom);

		Collection<Zone> actual = dao.readZones();

		assertThat(actual,
				hasItems(expected.toArray(new Zone[expected.size()])));
	}

	@Test
	public void testReadZonesByName() {
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));

		Collection<Zone> expected = Arrays.asList(bathroom);

		Collection<Zone> actual = dao.readZones("bathroom");

		assertThat(actual,
				hasItems(expected.toArray(new Zone[expected.size()])));
	}

	@Test
	public void testReadZonesByNameNoMatch() {

		Collection<Zone> expected = new ArrayList<Zone>();

		Collection<Zone> actual = dao.readZones("Unknown Zone");

		assertThat(actual,
				hasItems(expected.toArray(new Zone[expected.size()])));
	}

	@Test
	public void testReadZone() throws RecordNotFoundException {
		UUID id = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone expected = new Zone("bathroom");
		expected.setId(id);

		Zone actual = dao.readZone(id);
		assertEquals(expected, actual);
	}

	@Test(expected = RecordNotFoundException.class)
	public void testReadZoneUnknownID() throws RecordNotFoundException {
		UUID id = UUID.fromString("9ff78a6a-0000-4f38-bfeb-5fa189b6421b");
		Zone expected = new Zone("bathroom");
		expected.setId(id);

		Zone actual = dao.readZone(id);
		assertEquals(expected, actual);
	}

	@Test(expected = RecordNotFoundException.class)
	public void testDeleteZone() throws RecordNotFoundException {
		UUID id = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone zone = new Zone("bathroom");
		zone.setId(id);
		dao.deleteZone(zone);
		dao.readZone(id);
	}
	
//	@Test(expected= RecordNotFoundException.class)
//	public void testDeleteUnknownZone() throws RecordNotFoundException {
//		UUID id = UUID.fromString("9ff78a6a-0000-4f38-bfeb-5fa189b6421b");
//		Zone zone = new Zone("bathroom");
//		zone.setId(id);
//
//		dao.deleteZone(zone);
//	}

}
