package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "WIFIRSSI")
public class WifiRSSIEntity {
    @EmbeddedId
    public WifiRSSIEntityId id;
    @MapsId("measId")
    @ManyToOne
    @JoinColumn(name = "measId", referencedColumnName = "measId")
    private MeasurementEntity measurement;
    private double rssi;
}
