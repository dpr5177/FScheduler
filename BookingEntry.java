/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author davidrobinson
 */
public class BookingEntry {
    private String Customer;
    private String Flight;
    private Date Day;
    private Timestamp ts; 
    public BookingEntry()
    {
    }
    
    public BookingEntry(String Customer, String Flight,Date Day, Timestamp ts){
        setCustomer(Customer);
        setFlight(Flight);
        setDate(Day);
        setTS(ts);
    }
    
    public void setCustomer(String Customer){
        this.Customer = Customer;
    }
    public String getCustomer(){
        return Customer;
    }

    public void setFlight(String Flight){
        this.Flight = Flight;
    }
    public String getFlight(){
        return Flight;
    }
    public void setDate(Date Day){
        this.Day = Day;
    }
    public Date getDate(){
        return Day;
    }
    public void setTS(Timestamp ts){
        this.ts = ts;
    }
    public Timestamp getTS(){
        return ts;
    }
}


