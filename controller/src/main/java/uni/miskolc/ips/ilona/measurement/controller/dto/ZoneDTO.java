package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
public class ZoneDTO {
    private String id;
    private String name;

    public ZoneDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
