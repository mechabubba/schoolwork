// Displaying content of username
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
public class loginServer {
    final String DATABASE_URL = "jdbc:sqlite:/Users/skhalil/Downloads/fourword/WordGame/WordGameServer/database";

    public static String username;
    public static String password;
    public loginServer(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
