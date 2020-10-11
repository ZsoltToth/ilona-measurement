package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Zone")
public class ZoneEntity {
    @Id
    @Column(name = "zoneId")
    private String id;
    private String name;
    @OneToMany(
            mappedBy = "zone",
            cascade = CascadeType.ALL
    )
    private Collection<PositionEntity> positions;
}
