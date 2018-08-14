import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author davidrobinson
 */
public class Flight {
    private String flight;
    private int seats;
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler2DBDavidRobinsondpr5177";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement selectAllFlights;
    private PreparedStatement checkAllFlights;
    private PreparedStatement insertNewSeats;
    private PreparedStatement removeFlight;
    public Flight()
    {
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllFlights = connection.prepareStatement("SELECT * FROM FLIGHT");
            checkAllFlights = connection.prepareStatement("SELECT SEATS FROM FLIGHT WHERE NAME = ?");
            removeFlight = connection.prepareStatement("DELETE FROM FLIGHT WHERE NAME = ?");
            //insertNewSeats = connection.prepareStatement("INSERT INTO FLIGHT" + "(SEATS)" + "VALUES (?)" + "WHERE NAME (?)");
            //insertNewSeats = connection.prepareStatement("INSERT ? INTO SEATS FROM FLIGHT WHERE NAME = ?" + "(SEATS)" + "VALUES (?)" + "WHERE NAME = (?)");
            //insertNewSeats = connection.prepareStatement("UPDATE FLIGHT SET SEATS = ? WHERE NAME = ?");
            insertNewSeats = connection.prepareStatement("INSERT INTO FLIGHT" + "(NAME,SEATS)" + "VALUES (?,?)");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public Flight(String Flight,int Seats){
        setFlight(flight);
        setSeats(seats);
    }
    public List < String> getAllFlights(){
        List < String > results1 = null;
        ResultSet resultSet = null;
        try{
            resultSet = selectAllFlights.executeQuery();
            results1 = new ArrayList < String >();
            while (resultSet.next()){
                //
                results1.add(new String(
                        resultSet.getString("NAME")));
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
    public List <Integer> FlightSeats(String name){
        List <Integer> results = null;
        ResultSet resultSet = null;
        try{
            checkAllFlights.setString(1,name);
            resultSet = checkAllFlights.executeQuery();
            results = new ArrayList < Integer >();
            while (resultSet.next()){
                //
                results.add(new Integer(
                        resultSet.getInt("SEATS")));
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
        return results;
    }
    public int addFlightSeat(String name, int num){
        int result = 0;
        try{
            insertNewSeats.setString(1,name);
            insertNewSeats.setInt(2, num);
            result = insertNewSeats.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    public int cancelFlight(String name){
        int result = 0;
        try{
            removeFlight.setString(1,name);
            result = removeFlight.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    public void setFlight(String Flight){
        this.flight = Flight;
    }
    public String getFlight(){
        return flight;
    }
    public void setSeats(int Seats){
        this.seats = Seats;
    }
    public int getSeats(){
        return seats;
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
