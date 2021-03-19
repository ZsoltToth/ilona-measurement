package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class RfidTagsTest {

  @Test
  public void testDistanceFromItselfIsZero() {
    Set<byte[]> data = new HashSet<byte[]>();
    data.add(new byte[] {0x4A, 0x1A, (byte) 52});
    data.add(new byte[] {0x4B, 0x1B, (byte) 52});
    RfidTags rfid = new RfidTags(data);
    double expected = 0.0;
    double actual = rfid.distance(rfid);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceReflexivity() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {0x4A, 0x1A, (byte) 62});
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {0x2A, 0x1A, (byte) 52});
    RfidTags rfid2 = new RfidTags(data2);
    assertEquals(rfid1.distance(rfid2), rfid2.distance(rfid1), 1e-10);
  }

  @Test
  public void testDistanceOneEmptySet() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {0x4A, 0x1A, (byte) 52});
    RfidTags rfid2 = new RfidTags(data2);
    double expected = 1.0;
    double actual = rfid1.distance(rfid2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceAllEmptySet() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    RfidTags rfid1 = new RfidTags(data1);
    RfidTags rfid2 = new RfidTags(data1);
    double expected = 0;
    double actual = rfid1.distance(rfid2);

    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistanceDifferent() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {0x4A, 0x1A, (byte) 25});
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {0x4B, 0x1B, (byte) 0xAB});
    RfidTags rfid2 = new RfidTags(data2);
    double expected = 1;
    double actual = rfid1.distance(rfid2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testDistance() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {0x1A, 0x2A, 0x3A});
    data1.add(new byte[] {0x4A, 0x5A, 0x6A});
    RfidTags rfid1 = new RfidTags(data1);

    Set<byte[]> data2 = new HashSet<byte[]>();

    data2.add(new byte[] {0x4A, 0x5A, 0x6A});
    data2.add(new byte[] {0x1A, 0x1B, 0x2B});

    RfidTags rfid2 = new RfidTags(data2);
    double expected = (1 - 0.3333333333333);
    double actual = rfid1.distance(rfid2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testIn() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    data1.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    RfidTags rfid2 = new RfidTags(data2);
    double expected = 0.5;
    double actual = rfid1.distance(rfid2);
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testIntersection() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    data1.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data1.add(new byte[] {(byte) 33, (byte) 11, (byte) 76});
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data2.add(new byte[] {(byte) 32, (byte) 15, (byte) 76});
    data2.add(new byte[] {(byte) 32, (byte) 11, (byte) 71});
    data2.add(new byte[] {(byte) 32, (byte) 19, (byte) 76});
    RfidTags rfid2 = new RfidTags(data2);
    int expected = 1;
    int actual = rfid1.intersection(rfid2).size();
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testUnion() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    data1.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data1.add(new byte[] {(byte) 33, (byte) 11, (byte) 76});
    RfidTags rfid1 = new RfidTags(data1);
    Set<byte[]> data2 = new HashSet<byte[]>();
    data2.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data2.add(new byte[] {(byte) 32, (byte) 15, (byte) 76});
    data2.add(new byte[] {(byte) 32, (byte) 11, (byte) 71});
    data2.add(new byte[] {(byte) 32, (byte) 19, (byte) 76});
    RfidTags rfid2 = new RfidTags(data2);
    int expected = 6;
    int actual = rfid1.union(rfid2).size();
    assertEquals(expected, actual, 1e-10);
  }

  @Test
  public void testEquals() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    data1.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data1.add(new byte[] {(byte) 33, (byte) 11, (byte) 76});
    RfidTags rfid1 = new RfidTags();
    rfid1.setTags(data1);
    rfid1.removeTag(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    assertEquals(true, rfid1.equals(rfid1));
  }

  @Test
  public void testNotEquals() {
    Set<byte[]> data1 = new HashSet<byte[]>();
    data1.add(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    data1.add(new byte[] {(byte) 32, (byte) 11, (byte) 76});
    data1.add(new byte[] {(byte) 33, (byte) 11, (byte) 76});
    RfidTags rfid1 = new RfidTags();
    rfid1.setTags(data1);
    rfid1.removeTag(new byte[] {(byte) 1, (byte) 2, (byte) 3});
    assertEquals(false, rfid1.equals(new ArrayList<RfidTags>()));
  }
}
