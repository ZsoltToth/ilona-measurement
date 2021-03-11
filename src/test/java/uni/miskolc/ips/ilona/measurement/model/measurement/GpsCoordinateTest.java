package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import org.junit.Test;

public class GpsCoordinateTest {

  @Test
  public void testDistanceFromItselfIsZero() {
    GpsCoordinate coord = new GpsCoordinate(0, 0, 0);
    double expected = 0.0;
    double actual = coord.distance(coord);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceReflexivity() {
    GpsCoordinate coord1 = new GpsCoordinate();
    coord1.setAltitude(0.0);
    coord1.setLatitude(0.0);
    coord1.setLongitude(0.0);
    GpsCoordinate coord2 = new GpsCoordinate(1, 1, 1);
    assertEquals(coord1.distance(coord2), coord2.distance(coord1), 1e-10);
  }

  @Test
  public void testDistanceReflexivity2PI() {
    GpsCoordinate coord1 = new GpsCoordinate(0, 0, 1);
    GpsCoordinate coord2 = new GpsCoordinate(2 * Math.PI, 4 * Math.PI, 1);
    double expected = 0.0;
    double actual = coord1.distance(coord2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceDifferenceInRadius() {
    GpsCoordinate coord1 = new GpsCoordinate(0, 0, 0);
    GpsCoordinate coord2 = new GpsCoordinate(0, 0, 1);
    double expected = 1.0;
    double actual = coord1.distance(coord2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistance() {
    GpsCoordinate coord1 = new GpsCoordinate(0.83972309, 0.362571349, 6371000 + 124.081);
    GpsCoordinate coord2 = new GpsCoordinate(0.839236868, 0.362733136, 6371000 + 125.65);
    double expected = 3000;
    double actual = coord1.distance(coord2);
    assertEquals(expected, actual, 200);
  }
}
