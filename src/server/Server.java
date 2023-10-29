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
  private ServerSocket serverSocket;
  private ServerThread client;
  private HashSet<String> usernames;

  public static void main(String[] args) {
    Server server = new Server();
    server.start();
  }

  public Server() {
    usernames = new HashSet<>();
  }

  public void start() {
    try {
      serverSocket = new ServerSocket(PORT);
      System.out.println("Chat server started on port " + PORT);
      ServerThread client = new ServerThread(serverSocket.accept());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
