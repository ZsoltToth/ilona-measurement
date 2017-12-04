package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * It represents the measured values of a Magnetometer. The magnetometer returns
 * values in Cartesian coordinate system.
 * 
 * @author zsolt
 *
 */
public class Magnetometer {

	/**
	 * The constant default value of the unknown distance.
	 */
	public static final double UNKNOW_DISTANCE = -1.0;

	/**
	 * The attribute representing the X axis of the magnetometer.
	 */
	private double xAxis;
	/**
	 * The attribute representing the Y axis of the magnetometer.
	 */
	private double yAxis;
	/**
	 * The attribute representing the Z axis of the magnetometer.
	 */
	private double zAxis;
	/**
	 * The attribute representing the radian of the magnetometer.
	 */
	private double radian;

	/**
	 * The logger.
	 */
	private static final Logger LOG = LogManager.getLogger(Magnetometer.class);

	/**
	 * The constructor of the magnetometer class.
	 * @param xAxis as double
	 * @param yAxis as double
	 * @param zAxis as double
	 * @param radian as double
	 */
	public Magnetometer(final double xAxis, final double yAxis, final double zAxis, final double radian) {
		super();
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.zAxis = zAxis;
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
	 * @return the X axis as double
	 */
	public final double getxAxis() {
		return xAxis;
	}

	/**
	 * The setter method of the X axis.
	 * @param xAxis as double.
	 */
	public final void setxAxis(final double xAxis) {
		this.xAxis = xAxis;
	}

	/**
	 * The getter method of the Y axis.
	 * @return the Y axis as double
	 */
	public final double getyAxis() {
		return yAxis;
	}

	/**
	 * The setter method of the Z axis.
	 * @param yAxis as double.
	 */
	public final void setyAxis(final double yAxis) {
		this.yAxis = yAxis;
	}

	/**
	 * The getter method of the Z axis.
	 * @return the Z axis as double
	 */
	public final double getzAxis() {
		return zAxis;
	}

	/**
	 * The setter method of the Z axis.
	 * @param zAxis as double.
	 */
	public final void setzAxis(final double zAxis) {
		this.zAxis = zAxis;
	}

	/**
	 * The getter method of the radian.
	 * @return the radian as double
	 */
	public final double getRadian() {
		return radian;
	}

	/**
	 * The setter method of the radian.
	 * @param radian as double.
	 */
	public final void setRadian(final double radian) {
		this.radian = radian;
	}

	/**
	 * This function calculates the distance between two magnetometer instances.
	 * @param other The other magnetometer
	 * @return the distance as double
	 */
	public final double distance(final Magnetometer other) {
		double result = 0;
		double cos = 0;
		if (this.isNull() || other.isNull()) {
			result = UNKNOW_DISTANCE;
			return result;
		}
		RealVector x1 = this.getRotatedCoordinates();
		RealVector x2 = other.getRotatedCoordinates();
		cos = cosine(x1, x2);
		cos = Math.acos(cos);
		result = cos / Math.PI;
		LOG.info(String.format("Distance between %s and %s is %f",
				this.toString(), other.toString(), result));
		return result;
	}

	/**
	 * Calculates the rotated coordinates from the original ones.
	 * @return the rotated vector
	 */
	private RealVector getRotatedCoordinates() {
		RealVector x = new ArrayRealVector(new double[] { this.getxAxis(),
				this.getyAxis(), this.getzAxis() });
		double radian = this.getRadian();
		double[][] d = { { Math.cos(-radian), Math.sin(-radian), 0 },
				{ -Math.sin(-radian), Math.cos(-radian), 0 }, { 0, 0, 1 } };
		RealMatrix mat = new Array2DRowRealMatrix(d);
		x = mat.preMultiply(x);
		return x.unitVector();
	}

	/**
	 * Check if the coordinate exists.
	 * @return 0.0 if it does not
	 */
	private boolean isNull() {
		RealVector vector = new ArrayRealVector(new Double[] { xAxis, yAxis,
				zAxis });
		return vector.getL1Norm() == 0.0;
	}

	/**
	 * Gets the cosine value of the distance between two vectors.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return the result as double
	 */
	private double cosine(final RealVector v1, final RealVector v2) {
		double result = 0.0;
		double v1norm = v1.getNorm();
		double v2norm = v2.getNorm();
		result = v1.dotProduct(v2) / (v1norm * v2norm);
		result = result < -1.0 ? -1.0 : result;
		result = result > 1.0 ? 1.0 : result;
		return result;

	}

	@Override
	public final String toString() {
		return "Magnetometer [xAxis=" + xAxis + ", yAxis=" + yAxis + ", zAxis="
				+ zAxis + ", radian=" + radian + "]";
	}

}
