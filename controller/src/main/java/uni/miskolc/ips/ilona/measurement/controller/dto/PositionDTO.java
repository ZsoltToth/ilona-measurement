package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
public class PositionDTO {
    private String id;
    private ZoneDTO zone;
    private CoordinateDTO coordinate;

    public PositionDTO(String id, ZoneDTO zone, CoordinateDTO coordinate) {
        this.id = id;
        this.zone = zone;
        this.coordinate = coordinate;
    }
}
