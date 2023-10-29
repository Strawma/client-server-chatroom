package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import server.Server;

/**
 * This class is responsible for starting the client and connecting to the server.
 */
public class Client {

  private Socket socket;
  private boolean quit;

  public static void main(String[] args) {
    Client client = new Client();
    client.start();
  }

  public void start() {
    try {
      quit = false;
      socket = new Socket(Server.HOST, Server.PORT);
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      Scanner scanner = new Scanner(System.in);
      System.out.println("Client started");
      new Thread(() -> { // thread for sending messages
        try {
          String message;
          while (!quit) {
            message = scanner.nextLine();
            output.println(message);
            if (message.equals(Server.QUIT)) {
              System.out.println("Goodbye");
              quit = true;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
      while (!quit) {
        String serverMessage = input.readLine();
        if (serverMessage == null) {
          break;
        }
        System.out.println(serverMessage);
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
