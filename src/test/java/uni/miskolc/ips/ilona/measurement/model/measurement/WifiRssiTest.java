package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import uni.miskolc.ips.ilona.measurement.model.measurement.wifi.VectorIntersectionWifiRssiDistance;
import uni.miskolc.ips.ilona.measurement.model.measurement.wifi.VectorUnionWifiRssiDistance;

public class WifiRssiTest {

  @Test
  public void testZeroFromItselfIntersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = 0.0;
    double actual = wifiinter.distance(wifi, wifi);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testReflexivityIntersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("One", -1.3);
    wifi2.setRssi("Five", -1.4);
    wifi2.setRssi("Six", -3.3);
    wifi2.setRssi("Four", -2.3);
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    assertEquals(wifiinter.distance(wifi, wifi2), wifiinter.distance(wifi2, wifi), 1e-10);
  }

  @Test
  public void testOneEmptyIntersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = WifiRssiDistanceCalculator.UNKOWN_DISTANCE;
    double actual = wifiinter.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testTwoEmptyIntersection() {
    WifiRssi wifi = new WifiRssi();
    WifiRssi wifi2 = new WifiRssi();
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = WifiRssiDistanceCalculator.UNKOWN_DISTANCE;
    double actual = wifiinter.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDifferentIntersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -8.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("Eight", -2.3);
    wifi2.setRssi("Five", -1.4);
    wifi2.setRssi("Seven", -3.3);
    wifi2.setRssi("Six", -2.6);
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = -1.0;
    double actual = wifiinter.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceIntersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -12.4);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("One", -1.4);
    wifi2.setRssi("Five", -1.4);
    wifi2.setRssi("Two", -333.3);
    wifi2.setRssi("Four", -29.3);
    wifi2.setRssi("Three", -31.3);
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = 0.4734;
    double actual = wifiinter.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-4);
  }

  @Test
  public void testDistance1Intersection() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -12.4);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("One", -1.4);
    wifi2.setRssi("Five", -1.4);
    VectorIntersectionWifiRssiDistance wifiinter = new VectorIntersectionWifiRssiDistance();
    double expected = 1.0;
    double actual = wifiinter.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-4);
  }

  @Test
  public void testZeroFromItselfUnion() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    double expected = 0.0;
    double actual = wifiunion.distance(wifi, wifi);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testReflexivityUnion() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("One", -1.3);
    wifi2.setRssi("Five", -1.4);
    wifi2.setRssi("Six", -3.3);
    wifi2.setRssi("Four", -2.3);
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    assertEquals(wifiunion.distance(wifi, wifi2), wifiunion.distance(wifi2, wifi), 1e-10);
  }

  @Test
  public void testOneEmptyUnion() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    double expected = 1;
    double actual = wifiunion.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testTwoEmptyUnion() {
    WifiRssi wifi = new WifiRssi();
    WifiRssi wifi2 = new WifiRssi();
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    double expected = WifiRssiDistanceCalculator.UNKOWN_DISTANCE;
    double actual = wifiunion.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDifferentUnion() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -1.3);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -8.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("Eight", -2.3);
    wifi2.setRssi("Five", -4.4);
    wifi2.setRssi("Seven", -3.3);
    wifi2.setRssi("Six", -8.6);
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    double expected = 13.686855007634149;
    double actual = wifiunion.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceUnion() {
    WifiRssi wifi = new WifiRssi();
    wifi.setRssi("One", -12.4);
    wifi.setRssi("Two", -1.4);
    wifi.setRssi("Three", -3.3);
    wifi.setRssi("Four", -2.3);
    WifiRssi wifi2 = new WifiRssi();
    wifi2.setRssi("One", -1.4);
    wifi2.setRssi("Five", -1.4);
    wifi2.setRssi("Two", -3.3);
    wifi2.setRssi("Four", -2.9);
    wifi2.setRssi("Three", -1.3);
    WifiRssiDistanceCalculator wifiunion = new VectorUnionWifiRssiDistance(0);
    double expected = 11.442464769445436;
    double actual = wifiunion.distance(wifi, wifi2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testRemoveSSID() {
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("One", -50.3);
    map.put("Two", -70.6);
    WifiRssi wifi = new WifiRssi(map);
    wifi.removeSsid("Two");
    assertEquals(1, wifi.getRssiValues().size(), 1e-10);
  }
}
