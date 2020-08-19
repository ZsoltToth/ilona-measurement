package uni.miskolc.ips.ilona.measurement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uni.miskolc.ips.ilona.measurement.persist.MeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLMeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLPositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;
import uni.miskolc.ips.ilona.measurement.service.MeasurementService;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.impl.MeasurementManagerServiceImpl;
import uni.miskolc.ips.ilona.measurement.service.impl.ZoneManagerServiceImpl;

import java.io.FileNotFoundException;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uni.miskolc.ips.ilona.measurement.controller")
@EnableSwagger2
@Slf4j
public class IlonaMeasurementApplicationContext implements WebMvcConfigurer {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *
     * @return A MySQLZoneDAO bean which is impelemtend in persist.mysql
     */
    @Bean(name = "zoneDao")
    public ZoneDAO getZoneDAO() throws FileNotFoundException {
        return new MySQLZoneDAO(
                System.getProperty("mybatis.config"),
                System.getProperty("database.host"),
                Integer.parseInt(System.getProperty("database.port")),
                System.getProperty("database.db"),
                System.getProperty("database.user"),
                System.getProperty("database.password"));
    }

    @Bean(name = "positionDAO")
    public PositionDAO getPositionDAO() throws FileNotFoundException {

        return new MySQLPositionDAO(System.getProperty("mybatis.config"),
                System.getProperty("database.host)"),
                Integer.parseInt(System.getProperty("database.port")),
                System.getProperty("database.db"),
                System.getProperty("database.user"),
                System.getProperty("database.password"),
                getZoneDAO());
    }

    @Bean(name = "measurementDAO")
    public MeasurementDAO getMeasurementDAO() throws FileNotFoundException {

        return new MySQLMeasurementDAO(System.getProperty("mybatis.config"),
                System.getProperty("database.host"),
                Integer.parseInt(System.getProperty("database.port")),
                System.getProperty("database.db"),
                System.getProperty("database.user"),
                System.getProperty("database.password"),
                getPositionDAO());
    }

    @Bean(name = "zoneManagerService")
    public ZoneService zoneManagerService() throws FileNotFoundException {

        return new ZoneManagerServiceImpl(getZoneDAO());
    }

    @Bean(name = "measurementManagerService")
    public MeasurementService measurementManagerService() throws FileNotFoundException {

        return new MeasurementManagerServiceImpl(getMeasurementDAO());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /*
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");

    }
     */
}
