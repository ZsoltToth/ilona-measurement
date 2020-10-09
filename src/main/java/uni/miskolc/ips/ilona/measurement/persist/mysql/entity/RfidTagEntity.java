package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RFIDTags")
public class RfidTagEntity {
    @EmbeddedId
    private RfidTagId id;
    @MapsId("measId")
    @ManyToOne
    @JoinColumn(name = "measId", referencedColumnName = "measId")
    private MeasurementEntity measurement;
}
