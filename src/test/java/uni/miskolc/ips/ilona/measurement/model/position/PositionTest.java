package uni.miskolc.ips.ilona.measurement.model.position;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testSetParameters() {
		UUID uuid= UUID.randomUUID();
		Coordinate coord= new Coordinate();
		Zone zone = Zone.UNKNOWN_POSITION;
		Position pos = new Position();
		pos.setCoordinate(coord);
		pos.setZone(zone);
		pos.setUUID(uuid);
		if(pos.getUUID().compareTo(uuid)==0 && pos.getCoordinate().equals(coord) && pos.getZone().equals(zone)){
			String positionString = pos.toString();
		}
	}
	
	@Test
	public void testEqualsDifferent(){
		Coordinate coord= new Coordinate();
		Position pos = new Position(coord);
		assertFalse(pos.equals(coord));
		
	}
	@Test
	public void testEqualsSame(){
		Coordinate coord= new Coordinate();
		Position pos = new Position(coord,Zone.UNKNOWN_POSITION);
		assertTrue(pos.equals(pos));
	}
	

}
