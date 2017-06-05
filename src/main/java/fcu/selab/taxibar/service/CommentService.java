package fcu.selab.taxibar.service;

import java.net.URISyntaxException;
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

import fcu.selab.taxibar.data.Comment;
import fcu.selab.taxibar.db.CommentDbManager;

@Path("comment/")
public class CommentService {

  private CommentDbManager dbManager = CommentDbManager.getInstance();

  private RankService rankService = new RankService();

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
  public Response addComment(@FormParam("plateNumber") String plateNumber, @FormParam("content") String content,
      @FormParam("rate") int rate) throws URISyntaxException {
    int driverId = driverService.getDriverByPlateNumber(plateNumber).getId();

    Comment comment = new Comment();
    comment.setDriverId(driverId);
    comment.setUserId(1);
    comment.setComment(content);
    comment.setScore(rate);

    boolean check = dbManager.addComment(comment);

    caculateRank(driverId);

    // Response response = Response.ok().build();
    if (!check) {
      // response =
      // Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
      java.net.URI location = new java.net.URI("../GiveComment.jsp");
    }
    // return response;
    java.net.URI location = new java.net.URI("../GiveComment.jsp");
    return Response.temporaryRedirect(location).build();
  }

  public void caculateRank(int driverId) {
    List<Comment> comments = getCommentByDriverId(driverId);
    int score = 0;
    String scoreType = "";

    for (Comment comment : comments) {
      score = score + comment.getScore();
      if (score == 1) {
        scoreType = "scoreOne";
      }
      if (score == 0) {
        scoreType = "scoreZreo";
      }
      if (score == -1) {
        scoreType = "scoreMinusOne";
      }
    }

    rankService.addRank(driverId, scoreType);
  }
  //
  // public String getMax(int scoreOne, int scoreZreo, int scoreMinusOne) {
  // String scoreType = "";
  //
  // if (scoreOne >= scoreZreo) {
  // if (scoreZreo >= scoreMinusOne) {
  // scoreType = "scoreOne";
  // } else if (scoreOne >= scoreMinusOne) {
  // scoreType = "scoreOne";
  // } else {
  // scoreType = "scoreMinusOne";
  // }
  // } else {
  // if (scoreOne >= scoreMinusOne) {
  // scoreType = "scoreZreo";
  // } else if (scoreZreo >= scoreMinusOne) {
  // scoreType = "scoreZreo";
  // } else {
  // scoreType = "scoreMinusOne";
  // }
  // }
  //
  // return scoreType;
  // }

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
