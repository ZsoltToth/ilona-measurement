package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.web.MeasurementApp;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MeasurementApp.class)
public class MySqlPositionDaoIntegrationTest extends SetupIntegrationTest {
    @Autowired
    private MySqlPositionDao positionDAO;

    @Test
    public void readPositions() {
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b"));
        Zone kitchen = new Zone("kitchen");
        kitchen.setId(UUID.fromString("743d2365-2eaa-412f-8324-6b6b1361ba5b"));
        Zone bedroom = new Zone("bedroom");
        bedroom.setId(UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8"));

        Position pos0 = new Position(new Coordinate(0, 0, 0), bathroom);
        pos0.setUuid(UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15"));

        Position pos1 = new Position(new Coordinate(1, 1, 1), kitchen);
        pos1.setUuid(UUID.fromString("5f484241-6dcc-4731-846c-7fc3e4f0fafb"));

        Position pos2 = new Position(new Coordinate(2, 2, 2), bedroom);
        pos2.setUuid(UUID.fromString("c36e7f61-ba7b-408f-b113-c528980e7131"));

        Collection<Position> expected = Arrays.asList(pos0, pos1, pos2);

        Collection<Position> actual = positionDAO.readPositions();

        assertThat(actual, hasItems(expected.toArray(new Position[expected.size()])));
    }

    @Test
    public void getPosition() throws RecordNotFoundException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
        Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
        expected.setUuid(posId);

        Position actual = positionDAO.getPosition(posId);

        System.out.println("actual --> " + actual);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getPositionIdNotFound() throws RecordNotFoundException {
        UUID unknownZoneId = UUID.fromString("9ff78a6a-0000-0000-bfeb-5fa189b6421b");

        positionDAO.getPosition(unknownZoneId);
    }

    @Test
    public void createPosition() throws InsertionException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
        Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
        expected.setUuid(posId);

        positionDAO.createPosition(expected);
    }

    @Test
    public void createPositionCreatesZone() throws RecordNotFoundException, InsertionException {
        UUID zoneId = UUID.fromString("827cdad2-d362-3fac-8114-8a18322d043b");
        Zone testZone = new Zone("EZ");
        testZone.setId(zoneId);

        UUID posId = UUID.fromString("7b720c38-588d-410e-87d9-fa9d951cbc6a");
        Position expected = new Position(new Coordinate(3, 3, 3), testZone);
        expected.setUuid(posId);

        positionDAO.createPosition(expected);

        Position actual = positionDAO.getPosition(UUID.fromString("7b720c38-588d-410e-87d9-fa9d951cbc6a"));
        Assert.assertEquals(expected.getZone().getId(), actual.getZone().getId());
    }

    @Test(expected = InsertionException.class)
    public void createPositionDuplicatedEntry() throws InsertionException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
        Position expected = new Position(new Coordinate(4, 0, 0), bathroom);
        expected.setUuid(posId);

        positionDAO.createPosition(expected);
    }

    @Test()
    public void updatePosition() throws RecordNotFoundException {
        UUID zoneId = UUID.fromString("183f0204-5029-4b33-a128-404ba5c68fa8");
        Zone bathroom = new Zone("bedroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
        Position expected = new Position(new Coordinate(1, 2, 3), bathroom);
        expected.setUuid(posId);

        positionDAO.updatePosition(expected);

        Position actual = positionDAO.getPosition(posId);
        System.out.println(actual);
        System.out.println(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RecordNotFoundException.class)
    public void updatePositionNotFound() throws RecordNotFoundException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
        Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
        expected.setUuid(posId);

        positionDAO.updatePosition(expected);
    }

    @Test()
    public void deletePosition() throws RecordNotFoundException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-70f16f283a15");
        Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
        expected.setUuid(posId);
        int expectedSize = positionDAO.readPositions().size() - 1;
        positionDAO.deletePosition(expected);
        int actualSize = positionDAO.readPositions().size();
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test(expected = RecordNotFoundException.class)
    public void deletePositionNotFound() throws RecordNotFoundException {
        UUID zoneId = UUID.fromString("9ff78a6a-2216-4f38-bfeb-5fa189b6421b");
        Zone bathroom = new Zone("bathroom");
        bathroom.setId(zoneId);

        UUID posId = UUID.fromString("eb264eea-4106-46a3-9992-0123456789ab");
        Position expected = new Position(new Coordinate(0, 0, 0), bathroom);
        expected.setUuid(posId);
        positionDAO.deletePosition(expected);
    }

    @Test()
    public void readPositionFromArray() {
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
        pos1.setUuid(posId1);
        UUID posId2 = UUID.fromString("5f484241-6dcc-4731-846c-7fc3e4f0fafb");
        Position pos2 = new Position(new Coordinate(0, 0, 0), kitchen);
        pos2.setUuid(posId2);
        UUID posId3 = UUID.fromString("c36e7f61-ba7b-408f-b113-c528980e7131");
        Position pos3 = new Position(new Coordinate(0, 0, 0), bathroom);
        pos3.setUuid(posId3);

        Collection<Position> expected = Arrays.asList(pos1, pos2, pos3);
        Collection<Position> actual = positionDAO.readPositions();
        assertThat(actual,
                hasItems(expected.toArray(new Position[expected.size()])));
    }
}