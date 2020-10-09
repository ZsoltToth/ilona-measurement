package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BluetoothTags")
public class BluetoothTagEntity {
    @EmbeddedId
    private BluetoothTagId id;
    @MapsId("measId")
    @ManyToOne
    @JoinColumn(name = "measId", referencedColumnName = "measId")
    private MeasurementEntity measurement;
}
