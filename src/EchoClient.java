import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by michaelmeyer on 1/8/17.
 */
public class EchoClient {

    private static final String SERVER_PORT_ARG = "--serverPort=";
    private static final String SERVER_IP_ARG = "--serverIP=";

    public static void main(String[] args) {
        // Should only have two args for port and ip
        int serverPort = 0;
        String serverIP = "";
        if (args.length == 2) {
            if (args[0].startsWith(SERVER_PORT_ARG) && args[1].startsWith(SERVER_IP_ARG)) {
                serverPort = parsePort(args[0]);
                serverIP = parseIP(args[1]);
            } else if (args[0].startsWith(SERVER_IP_ARG) && args[1].startsWith(SERVER_PORT_ARG)){
                serverPort = parsePort(args[1]);
                serverIP = parseIP(args[0]);
            } else  {
                System.out.println("Illegal arguments. Only valid arguments are: " + SERVER_IP_ARG + "<ip> " + SERVER_PORT_ARG + "<port>");
                System.exit(1);
            }
        } else {
            System.out.println("Illegal argument count. Should have 2 args: " + SERVER_IP_ARG + "<ip> " + SERVER_PORT_ARG + "<port>");
            System.exit(1);
        }

        runClient(serverIP, serverPort);
    }

    /*
        Runs the echo client.
     */
    private static void runClient(String serverIP, int serverPort) {
        try (
                Socket socket = new Socket(serverIP, serverPort);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String userInput;
            while ((userInput = standardInput.readLine()) != null) {
                output.println(userInput);
                System.out.println("echo: " + input.readLine());
            }
        } catch (IOException e) {

        }
    }

    /*
        Helper method to parse server port
     */
    private static int parsePort(String port) {
        String portPortion = port.replace(SERVER_PORT_ARG, "");
        return Integer.parseInt(portPortion);
    }

    /*
        Helper method to parse server ip
     */
    private static String parseIP(String ip) {
        return ip.replace(SERVER_IP_ARG, "");
    }

}
