package fcu.selab.taxibar.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fcu.selab.taxibar.data.Comment;
import fcu.selab.taxibar.db.CommentDbManager;

@Path("comment/")
public class CommentService {

  private CommentDbManager dbManager = CommentDbManager.getInstance();

  private DriverService driverService = new DriverService();

  @GET
  @Path("hello")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  @POST
  @Path("add")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response addComment(@FormParam("plateNumber") String plateNumber, @FormParam("title") String title,
      @FormParam("content") String content, @FormParam("rate") float rate) {
    int driverId = driverService.getDriverByPlateNumber(plateNumber).getId();

    Comment comment = new Comment();
    comment.setDriverId(driverId);
    comment.setUserId(1);
    comment.setTitle(title);
    comment.setComment(content);
    comment.setScore(rate / 5);

    boolean check = dbManager.addComment(comment);

    Response response = Response.ok().build();
    if (!check) {
      response = Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    }
    return response;
  }

  @GET
  @Path("driverId/{driverId}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> getCommentByDriverId(@PathParam("driverId") int driverId) {
    if (driverId != 0) {
      return dbManager.getCommentByDriverId(driverId);
    }

    return new ArrayList<Comment>();
  }

  @GET
  @Path("list")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> listComments() {
    return dbManager.listComments();
  }
}
