package uni.miskolc.ips.ilona.measurement.persist.mysql;

import com.mysql.jdbc.Driver;
import org.h2.tools.RunScript;
import org.junit.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetupIntegrationTest {
    public static final String TEST_SETUP_SQL = "src/main/resources/sql/setupTestDB.sql";
    public static final String TEST_TEAR_DOWN_SQL = "src/main/resources/sql/teardownTestDB.sql";
    public static final String TEST_CREATE_TABLES = "src/main/resources/sql/createTables.sql";
    public static final String TEST_DROP_TABLES = "src/main/resources/sql/dropTables.sql";

    protected static String HOST;
    protected static int PORT;
    protected static String DATABASE;
    protected static String USER;
    protected static String PASSWORD;

    @BeforeClass
    public static void beforeClass() {
        File createTablesSQL = new File(TEST_CREATE_TABLES);
        File dropTablesSQL = new File(TEST_DROP_TABLES);
        File setupTestSQL = new File(TEST_SETUP_SQL);
        File teardownSQL = new File(TEST_TEAR_DOWN_SQL);

        Assume.assumeTrue(createTablesSQL.exists());
        Assume.assumeTrue(dropTablesSQL.exists());
        Assume.assumeTrue(setupTestSQL.exists());
        Assume.assumeTrue(teardownSQL.exists());

        String host = System.getProperty("DB_HOST");

        int port;
        try {
            port = Integer.parseInt(System.getProperty("DB_PORT"));
        } catch (NumberFormatException ex) {
            port = -1;
            Assume.assumeNoException(ex);
        }

        String database = System.getProperty("DB_NAME");
        String user = System.getProperty("DB_USER");
        String password = System.getProperty("DB_PASSWORD");
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

    @AfterClass
    public static void afterClass() {
        try {
            runSQLScript(TEST_DROP_TABLES);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    @Before
    public void setUp() {
        try {
            runSQLScript(TEST_SETUP_SQL);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    @After
    public void tearDown() {
        try {
            runSQLScript(TEST_TEAR_DOWN_SQL);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    private static void runSQLScript(String scriptFile) throws IOException, SQLException, ClassNotFoundException {
        Class.forName(Driver.class.getName());
        final String connectionURL = String.format("jdbc:mysql://%s:%d/%s",
                HOST, PORT, DATABASE);
        Connection connection = DriverManager.getConnection(connectionURL,
                USER, PASSWORD);
        RunScript.execute(connection, new FileReader(scriptFile));
        connection.close();
    }
}
