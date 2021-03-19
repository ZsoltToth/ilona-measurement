package uni.miskolc.ips.ilona.measurement.model.position;

public class Coordinate {

    private double coordinateX;

    private double coordinateY;

    private double coordinateZ;

    /**
     * Public default constructor for Jackson parser
     */
    public Coordinate() {
    }

    public Coordinate(double x, double y, double z) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.coordinateZ = z;
    }

    public double getX() {
        return coordinateX;
    }

    public void setX(double x) {
        this.coordinateX = x;
    }

    public double getY() {
        return coordinateY;
    }

    public void setY(double y) {
        this.coordinateY = y;
    }

    public double getZ() {
        return coordinateZ;
    }

    public void setZ(double z) {
        this.coordinateZ = z;
    }

    @Override
    public String toString() {
        return "Coordinate [x=" + coordinateX + ", y=" + coordinateY + ", z=" + coordinateZ + "]";
    }

    public double distance(Coordinate other) {
        double result = 0.0;
        result += Math.pow(this.coordinateX - other.coordinateX, 2);
        result += Math.pow(this.coordinateY - other.coordinateY, 2);
        result += Math.pow(this.coordinateZ - other.coordinateZ, 2);
        result = Math.sqrt(result);
        return result;
    }
}
