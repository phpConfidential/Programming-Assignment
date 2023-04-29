import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ReservationForm extends JFrame{
    private JPanel reservationPanel;
    private JPanel ReservationPanel;

    // start date object
    JDateChooser initialDate = new JDateChooser();

    // end date object
    JDateChooser finalDate = new JDateChooser();

    private JTextField endDate;
    private JButton familyButton;
    private JPanel StartDatePanel;

    public ReservationForm()
    {
        // add start date and end date
        StartDatePanel.add(initialDate);
        StartDatePanel.add(finalDate);
        StartDatePanel.setVisible(true);

        // change date format to mysql format
        initialDate.setDateFormatString("YY-MM-dd");
        finalDate.setDateFormatString("YY-MM-dd");

        familyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Function validates the reservation*/

                // create simple date format objects
                SimpleDateFormat simpleStartDate = new SimpleDateFormat("YY-MM-dd");
                SimpleDateFormat simpleEndDate = new SimpleDateFormat("YY-MM-dd");

                // get initial date and end date
                String customerStartDate = simpleStartDate.format(initialDate.getDate());
                String customerEndDate = simpleEndDate.format(finalDate.getDate());

                System.out.println(customerStartDate);

                // validate if start date < end date || start date == end date
                if(customerEndDate.compareTo(customerStartDate) < 0 || customerStartDate.compareTo(customerStartDate) == 0)
                {
                    //print valid reservation dates give
                    System.out.println("Dates selected are valid");

                    // create database object
                    DatabaseConnection dbObject = new DatabaseConnection();

                    // test connection
                    if(dbObject.databaseConnection())
                    {
                        boolean valueReturned = dbObject.confirmReservation("familyroom",customerStartDate,customerEndDate);
                        System.out.println("Value returned is : "+ valueReturned);

                        // call function to confirm reservation
                        if(dbObject.confirmReservation("familyroom",customerStartDate,customerEndDate))
                        {
                            // call function to calculate amount
                            int amount = dbObject.calculateDuration(customerStartDate, customerEndDate, 2000);

                            // call function to insert new reservation
                            if(dbObject.createReservation("familyroom", customerStartDate, customerEndDate, amount, "Mathias"))
                            {
                                String successful = "Dear Customer - [customer name], your reservation is successful \n";
                                String available = String.format("The room is available to you from '%s' to '%s' \n", customerStartDate, customerEndDate);
                                String enjoy = "Please enjoy your stay";

                                // display full message
                                JOptionPane.showMessageDialog(familyButton,successful+available+enjoy);
                            }
                        }

                        // display message
                        else
                        {
                            String message = "Room already booked for given dates \n Please supply different dates or try another room";
                            // reservation dates
                            JOptionPane.showMessageDialog(familyButton, message);
                        }
                    }
                    else
                    {
                        // failed to connect to database
                        JOptionPane.showMessageDialog(familyButton, "Database connection failed");
                    }

                }
                else
                {
                    String message = String.format("Invalid dates given \n Start has to be before End Date \n  Not from '%s' to '%s'", customerStartDate, customerEndDate);
                    JOptionPane.showMessageDialog(familyButton, message);
                    System.out.println("Reservation is invalid");
                }

            }
        });
    }

    public static void main(String[] args) {

        // create new reservations object
        ReservationForm reserveObject = new ReservationForm();


        reserveObject.setContentPane(reserveObject.ReservationPanel);

        // set GUI attributes
        reserveObject.setVisible(true);
        reserveObject.setDefaultCloseOperation(EXIT_ON_CLOSE);
        reserveObject.setSize(400,400);
        reserveObject.setTitle("New Reservations");
    }
}
