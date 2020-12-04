package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("drills/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class Drills {

//test

    @GET
    @Path("list")
    public String UsersList() {
        System.out.println("Invoked Users.UsersList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT DrilllID, DrillName, Description, Rating FROM Users");
            ResultSet results = ps.executeQuery();      // I am using CamelCase for the column names to make it consistant
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("DrilllID", results.getInt(1));
                row.put("DrillName", results.getString(2));
                row.put("Description", results.getString(3));
                row.put("Rating", results.getInt(4));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}