package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RFIDTags")
public class RfidTagEntity {
    @EmbeddedId
    private RfidTagId id;

    @MapsId("measId")
    @ManyToOne
    @JoinColumn(name = "measId", referencedColumnName = "measId")
    private MeasurementEntity measurement;
}
