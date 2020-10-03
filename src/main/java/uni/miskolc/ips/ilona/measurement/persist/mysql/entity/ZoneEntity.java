package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Zone")
public class ZoneEntity {
    @Id
    @Column(name = "zoneId")
    private String id;
    private String name;
}
