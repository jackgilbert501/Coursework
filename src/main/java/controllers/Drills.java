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
    private Integer drillID;
    private String drillName;
    private String description;
    private Integer rating;

//test

    @GET
    @Path("list")
    public String DrillList() {
        System.out.println("Invoked Drills.DrillsList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT DrillID, DrillName, Description, Rating FROM Drills");
            ResultSet results = ps.executeQuery();      // I am using CamelCase for the column names to make it consistant
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("DrillID", results.getInt(1));
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


    @POST
    @Path("add")
    public String DrillsAdd(@FormDataParam("DrillID") Integer DrillID, @FormDataParam("DrillName") String DrillName, @FormDataParam("Description") String Description, @FormDataParam("Rating") Integer Rating) {
        drillID = DrillID;
        drillName = DrillName;
        description = Description;
        rating = Rating;
        System.out.println("Invoked Drills.DrillsAdd()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Drills (DrillName, Description, Rating) VALUES (?, ?, ?)");

            ps.setString(1, DrillName);
            ps.setString(2, Description);
            ps.setInt(3, Rating);

            ps.execute();
            return "{\"OK\": \"Added drill.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }


    @POST
    @Path("delete/{DrillID}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String DeleteDrill(@PathParam("DrillID") Integer DrillID) throws Exception {
        System.out.println("Invoked Drills.DeleteDrill()");
        if (DrillID == null) {
            throw new Exception("DrillID is missing in the HTTP request's URL.");
        }
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Drills WHERE DrillID = ?");
            ps.setInt(1, DrillID);
            ps.execute();
            return "{\"OK\": \"Drill deleted\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }








}