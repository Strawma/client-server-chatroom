package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is responsible for handling a connection to a single client.
 */
public class ServerThread extends Thread{
  private Server server;
  private final Socket socket;

  public ServerThread(Socket socket) {
    this.socket = socket;
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

      while (true) {
        output.println("Please enter a username:");
        username = input.readLine();
        if (username != null) {
          break;
        }
      }
      output.println("Username accepted");

      while (true) {
        message = input.readLine();
        if (message.equals(Server.QUIT)) {
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
