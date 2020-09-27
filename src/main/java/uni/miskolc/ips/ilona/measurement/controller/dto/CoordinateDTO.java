package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDTO {
    private double x;
    private double y;
    private double z;
}
