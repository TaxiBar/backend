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

import fcu.selab.taxibar.data.User;
import fcu.selab.taxibar.db.UserDbManager;

@Path("user/")
public class UserService {

  private UserDbManager dbManager = UserDbManager.getInstance();

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
  public String register(@FormParam("userName") String userName, @FormParam("password") String password,
      @FormParam("email") String email, @FormParam("phone") String phone) {
    User user = new User();
    user.setUserName(userName);
    user.setPassword(password);
    boolean check = dbManager.addUser(user);

    return String.valueOf(check);
  }

  @POST
  @Path("validate")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public String isValidUser(@FormParam("userName") String userName, @FormParam("password") String password) {
    boolean valid = dbManager.validateUser(userName, password);
    return String.valueOf(valid);
  }

  @GET
  @Path("id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser(@PathParam("id") int id) {
    return dbManager.getUserById(id);
  }

  @GET
  @Path("list")
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> listUsers() {
    return dbManager.listUsers();
  }
}
