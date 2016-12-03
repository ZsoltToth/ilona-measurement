package uni.miskolc.ips.ilona.measurement.persist.mysql;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mappers.ZoneMapper;


public class ZoneDAOTest {

	private MySQLZoneDAO zoneDAO;
	private final UUID kitchenId = UUID.randomUUID();
	private final UUID UNKNOWN_ZONE_ID = UUID.randomUUID();
	private Collection<Zone> zones;
	
	@Before
	public void setUp(){
		ZoneMapper mapper = EasyMock.createMock(ZoneMapper.class);
		SqlSession session =  EasyMock.createMock(SqlSession.class);
		SqlSessionFactory sessionFactory = EasyMock.createMock(SqlSessionFactory.class);
		
		Zone kitchen = new Zone("Kitchen");
		kitchen.setId(kitchenId);
		Zone bedroom = new Zone("Bedroom");
		Zone livingroom = new Zone("Living Room");
		
		zones = Arrays.asList(kitchen, bedroom, livingroom);
		
		//mapper => Collection<Zone> selectZones();
		EasyMock.expect(mapper.selectZones()).andReturn(zones);
		
		//mapper => Collection<Zone> selectZonesByName(String name);
		EasyMock.expect(mapper.selectZoneByUUID(kitchenId)).andReturn(kitchen);
		EasyMock.expect(mapper.selectZoneByUUID(UNKNOWN_ZONE_ID)).andThrow(new PersistenceException());

		//mapper => Zone selectZoneByUUID(@Param("id") UUID id);
		EasyMock.expect(mapper.selectZonesByName("Kitchen")).andReturn(Arrays.asList(kitchen));
		EasyMock.expect(mapper.selectZonesByName("Bedroom")).andReturn(Arrays.asList(bedroom));
		EasyMock.expect(mapper.selectZonesByName("Living Room")).andReturn(Arrays.asList(livingroom));
		
		EasyMock.expect(sessionFactory.openSession()).andReturn(session);
		EasyMock.expect(session.getMapper(ZoneMapper.class)).andReturn(mapper);
		session.close();
		EasyMock.replay(mapper,session,sessionFactory);
		
		zoneDAO = new MySQLZoneDAO(sessionFactory);
		
	}
	
	@Test
	public void testMySQLZoneDAO() {
		
	}

	@Ignore
	@Test
	public void testCreateZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadZones() {
		Collection<Zone> expected = zones;
		Collection<Zone> actual = zoneDAO.readZones();
		assertThat(actual, hasItems(expected.toArray(new Zone[expected.size()])));
	}

	@Test
	public void testReadZonesString() {
		Zone kitchen = new Zone("Kitchen");
		kitchen.setId(kitchenId);
		Collection<Zone> expected = Arrays.asList(kitchen);
		Collection<Zone> actual = zoneDAO.readZones("Kitchen");
		assertThat(actual, hasItems(expected.toArray(new Zone[expected.size()])));
	}

	@Test
	public void testReadZone() throws RecordNotFoundException {
		Zone kitchen = new Zone("Kitchen");
		kitchen.setId(kitchenId);
		Zone actual = zoneDAO.readZone(kitchenId);
		assertEquals(kitchen, actual);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void testReadZoneNotFound() throws RecordNotFoundException {
		Zone actual = zoneDAO.readZone(UNKNOWN_ZONE_ID);
	}

	@Ignore
	@Test
	public void testDeleteZone() {
		fail("Not yet implemented");
	}

}
