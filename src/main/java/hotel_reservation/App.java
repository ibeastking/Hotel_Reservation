package hotel_reservation;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Scanner;

class SQL {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    SQL() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel_db";
        String user = "root";
        String pass = "kansagara@22";

        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {

        }
    }
}

class New_Reservation extends SQL {
    New_Reservation(Scanner sc_sql) throws SQLException {
        super();

        // Scanner sc_sql = new Scanner(System.in);

        try {
            String query = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?,?,?);";
            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter Guest Name: ");
            preparedStatement.setString(1, sc_sql.nextLine());
            System.out.println("Enter Room Number: ");
            preparedStatement.setInt(2, sc_sql.nextInt());
            sc_sql.nextLine();
            System.out.println("Enter Contact Number: ");
            preparedStatement.setString(3, sc_sql.nextLine());

            System.out.println("New Reservation Complete");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("New Reservation Entry Failed");
        }

        // sc_sql.close();
    }
}

class View_Reservation extends SQL {

    View_Reservation() throws SQLException {
        super();

        try {
            String query = "SELECT * FROM reservations";
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Reservation ID: " + resultSet.getInt(1));
                System.out.println("Guest Name: " + resultSet.getString(2));
                System.out.println("Room Number: " + resultSet.getInt(3));
                System.out.println("Contact Number: " + resultSet.getString(4));
                System.out.println("Reservation Data: " + resultSet.getString(5));
                System.out.println("--------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("View Reservation Failed");
        }

        System.out.println("All Reservations Displayed\n\n");
    }
}

class Get_Room_Number extends SQL {

    Get_Room_Number(Scanner sc_sql) throws SQLException {
        super();

        // Scanner sc_sql = new Scanner(System.in);

        try {
            System.out.println("Enter Reservation ID: ");
            int rid = sc_sql.nextInt();
            sc_sql.nextLine();
            String query = "SELECT * FROM reservations WHERE reservation_id = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rid);
            resultSet = preparedStatement.executeQuery();

            System.out.println("--------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Reservation ID: " + resultSet.getInt(1));
                System.out.println("Guest Name: " + resultSet.getString(2));
                System.out.println("Room Number: " + resultSet.getInt(3));
                System.out.println("Contact Number: " + resultSet.getString(4));
                System.out.println("Reservation Data: " + resultSet.getString(5));
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("Reservation Displayed");

        } catch (SQLException e) {
            System.out.println("Reservation Display Failed");
        }

        // sc_sql.close();
    }
}

class Update_Reservation extends SQL {
    Update_Reservation(Scanner sc_sql) throws SQLException {
        super();

        // Scanner sc_sql = new Scanner(System.in);

        try {
            System.out.println("--------------------------------------------------------");
            System.out.println("Enter Reservation ID: ");
            int reservation_id = sc_sql.nextInt();
            sc_sql.nextLine();
            System.out.println("Enter New Name: ");
            String guest_name = sc_sql.nextLine();
            System.out.println("Enter Room Number: ");
            int room_number = sc_sql.nextInt();
            sc_sql.nextLine();
            System.out.println("Enter Contact Number: ");
            String contact_number = sc_sql.nextLine();
            System.out.println("--------------------------------------------------------");

            // StringBuilder query = new StringBuilder();
            // query.append("UPDATE reservations SET guest_name = ");
            // query.append(guest_name);
            // query.append(", room_number = ");
            // query.append(room_number);
            // query.append(", contact_number = ");
            // query.append(contact_number);
            // query.append(" WHERE reservation_id = ");
            // query.append(reservation_id);
            // query.append(";");

            // statement = connection.createStatement();
            // statement.executeUpdate(query.toString());

            String query = "update reservations set guest_name = ?, room_number = ?, contact_number = ? where reservation_id = ?;";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, guest_name);
            preparedStatement.setInt(2, room_number);
            preparedStatement.setString(3, contact_number);
            preparedStatement.setInt(4, reservation_id);

            preparedStatement.executeUpdate();

            System.out.println("Reservation Updated Successful");
        } catch (SQLException e) {
            System.out.println("Reservation Updated Failed");
        }

        // sc_sql.close();
    }
}

class Delete_Reservation extends SQL {
    Delete_Reservation(Scanner sc_sql) throws SQLException {
        // Scanner sc_sql = new Scanner(System.in);

        try {
            StringBuilder query = new StringBuilder();

            query.append("DELETE FROM reservations WHERE reservation_id = ");
            System.out.println("Enter Reservation ID: ");
            query.append(sc_sql.nextInt());
            query.append(";");
            sc_sql.nextLine();

            statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Delete Reservation Successful");
        } catch (SQLException e) {
            System.out.println("Delete Reservation Failed");
        }

        // sc_sql.close();
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("HOTEL RESERVATION MENU");
            System.out.println("1. New Reservation");
            System.out.println("2. View Reservation");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservation");
            System.out.println("5. Delete Reservation");
            System.out.println("6. Exit");
            int opt;

            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Go back to the start of the loop
            }

            if (opt == 6) {
                System.out.print("Exiting");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                break;
            } else {
                switch (opt) {
                    case 1:
                        new New_Reservation(sc);
                        break;
                    case 2:
                        new View_Reservation();
                        break;
                    case 3:
                        // ! Failed
                        new Get_Room_Number(sc);
                        break;
                    case 4:
                        new Update_Reservation(sc);
                        break;
                    case 5:
                        new Delete_Reservation(sc);
                        break;
                }
            }
        }
        sc.close();
    }
}
