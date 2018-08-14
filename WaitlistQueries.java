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


public class WaitlistQueries extends BookingEntry{
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler2DBDavidRobinsondpr5177";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement selectAllBookingEntriesByFlight;
    
    private PreparedStatement selectBookingByFlight;
    private PreparedStatement selectBookingByDate;
    
    private PreparedStatement insertNewBooking;
    
    //*********REMOVE BOOKING IS PROBLEM
    //private PreparedStatement removeBooking;
    
    private PreparedStatement checkForAllBookingEntriesByFlight;
    public WaitlistQueries(){
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllBookingEntriesByFlight = connection.prepareStatement("SELECT CUSTOMER FROM WAITLIST WHERE DAY = ? ");
            
            //potential problems
            selectBookingByFlight = connection.prepareStatement("SELECT FLIGHT FROM WAITLIST WHERE CUSTOMER = ?");
            selectBookingByDate = connection.prepareStatement("SELECT DAY FROM WAITLIST WHERE CUSTOMER = ?");
            
            //removeBooking = connection.prepareStatement("DELETE FROM WATILIST WHERE CUSTOMER = ?");
            
            //potential problem - Probably not
            checkForAllBookingEntriesByFlight = connection.prepareStatement("SELECT CUSTOMER FROM WAITLIST WHERE FLIGHT = ? AND DAY = ? ");
            
            //selectAllFlights = connection.prepareStatement("SELECT * FROM BOOKINGS");
            insertNewBooking = connection.prepareStatement("INSERT INTO WAITLIST" + "(CUSTOMER,FLIGHT,DAY,TIMESTAMP)" + "VALUES (?,?,?,?)");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public List < String > getAllBookingEntriesByFlight(Date day){
        List < String > results = null;
        ResultSet resultSet = null;
        try{
            
            selectAllBookingEntriesByFlight.setDate(1,day);
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
    public List < String > checkAllBookingEntriesByFlight(String flight, Date day){
        List < String > results = null;
        ResultSet resultSet = null;
        try{
            checkForAllBookingEntriesByFlight.setString(1,flight);
            checkForAllBookingEntriesByFlight.setDate(2,day);
            resultSet = checkForAllBookingEntriesByFlight.executeQuery();
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
//    public int cancelBooking(String name){
//        int result = 0;
//        try{
//            removeBooking.setString(1,name);
//            result = removeBooking.executeUpdate();
//        }
//        catch(SQLException sqlException){
//            sqlException.printStackTrace();
//            close();
//        }
//        return result;
//    }
    public void close(){
        try{
            connection.close();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}

