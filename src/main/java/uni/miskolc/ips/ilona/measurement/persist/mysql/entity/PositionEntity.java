package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Position")
public class PositionEntity {
    @Id
    @Column(name = "posId")
    private String id;
    private double coord_X;
    private double coord_Y;
    private double coord_Z;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zoneId", referencedColumnName = "zoneId")
    private ZoneEntity zone;
    @OneToMany(
            mappedBy = "position",
            cascade = CascadeType.REMOVE
    )
    private Collection<MeasurementEntity> measurements;
}
