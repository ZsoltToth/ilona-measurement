package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
