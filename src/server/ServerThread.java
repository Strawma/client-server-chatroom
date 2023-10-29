package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

/**
 * This class is responsible for handling a connection to a single client.
 */
public class ServerThread extends Thread{
  private Server server;
  private final Socket socket;

  public ServerThread(Server server, Socket socket) {
    this.socket = socket;
    this.server = server;
    System.out.println("Client connected");
    start();
  }

  @Override
  public void run() {
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      output.println("Welcome to the chat server!");
      String username;
      String message;

      HashSet<String> usernames = server.getUsernames();
      while (true) {
        output.println("Please enter a username:");
        username = input.readLine();
        synchronized (usernames) {
          if (username != null && !username.isEmpty()) {
            if (usernames.contains(username)) {
              output.println("Username already taken");
              continue;
            }
            output.println("Username accepted");
            server.addUsername(username);
            break;
          }
        }
      }

      while (true) {
        output.println("Enter a message: ");
        message = input.readLine();
        if (message.equals(Server.QUIT)) {
          server.removeUsername(username);
          System.out.println(username + " disconnected");
          break;
        }
        System.out.println(username+" says: " + message);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
