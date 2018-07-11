package uni.miskolc.ips.ilona.measurement.web;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class IlonaMeasurementWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("------------------------------------------");

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(IlonaMeasurementApplicationContext.class);
        context.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }


}
