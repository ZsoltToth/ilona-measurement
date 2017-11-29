package uni.miskolc.ips.ilona.measurement.util;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Log4j2Test {

	private static Logger LOG = LogManager.getFormatterLogger(Log4j2Test.class);

	@Test
	public void test() {
		LOG.info("info test");
		LOG.warn("info test");
		LOG.error("info test");
	}

}
