import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseConnection {
    /**
     * Class contains methods for connecting to the database
     * Class contains method for executing database queries*/

    // creating connection object
    Connection connection;

    // creating statement object
    Statement statement;

    // creating result object
    ResultSet result;

    // function to create password string
    public String concatenatePassword(char[] arrayChar)
    {
        /**
         * Function concatenates password from password characters*/

        String password = "";

        for(int x = 0; x < arrayChar.length; x++)
        {
            password+= arrayChar[x];
        }
        return password.trim();
    }

    public boolean databaseConnection()
    {
        /**
         * Function connects to database
         * Return true after successfully connecting to a database*/
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root", "password");

        }
        catch(Exception exception)
        {
            System.out.println("Error while connecting to database");
        }

        finally {
         return true;
        }

    }

    // function to log in user
    public boolean databaseQuery(String from_username, String from_password)
    {
        /**
         * Function returns true after successfully executing a query*/

        //user found
        boolean found = false;
        try
        {
            // prepare statement
            this.statement = this.connection.createStatement();

            String query = "SELECT username, password FROM customers";

            // execute query
            this.result = this.statement.executeQuery(query);

            while(this.result.next())
            {
                // get password and username
                String username = this.result.getString("username").trim();
                String password = this.result.getString("password").trim();

                // display username and password
                System.out.println("Database username is : " + username);
                System.out.println("Database password is : " + password);

                if(username.equals(from_username) && password.equals(from_password))
                {
                    found = true;

                }
                else{System.out.println("Not equal to the string values supplied: ");}
            }
            System.out.println("The value of found is : " + found);
        }

        catch (Exception error)
        {
            System.out.println("Failed to execute query statement");
        }

        if(found == false)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    // function to check reservation
    public boolean confirmReservation(String table, String start_date, String end_date)
    {
        /**
         * Function searches through database
         * Determines if dates given for start and end falls between dates of any reservation
         * Return false if dates not reserved
         * Returns true if dates are reserved*/

        boolean datesExist = false;
        try
        {
            // prepare statement
            this.statement = this.connection.createStatement();

            // sql query
            String query = String.format("SELECT startDate, endDate FROM familyroom WHERE startDate <= '%s' OR endDate >= '%s'"
                    ,start_date, start_date, end_date, end_date);

            // execute query
            this.result = this.statement.executeQuery(query);

            // check if reservation dates exist
            if(this.result != null)
            {
                // change date exist to true
                datesExist = true;

            }
        }
        catch(Exception error)
        {
            // print any error messages
            System.out.println("Failed to execute query...." + error.getMessage());
        }

        if(datesExist == true)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    // function to calculate reservation price
    public int calculateDuration(String start_date, String end_date, int price)
    {
        /**
         * Function calculates the duration by endDate - startDate
         * Returns the amount paid for that duration*/

        // create simple date format object
        SimpleDateFormat d1 = new SimpleDateFormat("YY-MM-dd");
        SimpleDateFormat d2 = new SimpleDateFormat("YY-MM-dd");

        int amount = 0;
        try
        {

            // convert to normal date objects
            Date day1 = d1.parse(start_date);
            Date day2 = d2.parse(end_date);

            // get time difference in ms
            long ms = day2.getTime() - day1.getTime();

            // convert difference to days
            long duration = (ms/(1000*60*60*24)) % 365;

            // convert long to integer and calculate amount
            amount =  (int) duration * price;

        }
        catch(Exception error)
        {
            //
            System.out.println("FAILED TO CONVERT DATES....");
        }
        // return amount
        return amount;

    }

    // function to insert reservations
    public boolean createReservation(String table, String startDate, String endDate, int amount, String customerName)
    {
        /**
         * Function inserts new reservation into table*/

        // affected rows variables
        int rows = 0;

        try
        {
            // prepare statement
            this.statement = this.connection.createStatement();

            // insert statement
            String insertQuery = String.format("INSERT INTO '%s' (startDate,endDate,amount,customerName) VALUES ('%s','%s','%s','%s')",
                    table, startDate, endDate, amount, customerName);

            // execute query
            rows = this.statement.executeUpdate(insertQuery);

        }
        catch(Exception error)
        {
            // display any error messages
            System.out.println("Failed to execute query");
        }

        // return true if rows was inserted
        return (rows > 0)? true: false;

    }

    // function to create new Admin
    public boolean newAdmin(String admin_name,String admin_password)
    {
        /**
         * Function inserts new admin into admin table*/

        // rows inserted variable
        boolean inserted = false;

        try
        {
            // preparing insert statement
            this.statement = this.connection.createStatement();

            // insert query
            String insert = String.format("INSERT INTO admin(admin_name, admin_password) VALUES ('%s', '%s')", admin_name, admin_password);

            // execute insert statement
            int rows = this.statement.executeUpdate(insert);

            if(rows == 1)
            {
                inserted = true;
            }

        }
        catch(Exception exception)
        {
            System.out.println("Failed to execute insert query");
        }

        if(inserted == false)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // function to register customer
    public boolean registerCustomer(String firstName, String lastName, String gender,String email, String phone, String username, String password)
    {
        /**
         * Function inserts new customer in to table*/

        // rows inserted variable
        boolean inserted = false;

        try
        {
            // preparing insert statement
            this.statement = this.connection.createStatement();

            String query = String.format("INSERT INTO customers (first_name,last_name,gender,email,phone,username,password) VALUES" +
                    " ('%s','%s','%s','%s','%s','%s','%s')", firstName, lastName, gender, email, phone, username, password);

            // execute insert statement
            int rows = this.statement.executeUpdate(query);

            if(rows == 1)
            {
                inserted = true;
            }

        }

        catch(Exception exception)
        {
            //
            System.out.println("Failed to execute insert statement....");
        }

        if(inserted == false)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
