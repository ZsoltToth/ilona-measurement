package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private String id;
    private ZoneDTO zone;
    private CoordinateDTO coordinate;
}
