package uni.miskolc.ips.ilona.measurement.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeasurementApp {

    /**
     *
     * -Ddatabase.host=127.0.0.1
     * -Ddatabase.port=3306
     * -Ddatabase.db=Ilona
     * -Ddatabase.user=ilona
     * -Ddatabase.password=secret
     * -Dmybatis.config=<path-to-persist-mysql>/persist.mysql/src/main/resources/mybatis-configuration.xml
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(MeasurementApp.class,args);
    }
}
