package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tamas13, comment made by kun3
 *     Represents the measurement of the bluetooth sensor and
 *     calculates the distance of two bluetooth sensor measurement
 */
@Slf4j
@ToString
@EqualsAndHashCode
public class BluetoothTags {

    private Set<String> tags;

    public BluetoothTags() {
        super();
    }

    public BluetoothTags(final Set<String> tags) {
        super();
        this.tags = tags;
    }

    public final Set<String> getTags() {
        return tags;
    }

    public final void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    public final void addTag(final String tag) {
        this.tags.add(tag);
    }

    public final void removeTag(final String tag) {
        this.tags.remove(tag);
    }

    /**
     * It calculates the distance of two sets based on the Jaccard index of the BluetoothTag objects.
     * The Jaccard index is the ratio of the size of the intersection and the size of the union of the
     * given two sets.
     *
     * @param other as BluetoothTags
     * @return result double as distance
     */
    public final double distance(final BluetoothTags other) {
        double result = 0.0;
        if (this.getTags().isEmpty() && other.getTags().isEmpty()) {
            return result;
        }
        Set<String> intersection = new HashSet<>(this.getTags());
        intersection.retainAll(other.getTags());

        Set<String> union = new HashSet<>(this.getTags());
        union.addAll(other.getTags());

        result = 1 - ((double) intersection.size() / (double) union.size());
        log.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), other.toString(), result));

        return result;
    }
}
