package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
