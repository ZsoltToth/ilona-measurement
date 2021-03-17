package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class responsible for the GPS Coordinate of the measurement.
 *
 * @author Tamas13
 */
@ToString
@EqualsAndHashCode
public class GpsCoordinate {
    private static final Logger LOG = LogManager.getLogger(GpsCoordinate.class);

    private double latitude;

    private double longitude;

    private double altitude;

    public GpsCoordinate(final double latitude, final double longitude, final double altitude) {
        super();

        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public GpsCoordinate() {
        super();
    }

    public final double getLatitude() {
        return latitude;
    }

    public final void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public final double getLongitude() {
        return longitude;
    }

    public final void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public final double getAltitude() {
        return altitude;
    }

    public final void setAltitude(final double altitude) {
        this.altitude = altitude;
    }

    /**
     * This method calculate the distance of two GPSCoordinates. The coordinates in Spherical
     * Coordinate System. The used formula:
     *
     * <p><math xmlns="http://www.w3.org/1998/Math/MathML"> <msqrt><msup><mi>r</mi>
     * <mn>2</mn></msup><mo>+</mo><mi>r</mi><msup><mo>'</mo><mn>2</mn></msup>
     * <mo>-</mo><mn>2</mn><mi>r</mi><mi>r</mi><mo>'</mo><mo>(</mo><mi>sin</mi>
     * <mo>(</mo><mi>&#952;</mi><mo>)</mo><mo>&#160;</mo><mi>sin</mi>
     * <mo>(</mo><mi>&#952;</mi><mo>'</mo><mo>)</mo><mi>cos</mi><mo>
     * (</mo><mi>&#961;</mi><mo>-</mo><mi>&#961;</mi><mo>'</mo><mo>)
     * </mo><mo>&#160;</mo><mo>+</mo><mo>&#160;</mo><mi>cos</mi><mo>
     * (</mo><mi>&#952;</mi><mo>)</mo><mi>cos</mi><mo>(</mo><mi>&#952;</mi>
     * <mo>'</mo><mo>)</mo><mo>)</mo></msqrt></math> , where : r = altitude, <math
     * xmlns="http://www.w3.org/1998/Math/MathML"><mi>&#952;</mi></math> = longitude, <math
     * xmlns="http://www.w3.org/1998/Math/MathML"><mi>&#961;</mi></math> = latitude.
     *
     * @param otherGps represents the coordinate to which we want to compare the represented
     *                 coordinate
     * @return the distance of two coordinate as a double value.
     */
    public final double distance(final GpsCoordinate otherGps) {

        double result = 0;

        double phi1 = this.getLongitude();
        double theta1 = this.getLatitude();
        double r1 = this.getAltitude();

        double phi2 = otherGps.getLongitude();
        double theta2 = otherGps.getLatitude();
        double r2 = otherGps.getAltitude();

        result +=
                Math.pow(r1, 2.0)
                        + Math.pow(r2, 2.0)
                        - (2
                        * r1
                        * r2
                        * (Math.sin(theta1) * Math.sin(theta2) * Math.cos(phi1 - phi2)
                        + Math.cos(theta1) * Math.cos(theta2)));
        result = Math.sqrt(result);
        LOG.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), otherGps.toString(), result));
        return result;
    }
}
