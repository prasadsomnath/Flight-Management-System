package com.skyllx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class App 
{
    public static void main( String[] args )
    {
        String url="jdbc:mysql://localhost:3306/my_db";
        String username="root";
        String password="root";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println("");
                System.out.println("welcome to 'FLIGHT MANAGEMENT SYSTEM' project");
                System.out.println("1.  New user for register");
                System.out.println("2. Search Flight");
                System.out.println("3. Booking flight");
                System.out.println("4. Cancel Flight");
                System.out.println("5. View Booking");
                System.out.println("6. Checking user_id");
                System.out.println("7. Exit");
                System.out.println(" *******  please select one of the above number: *******");

                // to select number from above we have to take scanner class

                Scanner s= new Scanner(System.in);
                int choise = s.nextInt();
                // using switch case we are calling the particular method or function
                switch (choise){
                    case 1:
                        registeruser(con,s);
                        break;
//
                    case 2:
                        searchflight(con,s);
                        break;
                    case 3:
                        bookingflight(con,s);
                        break;

                    case 4:
                        cancelflight(con,s);
                        break;
                    case 5:
                        viewbooking(con,s);
                        break;

                    case 6:
                        checking_userid(con,s);
                        break;

                    case 7:
                        s.close();
                        con.close();
                        return;
                    default:
                        System.out.println(" invalid number pls type once again ");
                }
            }
        }
        catch ( java.lang.Exception e){
            System.out.println(e.getMessage());
        }
    }


    //  i am writing the logic for each method in below
    private static void checking_userid(Connection con, Scanner s) throws Exception{
        s.nextLine();
        System.out.println("enter your email");
        String email= s.nextLine();
        System.out.println("enter your number");
        String number= s.nextLine();
        String q="select * from users where email_id=? and number=?";
        PreparedStatement ps=con.prepareStatement(q);
        ps.setString(1,email);
        ps.setString(2,number);
        ResultSet re =ps.executeQuery();
        if(re.next() && (email.equals(re.getString("email_id")))) {
            System.out.println("user id is:" + re.getInt("user_id") +
                    " ,Name:" + re.getString("name") +
                    " ,Number:" + re.getString("number"));
        }
        else {
            System.out.println("invalid  email_id and number pls write the correct data");
        }
        ps.close();
    }

    private static void viewbooking(Connection con, Scanner s)throws Exception {
        System.out.println("enter your user id");
        int user_id= s.nextInt();
        String q="select * from booking where user_id=?";
        PreparedStatement ps=con.prepareStatement(q);
        ps.setInt(1,user_id);
        ResultSet re=ps.executeQuery();
        while(re.next()){
            System.out.println(" Booking id:"+re.getInt("booking_id")+
                    " ,user id:"+re.getInt("user_id")+
                    " ,Flight id:"+re.getInt("flight_id")+
                    " ,Seat Number:"+re.getInt("seat_number")+
                    " ,Status:"+re.getString("status"));
        }
        ps.close();
    }

    private static void cancelflight(Connection con, Scanner s)throws Exception {
        System.out.println("Enter your Booking_id for Cancel");
        int user= s.nextInt();
        String q="update booking set status='canceled' where booking_id=?";

        PreparedStatement stmt= con.prepareStatement(q);
        stmt.setInt(1,user);
        int row= stmt.executeUpdate();
        System.out.println(row+" row affected in booking table ");

        if(row>0){
            String cancelQuery="update flights set no_of_seats= no_of_seats+"+row+" where flight_id  IN(select flight_id from booking where booking_id=?)";
            PreparedStatement ps=con.prepareStatement(cancelQuery);
            ps.setInt(1,user);
            int row1=ps.executeUpdate();
            System.out.println("no of row affected is:"+row1);
            ps.close();
            System.out.println("successfully canceled the booking pls check the status and we updated the seat number");
        }
        else {
            System.out.println("there is an error in canceling so pls check the booking status");
        }
        stmt.close();
    }

    private static void bookingflight(Connection con, Scanner s) throws Exception {
        System.out.println("enter user id");
        int user_id=s.nextInt();
        System.out.println("enter your flight_id");
        int flight_id=s.nextInt();
        /// / checking for flight based on flight_id
        String flightChecking="select no_of_seats from flights where flight_id=?";
        PreparedStatement stmt= con.prepareStatement(flightChecking);
        stmt.setInt(1,flight_id);
        ResultSet re= stmt.executeQuery();
        //// first of all we have to check no of seats is available or not then go for booking
        if(re.next() &&  re.getInt("no_of_seats")>0){
            int seatNumber= re.getInt("no_of_seats");

            // write a query of inserting data into booking table
            String conformationQuery="insert into booking(user_id,flight_id,seat_number) values(?,?,?)";
            PreparedStatement stmt1= con.prepareStatement(conformationQuery);
            stmt1.setInt(1,user_id);
            stmt1.setInt(2,flight_id);
            stmt1.setInt(3,seatNumber);

            int row= stmt1.executeUpdate();
            System.out.println("no of booking insertion is : "+row);

/// / after booking we have to update the seats
            String updateQuery="update flights set  no_of_seats =no_of_seats-1 where flight_id=? ";
            PreparedStatement st= con.prepareStatement(updateQuery);
            st.setInt(1,flight_id);
            int row1= st.executeUpdate();
            System.out.println("total seat number updated: "+row1);
            System.out.println("");
            System.out.println("=======*******   successfully Done The Booking   *******======");
            st.close();
            stmt1.close();
        }
        else {
            System.out.println("Booking  is not conformed please check flight once again");
        }

        stmt.close();
    }

    public  static void searchflight(Connection con, Scanner s) throws Exception  {
        s.nextLine(); // Basicaly this line helps to take the string one by one.
        System.out.println("Enter your Departure");
        String departure=s.nextLine();

        System.out.println("Enter your Destination");
        String destination= s.nextLine();
        String q= "select * from flights where departure=? and destination=? and no_of_seats>0";
        PreparedStatement stmt= con.prepareStatement(q);
        stmt.setString(1,departure);
        stmt.setString(2,destination);
        ResultSet re=stmt.executeQuery();

        System.out.println("The Available Flights From " +departure+ " To " + destination);// checking for scanner ip

        while(re.next() ){

            System.out.println(" flight_id "+re.getInt("flight_id")+
                    " ,airline:"+re.getString("airline")+
                    " ,departure:"+re.getString("departure")+
                    " ,destination:"+re.getString("destination")+
                    " ,date:"+re.getDate("date")+
                    " , no-of-seats:"+re.getString("no_of_seats")+
                    ", price:"+re.getDouble("price"));
        }
        stmt.close();
        System.out.println("=======######  ********** #######======");
    }

    public static void registeruser(Connection con,Scanner s) throws Exception{
        s.nextLine();
        System.out.println("enter your name");
        String name= s.nextLine();

        System.out.println("enter your email");
        String email= s.nextLine();
        System.out.println("enter your  phone-number");
        String number= s.nextLine();

        String q ="insert into users(name,email_id,number) values(?,?,?)";
        PreparedStatement stmt= con.prepareStatement(q) ;
        stmt.setString(1,name);
        stmt.setString(2,email);
        stmt.setString(3,number);
        int row= stmt.executeUpdate();
        System.out.println("Number of row affected in user table is:"+row);
        System.out.println("******  User Registered  Successfully!  *******");
        stmt.close();
    }
}
