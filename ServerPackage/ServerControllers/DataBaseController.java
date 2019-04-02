package ServerPackage.ServerControllers;

import java.io.IOException;
import java.sql.*;

public class DataBaseController {
    private Connection dataCon;
    public DataBaseController () {
        try {
            dataCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "ENSFProjectPassword409!");

            Statement myStmt = dataCon.createStatement();

            ResultSet myRS = myStmt.executeQuery("select * from ");
        }catch (java.sql.SQLException e){
            System.err.println("Error while connecting to data base");
        }

    }


}
