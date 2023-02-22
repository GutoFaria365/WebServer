import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) {
        Socket clientSocket = null;
        try {
            System.out.println("Server is running");
            ServerSocket serverSocket = new ServerSocket(8080);


            while (true) {
                clientSocket = serverSocket.accept();
                BufferedReader clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter requestOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("Connection established:" + " LA:" + clientSocket.getLocalAddress() + " IA:" + clientSocket.getInetAddress()
                        + " Connection Port:" + clientSocket.getPort());

                System.out.println("Waiting request");
                String request = clientRequest.readLine();
                if (request.startsWith("GET")) {
                    String[] parts = request.split(" ");
                    String get = parts[0];
                    String directory = parts[1];

                    System.out.println("request = " + request);
                    requestOutput.println("HTTP/1.1 200 OK");
                    requestOutput.println("Content-Type: text/html");
                    requestOutput.println();


                    dealWithRequest(requestOutput, directory);

                } else if (request.startsWith("PUT")) {
                    System.out.println("request = " + request);
                    requestOutput.println("HTTP/1.1 400 Bad Request");

                } else {
                    System.out.println("request = " + request);
                    requestOutput.println("HTTP/1.1 400 Bad Request");
                }

                clientSocket.close();
                System.out.println("Connection closed");
                System.out.println("-".repeat(50));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void dealWithRequest(PrintWriter requestOutput, String directory) {
        if (directory.equals("/")) {
            requestOutput.println("<html>");
            requestOutput.println("<head><title>Hello, World!</title></head>");
            requestOutput.println("<body>");
            requestOutput.println("<h1>Hello, World!</h1>");
            requestOutput.println("</body></html>");

        } else if (directory.equals("/teste")) {
            requestOutput.println("GET REKT");
        }
    }
//    public static String notFound(String fileName, long length) {
//        return "HTTP/1.0 404 Not Found\r\n" +
//                contentType(fileName) +
//                "Content-Length: " + length + "\r\n\r\n";
//    }
//
//    public static String ok(String fileName, long length) {
//        return "HTTP/1.0 200 Document Follows\r\n" +
//                contentType(fileName) +
//                "Content-Length: " + length + "\r\n\r\n";
//    }
}