package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.junit.Test;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;

public class MeasurementBuilderTest {

  @Test
  public void testSetUnset() {
    MeasurementBuilder measb = new MeasurementBuilder();
    measb.setPosition(new Coordinate());
    measb.unsetPosition();
    measb.setPosition(Zone.UNKNOWN_POSITION);
    measb.unsetPosition();
    measb.setPosition(new Position());
    measb.unsetPosition();

    measb.setBluetoothTags(new BluetoothTags());
    measb.setGpsCoordinates(new GpsCoordinate());
    measb.setMagnetometer(new Magnetometer());
    measb.setRfidTags(new RfidTags());
    measb.setWifiRssi(new WifiRssi());

    measb.unsetBluetoothTags();
    measb.unsetGpsCoordinates();
    measb.unsetMagnetometer();
    measb.unsetRfidTags();
    measb.unsetWifiRssi();
  }
}
