import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author davidrobinson
 */
public class Day {
    private Date day;
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler2DBDavidRobinsondpr5177";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement selectAllDays;
    private PreparedStatement insertNewDay;
    public Day()
    {
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllDays = connection.prepareStatement("SELECT * FROM DAY");
            insertNewDay = connection.prepareStatement("INSERT INTO DAY" + "(DATE)" + "VALUES (?)");

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public Day(Date Day){
        setDay(day);
        
    }
    public List < Date> getAllDays(){
        List < Date > results1 = null;
        ResultSet resultSet = null;
        try{
            resultSet = selectAllDays.executeQuery();
            results1 = new ArrayList < Date >();
            while (resultSet.next()){
                //
                results1.add(
                        resultSet.getDate("DATE"));
            }
        }
        catch ( SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally{
            try{
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results1;
    }
    public int addDay(java.sql.Date name){
        int result = 0;
        try{
            insertNewDay.setDate(1,name);
            result = insertNewDay.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    public void setDay(Date Day){
        this.day = Day;
    }
    public Date getDay(){
        return day;
    }
    public void close(){
        try{
            connection.close();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}

