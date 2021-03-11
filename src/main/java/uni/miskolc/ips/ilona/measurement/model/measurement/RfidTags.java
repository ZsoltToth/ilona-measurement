package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RfidTags {

    private static final Logger LOG = LogManager.getLogger(RfidTags.class);

    private Set<byte[]> tags;

    public RfidTags() {
        super();
        tags = new HashSet<>();
    }

    public RfidTags(Set<byte[]> tags) {
        super();
        this.tags = trimTagsSet(tags);
    }

    public Set<byte[]> getTags() {
        return tags;
    }

    public void setTags(Set<byte[]> tags) {
        this.tags = trimTagsSet(tags);
    }

    public void addTag(byte[] tag) {
        this.tags.add(trimTags(tag));
    }

    public void removeTag(byte[] tag) {
        this.tags.remove(trimTags(tag));
    }

    public double distance(RfidTags other) {
        double result;

        if (this.getTags().isEmpty() && other.getTags().isEmpty()) {
            return 0.0;
        }
        if (this.getTags().isEmpty() || other.getTags().isEmpty()) {
            return 1.0;
        }

        Set<byte[]> intersection = this.intersection(other);
        Set<byte[]> union = this.union(other);

        result = 1 - ((double) intersection.size() / (double) union.size());
        LOG.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), other.toString(), result));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RfidTags rfidTags = (RfidTags) o;

        return tags.equals(rfidTags.tags);
    }

    @Override
    public int hashCode() {
        return tags != null ? tags.hashCode() : 0;
    }

    protected Set<byte[]> union(RfidTags other) {
        Set<byte[]> union = new HashSet<>();
        union.addAll(this.intersection(other));

        for (byte[] thiseach : this.tags) {
            for (byte[] each : other.tags) {
                if (!Arrays.equals(thiseach, each)) {

                    if (this.contain(union, each) && this.contain(union, thiseach)) {
                        continue;
                    } else if (this.contain(union, each)) {
                        union.add(thiseach);
                    } else if (this.contain(union, thiseach)) {
                        union.add(each);
                    } else {
                        union.add(thiseach);
                        union.add(each);
                    }
                }
            }
        }
        return union;
    }

    protected Set<byte[]> intersection(RfidTags other) {
        Set<byte[]> intersection = new HashSet<>();
        for (byte[] thiseach : this.tags) {
            for (byte[] each : other.tags) {
                if (Arrays.equals(thiseach, each)) {
                    intersection.add(each);
                }
            }
        }
        return intersection;
    }

    private boolean contain(Set<byte[]> set, byte[] in) {
        for (byte[] each : trimTagsSet(set)) {
            if (Arrays.equals(each, trimTags(in))) {
                return true;
            }
        }
        return false;
    }

    private byte[] trimTags(byte[] tags) {
        int numOfGoodValues = 0;
        for (byte b : tags) {
            if (b != 0) {
                numOfGoodValues++;
            }
        }
        byte[] result = new byte[numOfGoodValues];
        int i = 0;
        for (byte b : tags) {
            if (b != 0) {
                result[i] = b;
                i++;
            }
        }
        return result;
    }

    private Set<byte[]> trimTagsSet(Set<byte[]> tags) {
        Set<byte[]> result = new HashSet<>();
        for (byte[] tag : tags) {
            result.add(trimTags(tag));
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("RFIDTags =");
        if (tags == null) {
            return result.toString();
        }
        int i = 1, j;
        for (byte[] each : tags) {
            j = 1;
            result.append("[");
            for (byte eachbyte : each) {
                result.append(eachbyte);
                if (j < each.length) {
                    result.append(",");
                }
                j++;
            }

            result.append("]");
            if (i < tags.size()) {
                result.append(";");
            }
            i++;
        }
        return result.toString();
    }
}
