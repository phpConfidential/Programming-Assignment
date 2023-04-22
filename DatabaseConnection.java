import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
