package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import org.junit.Test;



public class GPSCoordinateTest {

	@Test
	public void testDistanceFromItselfIsZero() {
		GPSCoordinate coord = new GPSCoordinate(0, 0, 0);
		double expected = 0.0;
		double actual = coord.distance(coord);
		assertEquals(expected, actual, 1e-10);
	}
	
	@Test
	public void testDistanceReflexivity() {
		GPSCoordinate coord1 = new GPSCoordinate();
		coord1.setAltitude(0.0);
		coord1.setLatitude(0.0);
		coord1.setLongitude(0.0);
		GPSCoordinate coord2 = new GPSCoordinate(1, 1, 1);
		assertEquals(coord1.distance(coord2),coord2.distance(coord1),1e-10);
	}
	
	@Test
	public void testDistanceReflexivity2PI() {
		GPSCoordinate coord1 = new GPSCoordinate(0, 0, 1);
		GPSCoordinate coord2 = new GPSCoordinate(2*Math.PI, 4*Math.PI, 1);
		double expected = 0.0;
		double actual = coord1.distance(coord2);
		assertEquals(expected, actual, 1e-10);
	}
	
	@Test
	public void testDistanceDifferenceInRadius() {
		GPSCoordinate coord1 = new GPSCoordinate(0, 0, 0);
		GPSCoordinate coord2 = new GPSCoordinate(0, 0, 1);
		double expected = 1.0;
		double actual = coord1.distance(coord2);
		assertEquals(expected, actual, 1e-10);
	}
	
	
	@Test
	public void testDistance() {
		GPSCoordinate coord1 = new GPSCoordinate(0.83972309, 0.362571349, 6371000 + 124.081);
		GPSCoordinate coord2 = new GPSCoordinate(0.839236868, 0.362733136, 6371000 + 125.65);
		double expected = 3000;
		double actual = coord1.distance(coord2);
		assertEquals(expected, actual,200);
	}
}
