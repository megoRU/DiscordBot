package messagesEvents;

public class UsersObjectsForTop {

  private String nameUser;
  private String countConnections;

  public UsersObjectsForTop(String nameUser, String countConnections) {
    this.nameUser = nameUser;
    this.countConnections = countConnections;
  }

  public String getNameUser() {
    return nameUser;
  }

  public void setNameUser(String nameUser) {
    this.nameUser = nameUser;
  }

  public String getCountConnections() {
    return countConnections;
  }

  public void setCountConnections(String countConnections) {
    this.countConnections = countConnections;
  }
}
