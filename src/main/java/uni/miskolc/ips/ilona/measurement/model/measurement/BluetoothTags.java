package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tamas13, comment made by kun3
 *     Represents the measurement of the bluetooth sensor and
 *     calculates the distance of two bluetooth sensor measurement
 */
public class BluetoothTags {

    /**
     * The logger class.
     */
    private static final Logger LOG = LogManager.getLogger(BluetoothTags.class);

    /**
     * The collection of the bluetooth tags.
     */
    private Set<String> tags;

    /**
     * Empty constructor for the bluetooth tags.
     */
    public BluetoothTags() {
        super();
    }

    /**
     * BluetoothTags constructor.
     *
     * @param tags as set
     */
    public BluetoothTags(final Set<String> tags) {
        super();
        this.tags = tags;
    }

    /**
     * Get method.
     *
     * @return set
     */
    public final Set<String> getTags() {
        return tags;
    }

    /**
     * Set method.
     *
     * @param tags as set
     */
    public final void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    /**
     * Method to add a new tag to the set.
     *
     * @param tag as String
     */
    public final void addTag(final String tag) {
        this.tags.add(tag);
    }

    /**
     * Method used to remove a tag.
     *
     * @param tag as string
     */
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
        LOG.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), other.toString(), result));

        return result;
    }

    @Override
    public final String toString() {
        return "BluetoothTags [tags=" + tags + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BluetoothTags that = (BluetoothTags) o;
        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }
}
