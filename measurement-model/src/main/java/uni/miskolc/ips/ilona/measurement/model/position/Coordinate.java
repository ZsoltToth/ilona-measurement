package uni.miskolc.ips.ilona.measurement.model.position;

public class Coordinate {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Public default constructor for Jackson parser
	 */
	public Coordinate(){
		
	}
	
	public Coordinate(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public double distance(Coordinate other){
		double result = 0.0;
		result += Math.pow(this.x- other.x,2);
		result += Math.pow(this.y- other.y,2);
		result += Math.pow(this.z- other.z,2);
		result =  Math.sqrt(result);
		return result;
	}
}
