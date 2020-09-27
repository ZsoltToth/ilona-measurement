package uni.miskolc.ips.ilona.measurement.model.position;

import java.util.UUID;

public class Position {

	private Coordinate coordinate;
	private Zone zone;
	private UUID id;

	public Position() {
		super();
	}

	public Position(Coordinate coordinate, Zone zone) {
		super();
		this.coordinate = coordinate;
		this.zone = zone;
		this.id = UUID.randomUUID();
	}

	public Position(Zone zone) {
		super();
		this.zone = zone;
		this.id = UUID.randomUUID();
	}

	public Position(Coordinate coordinate) {
		super();
		this.coordinate = coordinate;
		this.id = UUID.randomUUID();
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public UUID getUUID() {
		return this.id;
	}

	public void setUUID(UUID id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position == false) {
			return false;
		}
		/*
		 * return this.coordinate.equals(((Position) obj).coordinate) &&
		 * this.zone.equals(((Position) obj).zone);
		 */
		return this.id.equals(((Position)obj).id);
	}

	@Override
	public String toString() {
		return "Position [coordinate=" + coordinate + ", zone=" + zone
				+ ", id=" + id + "]";
	}

	
	
}
