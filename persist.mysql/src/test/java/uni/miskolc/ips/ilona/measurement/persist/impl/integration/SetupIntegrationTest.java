package uni.miskolc.ips.ilona.measurement.persist.impl.integration;

import com.mysql.jdbc.Driver;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetupIntegrationTest {

    public static final String MYBATIS_CONFIG_FILE = "src/main/resources/mybatis-configuration.xml";
    public static final String TEST_CREATE_TABLES = "src/main/resources/createTables.sql";
    public static final String TEST_DROP_TABLES = "src/main/resources/dropTables.sql";
    public static final String TEST_SETUP_SQL = "src/main/resources/setupTestDB.sql";
    public static final String TEST_TEAR_DOWN_SQL = "src/main/resources/teardownTestDB.sql";

    protected static String HOST;
    protected static int PORT;
    protected static String DATABASE;
    protected static String USER;
    protected static String PASSWORD;

    /**
     * It checks the existence of the required script files and properties. The
     * tests are ignored, if anything is missing.
     * <p>
     * It also set up the database and create tables.
     */
    @BeforeClass
    public static void beforeClass() {
        /* Check SQL Scripts */
        File mybatisConfig = new File(MYBATIS_CONFIG_FILE);
        File createTablesSQL = new File(TEST_CREATE_TABLES);
        File dropTablesSQL = new File(TEST_DROP_TABLES);
        File setupTestSQL = new File(TEST_SETUP_SQL);
        File teardownSQL = new File(TEST_TEAR_DOWN_SQL);

        Assume.assumeTrue(mybatisConfig.exists());
        Assume.assumeTrue(createTablesSQL.exists());
        Assume.assumeTrue(dropTablesSQL.exists());
        Assume.assumeTrue(setupTestSQL.exists());
        Assume.assumeTrue(teardownSQL.exists());

        /* Check System Properties */
        String host = System.getProperty("database.host");

        int port = -1;
        try {
            port = Integer.parseInt(System.getProperty("database.port"));
        } catch (NumberFormatException ex) {
            port = -1;
            Assume.assumeNoException(ex);
        }
        String database = System.getProperty("database.db");
        String user = System.getProperty("database.user");
        String password = System.getProperty("database.password");
        Assume.assumeNotNull(host, port, database, user, password);
        HOST = host;
        PORT = port;
        DATABASE = database;
        USER = user;
        PASSWORD = password;

        try {
            runSQLScript(TEST_CREATE_TABLES);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }

    }

    /**
     * It drops the created tables.
     */
    @AfterClass
    public static void afterClass() {
        try {
            runSQLScript(TEST_DROP_TABLES);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }

    }

    private static void runSQLScript(String scriptFile)
            throws ClassNotFoundException, SQLException, FileNotFoundException,
            IOException {

        Class.forName(Driver.class.getName());
        final String connectionURL = String.format("jdbc:mysql://%s:%d/%s",
                HOST, PORT, DATABASE);
        Connection connection = DriverManager.getConnection(connectionURL,
                USER, PASSWORD);
        ScriptRunner runner = new ScriptRunner(connection);
        Reader reader = new FileReader(scriptFile);
        runner.runScript(reader);
        connection.close();
        reader.close();
    }

    /**
     * It instantiate the DAO object and insert the test data into the test
     * database.
     */
    @Before
    public void setUp() {
        /* INSERT TEST RECORDS */
        try {
            runSQLScript(TEST_SETUP_SQL);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    /**
     * It deletes the records from the test database.
     */
    @After
    public void tearDown() {
        try {
            runSQLScript(TEST_TEAR_DOWN_SQL);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }
}
