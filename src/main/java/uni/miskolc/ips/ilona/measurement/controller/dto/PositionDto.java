package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {
    private String id;

    private ZoneDto zone;

    private CoordinateDto coordinate;
}
