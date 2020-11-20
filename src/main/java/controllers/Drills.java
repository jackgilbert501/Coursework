package controllers;

import server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Drills {

    PreparedStatement statement = Main.db.prepareStatement(   //using public connection
            "SELECT DrillID,  FROM  WHERE  LIKE ?"
    );
//statement.setString(1, '%' + searchValue.toLowerCase() + '%');  //% is wildcard so FoodName contains search value
    ResultSet resultSet = statement.executeQuery();

}
