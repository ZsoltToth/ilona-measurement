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
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Measurement")
public class MeasurementEntity {
    @Id
    @Column(name = "measId")
    private String id;
    private Date measTimestamp;
    private Double magnetometerX;
    private Double magnetometerY;
    private Double magnetometerZ;
    private Double magnetometerRadian;
    private Double gpsLatitude;
    private Double gpsLongitude;
    private Double gpsAltitude;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posId", referencedColumnName = "posId")
    private PositionEntity position;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurement", orphanRemoval = true)
    private Collection<BluetoothTagEntity> bluetoothTags;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurement", orphanRemoval = true)
    private Collection<WifiRSSIEntity> wifiRSSI;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurement", orphanRemoval = true)
    private Collection<RfidTagEntity> rfidTags;
}
