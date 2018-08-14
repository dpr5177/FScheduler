
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
public class Customer {
    private String customer;
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler2DBDavidRobinsondpr5177";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement selectAllCustomers;
    private PreparedStatement insertNewCustomer;
    
    public Customer()
    {
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllCustomers = connection.prepareStatement("SELECT * FROM CUSTOMER");
            insertNewCustomer = connection.prepareStatement("INSERT INTO CUSTOMER" + "(NAME)" + "VALUES (?)");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public Customer(String Customer){
        setCustomer(customer);
        
    }
    public List < String> getAllCustomers(){
        List < String > results1 = null;
        ResultSet resultSet = null;
        try{
            resultSet = selectAllCustomers.executeQuery();
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
    public int addCustomer(String name){
        int result = 0;
        try{
            insertNewCustomer.setString(1,name);
            result = insertNewCustomer.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    public void setCustomer(String Customer){
        this.customer = Customer;
    }
    public String getCustomer(){
        return customer;
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
