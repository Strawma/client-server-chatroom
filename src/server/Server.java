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
  private ServerThread client;
  private final HashSet<String> usernames;

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
      client = new ServerThread(serverSocket.accept());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
