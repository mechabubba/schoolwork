import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class database {
    private static Connection connection;
    private static Statement statement;
    public static void startUser(String user, String pass) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        try{
            final String url = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/swd_db04";
            System.out.println("");
            connection = DriverManager.getConnection( url, "swd_student04", "engr-2022-04");
            statement = connection.createStatement();
            String userName = null;
            String passWord = null;
            String show = "INSERT INTO USERDATA (user, pass) VALUES('"+userName+"','"+passWord+"')";
            statement.execute(show);
        }
        catch(Exception show){
            show.printStackTrace();
        }
    }
    public static ArrayList<String[]> firstGameLB(String game1)throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        try {
            final String url = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/swd_db04";
            System.out.println("");
            connection = DriverManager.getConnection(url, "swd_student04", "engr-2022-04");
            statement = connection.createStatement();
            ResultSet game1Result = statement.executeQuery("Select MAX(game1), username FROM userData");
            ResultSetMetaData metaData = game1Result.getMetaData();
            ArrayList<String[]> game1Results = new ArrayList<>();
            for (int i = 0; game1Result.next(); i++) {
                String[] example = new String[6];
                int j = 0;
                while (j < 6) {
                    example[j] = game1Result.getObject(j + 1).toString();
                    j++;
                }
                return game1Results;
            }
        } catch (Exception g1) {
            g1.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String[]> secondGameLB(String game2)throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        try {
            final String url = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/swd_db04";
            System.out.println("");
            connection = DriverManager.getConnection(url, "swd_student04", "engr-2022-04");
            statement = connection.createStatement();
            ResultSet game2Result = statement.executeQuery("Select MAX(game1), username FROM userData");
            ResultSetMetaData metaData = game2Result.getMetaData();
            ArrayList<String[]> game2Results = new ArrayList<>();
            for (int i = 0; game2Result.next(); i++) {
                String[] example = new String[6];
                int j = 0;
                while (j < 6) {
                    example[j] = game2Result.getObject(j + 1).toString();
                    j++;
                }
                return game2Results;
            }
        } catch (Exception g2) {
            g2.printStackTrace();
        }
        return null;
    }

}