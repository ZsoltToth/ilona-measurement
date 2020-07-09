package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
public class CoordinateDTO {
    private double x;
    private double y;
    private double z;

    public CoordinateDTO(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
