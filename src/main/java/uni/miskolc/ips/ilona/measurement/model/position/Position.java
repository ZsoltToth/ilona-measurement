package uni.miskolc.ips.ilona.measurement.model.position;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Position {

    @EqualsAndHashCode.Exclude
    private Coordinate coordinate;

    @EqualsAndHashCode.Exclude
    private Zone zone;

    private UUID uuid;

    public Position() {
        super();
    }

    public Position(Coordinate coordinate, Zone zone) {
        super();
        this.coordinate = coordinate;
        this.zone = zone;
        this.uuid = UUID.randomUUID();
    }

    public Position(Zone zone) {
        super();
        this.zone = zone;
        this.uuid = UUID.randomUUID();
    }

    public Position(Coordinate coordinate) {
        super();
        this.coordinate = coordinate;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Position [coordinate=" + coordinate + ", zone=" + zone + ", id=" + uuid + "]";
    }
}
