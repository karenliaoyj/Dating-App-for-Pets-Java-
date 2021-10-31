package com.yenju;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
 
@Path("/student")
public class StudentResource {
 
    @GET
    @Path("/info/{user}")
    @Produces("application/json")
    public Response getStudentInformation(@PathParam("user") String user) {
        //this method get the information of the student
        return Response.ok("API for getting information for user " + user).build();
    }
 
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response enrollStudent() {
        //for simplicity, we just return a string
        return Response.ok("Successfully enrolled student via POST request").build();
    }
 
    @PUT
    @Produces("application/json")
    public Response addNewStudent() {
        return Response.ok("Added new Student").build();
    }
 
    @DELETE
    @Path("/{user}")
    @Produces("text/plain")
    public Response deleteStudent(@PathParam("user") String user) {
        return Response.ok("Successfully deleted student " + user).build();
    }
}