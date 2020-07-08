package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class PositionDTO {
    protected String id;
    protected ZoneDTO zone;
    protected CoordinateDTO coordinate;
}
