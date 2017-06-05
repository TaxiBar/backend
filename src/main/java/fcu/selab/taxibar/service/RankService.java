package fcu.selab.taxibar.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fcu.selab.taxibar.data.Rank;
import fcu.selab.taxibar.db.RankDbManager;

@Path("rank/")
public class RankService {

  private RankDbManager dbManager = RankDbManager.getInstance();

  @GET
  @Path("hello")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  @POST
  @Path("add")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public String addRank(@FormParam("driverId") int driverId, @FormParam("scoreType") String scoreType) {
    int score = 0;

    if (scoreType.equals("scoreOne")) {
      score = 1;
    }
    if (scoreType.equals("scoreZreo")) {
      score = 0;
    }
    if (scoreType.equals("scoreMinusOne")) {
      score = -1;
    }

    Rank rank = new Rank();
    rank.setDriverId(driverId);
    rank.setScore(score);
    boolean check = dbManager.addRank(rank);

    return String.valueOf(check);
  }

  @GET
  @Path("driverId/{driverId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Rank getRank(@PathParam("driverId") int driverId) {
    return dbManager.getRankByDriverId(driverId);
  }

  @GET
  @Path("plateNumber/{plateNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Rank getRank(@PathParam("plateNumber") String plateNumber) {
    return dbManager.getRankByPlateNumber(plateNumber);
  }

  @GET
  @Path("list")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Rank> listRanks() {
    return dbManager.listRanks();
  }
}
