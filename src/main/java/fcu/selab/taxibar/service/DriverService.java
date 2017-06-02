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

import fcu.selab.taxibar.data.Driver;
import fcu.selab.taxibar.db.DriverDbManager;

@Path("driver/")
public class DriverService {

  private DriverDbManager dbManager = DriverDbManager.getInstanse();

  @GET
  @Path("hello")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public String register(@FormParam("plateNumber") String plateNumber) {
    Driver driver = new Driver();
    driver.setPlateNumber(plateNumber);
    boolean check = dbManager.addDriver(driver);

    return String.valueOf(check);
  }

  @GET
  @Path("id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Driver getDriverById(@PathParam("id") int driverId) {
    return dbManager.getDriverById(driverId);
  }

  @GET
  @Path("plateNumber/{plateNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Driver getDriverByPlateNumber(@PathParam("plateNumber") String plateNumber) {
    if (!plateNumber.equals("") || !plateNumber.equals(null)) {
      return dbManager.getDriverByPlateNumber(plateNumber);
    }
    return new Driver();
  }

  @GET
  @Path("list")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Driver> listDrivers() {
    return dbManager.listDrivers();
  }
}
