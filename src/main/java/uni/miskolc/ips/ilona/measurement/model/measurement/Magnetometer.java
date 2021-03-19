package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * It represents the measured values of a Magnetometer. The magnetometer returns values in Cartesian
 * coordinate system.
 *
 * @author zsolt
 */
@Slf4j
@ToString
@EqualsAndHashCode
public class Magnetometer {

    /**
     * The constant default value of the unknown distance.
     */
    public static final double UNKNOWN_DISTANCE = -1.0;

    private double axisX;

    private double axisY;

    private double axisZ;

    private double radian;

    public Magnetometer(
            final double axisX, final double axisY, final double axisZ, final double radian) {
        super();
        this.axisX = axisX;
        this.axisY = axisY;
        this.axisZ = axisZ;
        this.radian = radian;
    }

    public Magnetometer() {
        super();
    }

    public final double getAxisX() {
        return axisX;
    }

    public final void setAxisX(final double axisX) {
        this.axisX = axisX;
    }

    public final double getAxisY() {
        return axisY;
    }

    public final void setAxisY(final double axisY) {
        this.axisY = axisY;
    }

    public final double getAxisZ() {
        return axisZ;
    }

    public final void setAxisZ(final double axisZ) {
        this.axisZ = axisZ;
    }

    public final double getRadian() {
        return radian;
    }

    public final void setRadian(final double radian) {
        this.radian = radian;
    }

    /**
     * This function calculates the distance between two magnetometer instances.
     *
     * @param other The other magnetometer
     * @return the distance as double
     */
    public final double distance(final Magnetometer other) {
        double result;
        double cos;
        if (this.isNull() || other.isNull()) {
            result = UNKNOWN_DISTANCE;
            return result;
        }
        RealVector x1 = this.getRotatedCoordinates();
        RealVector x2 = other.getRotatedCoordinates();
        cos = cosine(x1, x2);
        cos = Math.acos(cos);
        result = cos / Math.PI;
        log.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), other.toString(), result));
        return result;
    }

    private RealVector getRotatedCoordinates() {
        RealVector x =
                new ArrayRealVector(new double[]{this.getAxisX(), this.getAxisY(), this.getAxisZ()});
        double radian = this.getRadian();
        double[][] d = {
                {Math.cos(-radian), Math.sin(-radian), 0},
                {-Math.sin(-radian), Math.cos(-radian), 0},
                {0, 0, 1}
        };
        RealMatrix mat = new Array2DRowRealMatrix(d);
        x = mat.preMultiply(x);
        return x.unitVector();
    }

    /**
     * Check if the coordinate exists.
     *
     * @return 0.0 if it does not
     */
    private boolean isNull() {
        RealVector vector = new ArrayRealVector(new Double[]{axisX, axisY, axisZ});
        return vector.getL1Norm() == 0.0;
    }

    /**
     * Gets the cosine value of the distance between two vectors.
     *
     * @param v1 vector 1
     * @param v2 vector 2
     * @return the result as double
     */
    private double cosine(final RealVector v1, final RealVector v2) {
        double result;
        double v1norm = v1.getNorm();
        double v2norm = v2.getNorm();
        result = v1.dotProduct(v2) / (v1norm * v2norm);
        result = Math.max(result, -1.0);
        result = Math.min(result, 1.0);
        return result;
    }
}
