import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel MainLoginPanel;
    private JButton createNewAccountButton;

    public LoginForm() {
        /**
         * Function is executed after login attempt
         * 
         * */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // create DatabaseConnection object
                DatabaseConnection connection = new DatabaseConnection();

                // test connection
                if(connection.databaseConnection())
                {
                    // get username
                    String username = textField1.getText();

                    // create password
                    String password = connection.concatenatePassword(passwordField1.getPassword());
                    System.out.println("The password is " + password);
                    System.out.println("The username is " + textField1.getText());

                    // return true if user is found
                    boolean userFound = connection.databaseQuery(username, password.trim());

                    if(userFound == true)
                    {
                        // message string
                        String message = "Welcome " + username;

                        // display welcome message
                        JOptionPane.showMessageDialog(loginButton, message);

                        //

                    }

                    // display no user found
                    else
                    {
                        // message string
                        String message = "User: " + username + " not found. \n Try again \n Username or Password is incorrect";

                        // show message on dialogue box
                        JOptionPane.showMessageDialog(loginButton, message);

                    }
                }

                else
                {
                    // display message connection failed
                    JOptionPane.showMessageDialog(loginButton, "Failed to connect to database");
                }
            }
        });

        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Function displays New Account Form if it is clicked
                 * Calls function to display new form window*/

                // create object
                NewAccountForm newAccount = new NewAccountForm();

                // call method to display new account form
                newAccount.showNewAccount();
            }
        });
    }

    // main method
    public static void main(String[] args) {
        // create instance of Login form
        LoginForm object = new LoginForm();

        object.setContentPane(object.MainLoginPanel);
        object.setTitle("Login");
        object.setSize(500,150);
        object.setVisible(true);
        object.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
