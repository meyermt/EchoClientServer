import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Basic server that will repeat whatever is sent to it from client
 * Created by michaelmeyer on 1/8/17.
 */
public class EchoServer {

    private static final String PORT_ARG = "--port=";

    public static void main(String[] args) {
        // Should only have one arg for port
        int port = 0;
        if (args.length == 1 && args[0].startsWith(PORT_ARG)) {
            String portPortion = args[0].replace(PORT_ARG, "");
            port = Integer.parseInt(portPortion);
        } else {
            System.out.println("Illegal arguments. Should be run with argument: --port=<port number>.");
            System.exit(1);
        }

        runServer(port);
    }

    /*
        Runs the echo server.
     */
    private static void runServer(int port) {
        try (
                ServerSocket server = new ServerSocket(port);
                Socket client = server.accept();
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            String clientInput;
            while ((clientInput = input.readLine()) != null) {
                output.println(clientInput);
                System.out.println("Got this message: " + clientInput);
            }
        } catch (IOException e) {
            System.out.println("Error in socket connection.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
