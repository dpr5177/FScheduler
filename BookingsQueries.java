/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidrobinson
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;


public class BookingsQueries extends BookingEntry{
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler2DBDavidRobinsondpr5177";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement selectAllBookingEntriesByFlight;
    private PreparedStatement selectBookingByFlight;
    private PreparedStatement selectBookingByDate;
    private PreparedStatement selectFlightChecker;
    private PreparedStatement insertNewBooking;
    private PreparedStatement removeBooking;
    private PreparedStatement selectBookingFlightByCustomer;
    private PreparedStatement selectBookingDayByCustomer;
    public BookingsQueries(){
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllBookingEntriesByFlight = connection.prepareStatement("SELECT CUSTOMER FROM BOOKINGS WHERE FLIGHT = ? AND DAY = ? ");
            selectBookingFlightByCustomer = connection.prepareStatement("SELECT FLIGHT FROM BOOKINGS WHERE CUSTOMER = ?");
            selectBookingDayByCustomer = connection.prepareStatement("SELECT DAY FROM BOOKINGS WHERE CUSTOMER = ?");

            selectBookingByFlight = connection.prepareStatement("SELECT FLIGHT FROM BOOKINGS WHERE CUSTOMER = ?");
            selectBookingByDate = connection.prepareStatement("SELECT DAY FROM BOOKINGS WHERE CUSTOMER = ?");
            selectFlightChecker = connection.prepareStatement("SELECT CUSTOMER FROM BOOKINGS WHERE FLIGHT = ? AND DAY = ? ");
            removeBooking = connection.prepareStatement("DELETE FROM BOOKINGS WHERE CUSTOMER = ?");
            //selectAllFlights = connection.prepareStatement("SELECT * FROM BOOKINGS");
            insertNewBooking = connection.prepareStatement("INSERT INTO BOOKINGS" + "(CUSTOMER,FLIGHT,DAY,TIMESTAMP)" + "VALUES (?,?,?,?)");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public List < String > getAllBookingEntriesByFlight(String flight, Date day){
        List < String > results = null;
        ResultSet resultSet = null;
        try{
            selectAllBookingEntriesByFlight.setString(1,flight);
            selectAllBookingEntriesByFlight.setDate(2,day);
            resultSet = selectAllBookingEntriesByFlight.executeQuery();
            results = new ArrayList < String >();
            while (resultSet.next()){
                results.add(new String(
                        resultSet.getString("CUSTOMER")));
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
    //Use next two to get the flight and day of the customer that is to be cancled
//    public List < String > getBookingFlightByCustomer(String name){
//        List < String > results = null;
//        ResultSet resultSet = null;
//        try{
//            selectBookingFlightByCustomer.setString(1,name);
//            resultSet = selectBookingFlightByCustomer.executeQuery();
//            results = new ArrayList < String >();
//            while (resultSet.next()){
//                results.add(new String(
//                        resultSet.getString("FLIGHT")));
//            }
//        }
//        catch ( SQLException sqlException){
//            sqlException.printStackTrace();
//        }
//        finally{
//            try{
//                resultSet.close();
//            }
//            catch (SQLException sqlException){
//                sqlException.printStackTrace();
//                close();
//            }
//        }
//        return results;
//    }
//    public List <Date> getBookingDayByCustomer(String name){
//        List <Date> results = null;
//        ResultSet resultSet = null;
//        try{
//            selectBookingDayByCustomer.setString(1,name);
//            resultSet = selectBookingDayByCustomer.executeQuery();
//            results = new ArrayList < Date >();
//            while (resultSet.next()){
//                results.add(resultSet.getDate("DAY"));
//            }
//        }
//        catch ( SQLException sqlException){
//            sqlException.printStackTrace();
//        }
//        finally{
//            try{
//                resultSet.close();
//            }
//            catch (SQLException sqlException){
//                sqlException.printStackTrace();
//                close();
//            }
//        }
//        return results;
//    }
    public List <String> getBookingFlight(String name){
        List <String> result = null;
        ResultSet resultSet = null;
        try{
            selectBookingByFlight.setString(1,name);
            resultSet = selectBookingByFlight.executeQuery();
            result = new ArrayList <String>();
            while (resultSet.next()){
                result.add(new String(
                        resultSet.getString("FLIGHT")));
            }
            //result.add(new String(resultSet.getString("FLIGHT")));
            //result = resultSet.getString("FLIGHT");
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
        return result;
    }
    public List <Date> getBookingDate(String name){
        List <Date> result = null;
        ResultSet resultSet = null;
        try{
            selectBookingByDate.setString(1,name);
            resultSet = selectBookingByDate.executeQuery();
            result = new ArrayList <Date>();
            while (resultSet.next()){
                result.add(
                        resultSet.getDate("DAY"));
            }
            //result.add(new String(resultSet.getString("FLIGHT")));
            //result = resultSet.getString("FLIGHT");
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
        return result;
    }
    //USE this for chekcing if a flight is full
    public List <String> flightChecker(String flight, Date day){
        List < String > results = null;
        ResultSet resultSet = null;
        try{
            selectFlightChecker.setString(1,flight);
            selectFlightChecker.setDate(2,day);
            resultSet = selectFlightChecker.executeQuery();
            results = new ArrayList < String >();
            while (resultSet.next()){
                results.add(new String(
                        resultSet.getString("CUSTOMER")));
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


    public int addBooking(String name, String flight, Date day, Timestamp ts){
        int result = 0;
        try{
            insertNewBooking.setString(1, name);
            insertNewBooking.setString(2, flight);
            insertNewBooking.setDate(3, day);
            insertNewBooking.setTimestamp(4, ts);
            result = insertNewBooking.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    public int cancelBooking(String name){
        int result = 0;
        try{
            removeBooking.setString(1,name);
            result = removeBooking.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
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
