package uni.miskolc.ips.ilona.measurement.persist.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BluetoothTagId implements Serializable {
    private String btDeviceId;

    private String measId;
}
