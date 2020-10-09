package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.web.MeasurementApp;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MeasurementApp.class)
public class MySQLZoneDAOIntegrationTest extends SetupIntegrationTest {
    @Autowired
    private MySQLZoneDAO zoneDAO;

    @Test
    public void createZone() throws InsertionException, RecordNotFoundException {
        Zone expected = new Zone("lobby");
        zoneDAO.createZone(expected);
        Zone actual = zoneDAO.readZone(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InsertionException.class)
    public void createExistingZone() throws InsertionException {
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
        zoneDAO.createZone(bathroom);
    }

    @Test
    public void readZones() {
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
        Zone kitchen = new Zone("kitchen");
        kitchen.setId(UUID.fromString("743d2365-2eaa-412f-8324-6b6b1361ba5b"));
        Zone bedroom = new Zone("bedroom");
        bedroom.setId(UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8"));

        Collection<Zone> expected = Arrays.asList(bathroom, kitchen, bedroom);

        Collection<Zone> actual = zoneDAO.readZones();

        assertThat(actual, hasItems(expected.toArray(new Zone[0])));
    }

    @Test
    public void readZonesByName() {
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));

        Collection<Zone> expected = Collections.singletonList(bathroom);

        Collection<Zone> actual = zoneDAO.readZones("bathroom");

        assertThat(actual, hasItems(expected.toArray(new Zone[expected.size()])));
    }

    @Test
    public void readZonesByNameNoMatch() {
        Collection<Zone> expected = new ArrayList<>();

        Collection<Zone> actual = zoneDAO.readZones("Unknown Zone");

        assertThat(actual, hasItems(expected.toArray(new Zone[expected.size()])));
    }

    @Test
    public void readZone() throws RecordNotFoundException {
        UUID id = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone expected = new Zone("bathroom");
        expected.setId(id);

        Zone actual = zoneDAO.readZone(id);
        assertEquals(expected, actual);
    }

    @Test(expected = RecordNotFoundException.class)
    public void readZoneUnknownId() throws RecordNotFoundException {
        UUID id = UUID.fromString("9ff78a6a-0000-4f38-bfeb-5fa189b6421b");
        Zone expected = new Zone("bathroom");
        expected.setId(id);

        Zone actual = zoneDAO.readZone(id);
        assertEquals(expected, actual);
    }

    @Test(expected = RecordNotFoundException.class)
    public void deleteZone() throws RecordNotFoundException {
        UUID id = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone zone = new Zone("bathroom");
        zone.setId(id);
        zoneDAO.deleteZone(zone);
        zoneDAO.readZone(id);
    }
}