package uni.miskolc.ips.ilona.measurement.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Global exception handler for controllers.
 *
 * If a database is not available the services should return with Insufficient storege status code (507).
 *
 * If there is a conversion problem during the marshalling of JSON messages then controllers return with unsupported media type satus code (415).
 *
 * @author tothzs
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(MeasurementController.class);

    @ResponseStatus(value= HttpStatus.INSUFFICIENT_STORAGE, reason = "Database is Unavailable")
    @ExceptionHandler(DatabaseUnavailableException.class)
    public void databaseUnavailableExceptionHandler(Exception ex){LOG.fatal(ex.getMessage());	}

    @ResponseStatus(value= HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason = "Data conversion was unsuccessful.")
    @ExceptionHandler(DatatypeConfigurationException.class)
    public void datatypeConfigurationExceptionHandler(Exception ex){ LOG.error(ex.getMessage());	}
}


