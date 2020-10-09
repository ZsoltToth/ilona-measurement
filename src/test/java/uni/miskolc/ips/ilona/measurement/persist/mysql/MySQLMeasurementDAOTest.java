package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uni.miskolc.ips.ilona.measurement.model.measurement.*;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.web.MeasurementApp;

import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MeasurementApp.class)
public class MySQLMeasurementDAOTest extends SetupIntegrationTest {
    @Autowired
    private MySQLMeasurementDAO measurementDAO;

    @Test()
    public void readMeasurementFromArray() {
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
        Position pos1 = new Position(new Coordinate(0, 0, 0), bathroom);
        pos1.setUUID(posId1);
        UUID posId2 = UUID.fromString("5f484241-6dcc-4731-846c-7fc3e4f0fafb");
        Position pos2 = new Position(new Coordinate(0, 0, 0), kitchen);
        pos2.setUUID(posId2);
        UUID posId3 = UUID.fromString("c36e7f61-ba7b-408f-b113-c528980e7131");
        Position pos3 = new Position(new Coordinate(0, 0, 0), bathroom);
        pos3.setUUID(posId3);

        UUID mesID1 = UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc");
        MeasurementBuilder mesbuild1 = new MeasurementBuilder();
        mesbuild1.setPosition(pos1);
        mesbuild1.setMagnetometer(new Magnetometer(0, 0, 0, 0));
        mesbuild1.setGPSCoordinates(new GPSCoordinate(00, 00, 00));
        Measurement mes1 = mesbuild1.build();
        mes1.setTimestamp(Timestamp.valueOf("2015-07-10 15:00:00"));
        mes1.setId(mesID1);

        UUID mesID2 = UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc");
        MeasurementBuilder mesbuild2 = new MeasurementBuilder();
        mesbuild2.setPosition(pos2);
        mesbuild2.setMagnetometer(new Magnetometer(1, 1, 1, 1));
        mesbuild2.setGPSCoordinates(new GPSCoordinate(11, 11, 11));
        Measurement mes2 = mesbuild2.build();
        mes2.setTimestamp(Timestamp.valueOf("2015-07-11 20:00:00"));
        mes2.setId(mesID2);

        UUID mesID3 = UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc");
        MeasurementBuilder mesbuild3 = new MeasurementBuilder();
        mesbuild3.setPosition(pos3);
        mesbuild3.setMagnetometer(new Magnetometer(2, 2, 2, 2));
        mesbuild3.setGPSCoordinates(new GPSCoordinate(22, 22, 22));
        Measurement mes3 = mesbuild3.build();
        mes3.setTimestamp(Timestamp.valueOf("2015-07-12 12:00:00"));
        mes3.setId(mesID3);

        Collection<Measurement> expected = Arrays.asList(mes1, mes2, mes3);
        Collection<Measurement> actual = measurementDAO.readMeasurements();
        assertThat(actual, hasItems(expected.toArray(new Measurement[expected.size()])));
    }

    @Test
    public void createMeasurement() throws InsertionException {
        Measurement measurement = createMeasurementInstance();
        measurementDAO.createMeasurement(measurement);
        Collection<Measurement> measurements = measurementDAO.readMeasurements();
        assertTrue(measurements.contains(measurement));
        Measurement actual = measurementDAO.readMeasurement(measurement.getId());
        Assert.assertEquals(measurement, actual);
        Assert.assertEquals(measurement.getGpsCoordinates(), actual.getGpsCoordinates());
        Assert.assertEquals(measurement.getBluetoothTags(), actual.getBluetoothTags());
        Assert.assertEquals(measurement.getWifiRSSI(), actual.getWifiRSSI());
        //Assert.assertArrayEquals(measurement.getRfidtags().getTags().toArray(), actual.getRfidtags().getTags().toArray());
    }

    @Test
    public void readMeasurement() {
        Measurement expected = new Measurement();
        expected.setId(UUID.fromString("4304fe32-8028-4830-bebc-dd1d535e5cfd"));
        Measurement actual = measurementDAO.readMeasurement(UUID.fromString("4304fe32-8028-4830-bebc-dd1d535e5cfd"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateMeasurement() throws InsertionException, RecordNotFoundException {
        Measurement measurement = createMeasurementInstance();
        measurement.setId(UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc"));
        measurementDAO.updateMeasurement(measurement);
        Measurement actual = measurementDAO.readMeasurement(UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc"));
        Assert.assertEquals(measurement, actual);

        Assert.assertEquals(measurement.getPosition(), actual.getPosition());
        Assert.assertEquals(measurement.getGpsCoordinates(), actual.getGpsCoordinates());
        Assert.assertEquals(measurement.getMagnetometer(), actual.getMagnetometer());
        Assert.assertEquals(measurement.getBluetoothTags(), actual.getBluetoothTags());
        Assert.assertEquals(measurement.getWifiRSSI(), actual.getWifiRSSI());
        //Assert.assertArrayEquals(measurement.getRfidtags().getTags().toArray(), actual.getRfidtags().getTags().toArray());
    }

    @Test
    public void deleteMeasurementByMeasurement() throws RecordNotFoundException {
        UUID zoneId1 = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId1);

        UUID posId1 = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
        Position pos1 = new Position(new Coordinate(0, 0, 0), bathroom);
        pos1.setUUID(posId1);

        UUID mesID1 = UUID.fromString("59d46ae9-e0c8-48d0-b14a-503ed414b7cc");
        MeasurementBuilder mesbuild1 = new MeasurementBuilder();
        mesbuild1.setPosition(pos1);
        mesbuild1.setMagnetometer(new Magnetometer(0, 0, 0, 0));
        mesbuild1.setGPSCoordinates(new GPSCoordinate(00, 00, 00));
        Measurement mes1 = mesbuild1.build();
        mes1.setTimestamp(Timestamp.valueOf("2015-07-10 15:00:00"));
        mes1.setId(mesID1);

        measurementDAO.deleteMeasurement(mes1);

        assertEquals(2, measurementDAO.readMeasurements().size());
    }

    private Measurement createMeasurementInstance() {
        MeasurementBuilder measurementBuilder = new MeasurementBuilder();
        measurementBuilder.setMagnetometer(new Magnetometer(4, 4, 4, 4));
        measurementBuilder.setGPSCoordinates(new GPSCoordinate(3, 2, 1));
        Map<String, Double> wifirssi = new HashMap<>();
        wifirssi.put("testAP1", -1.0);
        wifirssi.put("testAP2", -2.0);
        wifirssi.put("testAP3", -3.0);
        measurementBuilder.setWifiRSSI(new WiFiRSSI(wifirssi));
        measurementBuilder.setBluetoothTags(new BluetoothTags(new HashSet<>(Arrays.asList("bt1", "bt2", "bt3"))));
        measurementBuilder.setPosition(new Position(new Coordinate(3, 3, 3), new Zone("EZ")));
        RFIDTags rfid = new RFIDTags(new HashSet<>());
        rfid.addTag(new byte[]{(byte) 12});
        rfid.addTag(new byte[]{(byte) -82, (byte) 34});
        measurementBuilder.setRFIDTags(rfid);
        return measurementBuilder.build();
    }
}