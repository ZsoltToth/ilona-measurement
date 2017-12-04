package uni.miskolc.ips.ilona.measurement.persist.impl.integration;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLPositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;

public class PositionDAOIntegrationTest extends SetupIntegrationTest {

	private MySQLPositionDAO dao;
	
	@Before
	public void setUp(){
		super.setUp();
		try {
			ZoneDAO zoneDAO = new MySQLZoneDAO(MYBATIS_CONFIG_FILE, HOST, PORT, DATABASE, USER, PASSWORD);
			this.dao = new MySQLPositionDAO(MYBATIS_CONFIG_FILE, HOST, PORT, DATABASE, USER, PASSWORD, zoneDAO);
		} catch (FileNotFoundException e) {
			Assume.assumeNoException(e);
		}
	}
	
	@Test
	public void testReadPositions(){
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
		Zone kitchen = new Zone("kitchen");
		kitchen.setId(UUID.fromString("743d2365-2eaa-412f-8324-6b6b1361ba5b"));
		Zone bedroom = new Zone("bedroom");
		bedroom.setId(UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8"));
		
		Position pos0 = new Position(new Coordinate(0, 0, 0), bathroom);
		pos0.setUUID(UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15"));
		
		Position pos1 = new Position(new Coordinate(1, 1, 1), kitchen);
		pos1.setUUID(UUID.fromString("5f484241-6dcc-4731-846c-7fc3e4f0fafb"));
		
		Position pos2 = new Position(new Coordinate(2, 2, 2), bedroom);
		pos2.setUUID(UUID.fromString("c36e7f61-ba7b-408f-b113-c528980e7131"));
		
		Collection<Position> expected = Arrays.asList(pos0, pos1, pos2);
		
		Collection<Position> actual = dao.readPositions();
		
		assertThat(actual, hasItems(expected.toArray(new Position[expected.size()])));
	}
	
	@Test
	public void testReadPositionByUUID() throws RecordNotFoundException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
				
		Position actual = dao.getPosition(posId);
		
		System.out.println("actual --> "+actual);
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void testReadPositionByUUIDNotFound() throws RecordNotFoundException{
		UUID unknownZoneId = UUID.fromString("9ff78a6a-0000-0000-bfeb-5fa189b6421b");
				
		Position actual = dao.getPosition(unknownZoneId);
	}
	
	@Test
	public void testInsertPosition() throws InsertionException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		
		dao.createPosition(expected);
		
	}
	
	@Test(expected = InsertionException.class)
	public void testInsertPositionDuplicatedEntry() throws InsertionException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		
		dao.createPosition(expected);
	}
	
	@Test()
	public void testUpdatePosition() throws RecordNotFoundException{
		UUID zoneId = UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8");
		Zone bathroom = new Zone("bedroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		
		dao.updatePosition(expected);
		
		Position actual = dao.getPosition(posId);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void testUpdatePositionNotFound() throws RecordNotFoundException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		
		dao.updatePosition(expected);
	}
	
	@Test()
	public void testDeletePosition() throws RecordNotFoundException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		int expectedSize = dao.readPositions().size() -1;
		dao.deletePosition(expected);
		int actualSize = dao.readPositions().size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void testDeletePositionNotFound() throws RecordNotFoundException{
		UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
		Zone bathroom = new Zone("bathroom");
		bathroom.setId(zoneId);
		
		UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
		Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
		expected.setUUID(posId);
		dao.deletePosition(expected);
	}
	
	@Test()
	public void TestReadPositionFromArray () throws RecordNotFoundException, InsertionException{
	UUID zoneId1 = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
	Zone bathroom = new Zone("bathroom");
	bathroom.setId(zoneId1);
	UUID zoneId2 = UUID.fromString("743d2365-2eaa-412f-8324-6b6b1361ba5b");
	Zone kitchen = new Zone("kitchen");
	kitchen.setId(zoneId2);
	UUID zoneId3 = UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8");
	Zone bedroom = new Zone("bedroom");
	bedroom.setId(zoneId3);
	
	UUID posId1 = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
	Position pos1= new Position(new Coordinate(0,0,0), bathroom);
	pos1.setUUID(posId1);
	UUID posId2 = UUID.fromString("5f484241-6dcc-4731-846c-7fc3e4f0fafb");
	Position pos2= new Position(new Coordinate(0,0,0), kitchen);
	pos2.setUUID(posId2);
	UUID posId3 = UUID.fromString("c36e7f61-ba7b-408f-b113-c528980e7131");
	Position pos3= new Position(new Coordinate(0,0,0), bathroom);
	pos3.setUUID(posId3);
	
	Collection<Position> expected=Arrays.asList(pos1, pos2, pos3);
	Collection<Position> actual=dao.readPositions();
	assertThat(actual,
			hasItems(expected.toArray(new Position[expected.size()])));
	}
}
