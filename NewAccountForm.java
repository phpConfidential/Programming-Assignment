import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewAccountForm extends JFrame{
    private JTextField adminName;
    private JPasswordField adminPassword;
    private JTextField userFirstName;
    private JLabel userLastName;
    private JTextField newUserLastName;
    private JFormattedTextField userEmail;
    private JTextField userPhone;
    private JTextField newUsername;
    private JPasswordField userPassword;
    private JButton newUserButton;
    private JButton newAdminButton;
    private JPanel NewAccountPanel;
    private JRadioButton maleGender;
    private JRadioButton femaleGender;

    public NewAccountForm() {
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Function calls method to execute creating a new customer*/

                // create object

                DatabaseConnection connection = new DatabaseConnection();

                // test connection

                if(connection.databaseConnection())
                {
                    // get new customer details
                    String firstName, lastName, gender, email, phone, username, password;

                    firstName = userFirstName.getText();
                    lastName = newUserLastName.getText();
                    email = userEmail.getText();
                    phone = userPhone.getText();
                    username = newUsername.getText();

                    // create user password
                    password = connection.concatenatePassword(userPassword.getPassword());

                    if(connection.registerCustomer(firstName, lastName,"Female", email, phone, username, password))
                    {
                        // display message user successfully created
                        JOptionPane.showMessageDialog(newUserButton, "User successfully created");
                    }
                    else
                    {
                        // display message user successfully created
                        JOptionPane.showMessageDialog(newUserButton, "Failed to create user");
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(newUserButton, "Failed to connect to database");
                }
            }
        });
    }

    // function
    public void showNewAccount()
    {
        // create new object
        NewAccountForm newAccount = new NewAccountForm();

        newAccount.setSize(600,600);
        newAccount.setContentPane(newAccount.NewAccountPanel);
        newAccount.setTitle("New Account");
        newAccount.setVisible(true);
    }

    public static void main(String[] args) {
        //
    }

}
