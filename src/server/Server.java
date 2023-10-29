package server;

import java.net.ServerSocket;
import java.util.HashSet;

/**
 * This class is responsible for starting the server and accepting connections.
 */
public class Server {

  public static final int PORT = 1234;
  public static final String HOST = "localhost";
  public static final String QUIT = "quit";
  private final HashSet<String> usernames; // set of usernames currently connected

  public static void main(String[] args) {
    Server server = new Server();
    server.start();
  }

  public Server() {
    usernames = new HashSet<>();
  }

  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("Chat server started on port " + PORT);
      while (true) {
        new ServerThread(this, serverSocket.accept());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public HashSet<String> getUsernames() {
    return usernames;
  }

  public void addUsername(String username) {
    usernames.add(username);
  }

  public void removeUsername(String username) {
    usernames.remove(username);
  }
}
