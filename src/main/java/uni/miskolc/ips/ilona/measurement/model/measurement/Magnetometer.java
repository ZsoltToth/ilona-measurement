package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * It represents the measured values of a Magnetometer. The magnetometer returns values in Cartesian
 * coordinate system.
 *
 * @author zsolt
 */
public class Magnetometer {

    /**
     * The constant default value of the unknown distance.
     */
    public static final double UNKNOW_DISTANCE = -1.0;

    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(Magnetometer.class);

    /**
     * The attribute representing the X axis of the magnetometer.
     */
    private double axisX;

    /**
     * The attribute representing the Y axis of the magnetometer.
     */
    private double axisY;

    /**
     * The attribute representing the Z axis of the magnetometer.
     */
    private double axisZ;

    /**
     * The attribute representing the radian of the magnetometer.
     */
    private double radian;

    /**
     * The constructor of the magnetometer class.
     *
     * @param axisX  as double
     * @param axisY  as double
     * @param axisZ  as double
     * @param radian as double
     */
    public Magnetometer(
            final double axisX, final double axisY, final double axisZ, final double radian) {
        super();
        this.axisX = axisX;
        this.axisY = axisY;
        this.axisZ = axisZ;
        this.radian = radian;
    }

    /**
     * The empty constructor.
     */
    public Magnetometer() {
        super();
    }

    /**
     * The getter method of the X axis.
     *
     * @return the X axis as double
     */
    public final double getAxisX() {
        return axisX;
    }

    /**
     * The setter method of the X axis.
     *
     * @param axisX as double.
     */
    public final void setAxisX(final double axisX) {
        this.axisX = axisX;
    }

    /**
     * The getter method of the Y axis.
     *
     * @return the Y axis as double
     */
    public final double getAxisY() {
        return axisY;
    }

    /**
     * The setter method of the Z axis.
     *
     * @param axisY as double.
     */
    public final void setAxisY(final double axisY) {
        this.axisY = axisY;
    }

    /**
     * The getter method of the Z axis.
     *
     * @return the Z axis as double
     */
    public final double getAxisZ() {
        return axisZ;
    }

    /**
     * The setter method of the Z axis.
     *
     * @param axisZ as double.
     */
    public final void setAxisZ(final double axisZ) {
        this.axisZ = axisZ;
    }

    /**
     * The getter method of the radian.
     *
     * @return the radian as double
     */
    public final double getRadian() {
        return radian;
    }

    /**
     * The setter method of the radian.
     *
     * @param radian as double.
     */
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
            result = UNKNOW_DISTANCE;
            return result;
        }
        RealVector x1 = this.getRotatedCoordinates();
        RealVector x2 = other.getRotatedCoordinates();
        cos = cosine(x1, x2);
        cos = Math.acos(cos);
        result = cos / Math.PI;
        LOG.info(
                String.format(
                        "Distance between %s and %s is %f", this.toString(), other.toString(), result));
        return result;
    }

    /**
     * Calculates the rotated coordinates from the original ones.
     *
     * @return the rotated vector
     */
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

    @Override
    public final String toString() {
        return "Magnetometer [xAxis="
                + axisX
                + ", yAxis="
                + axisY
                + ", zAxis="
                + axisZ
                + ", radian="
                + radian
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Magnetometer that = (Magnetometer) o;

        if (Double.compare(that.axisX, axisX) != 0) return false;
        if (Double.compare(that.axisY, axisY) != 0) return false;
        if (Double.compare(that.axisZ, axisZ) != 0) return false;
        return Double.compare(that.radian, radian) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(axisX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(axisY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(axisZ);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(radian);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
