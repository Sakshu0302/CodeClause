import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.util.Scanner;

public class VPNProxy {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'server' to run as VPN Server or 'client' to run as VPN Client:");
        String mode = scanner.nextLine();

        if (mode.equalsIgnoreCase("server")) {
            runServer();
        } else if (mode.equalsIgnoreCase("client")) {
            runClient();
        } else {
            System.out.println("Invalid mode. Please enter 'server' or 'client'.");
        }
    }

    // Method to run VPN-like Server
    private static void runServer() throws Exception {
        int port = 4433;

        // Load the server keystore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("serverkeystore.jks"), "password".toCharArray());

        // Initialize KeyManagerFactory with the keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "password".toCharArray());

        // Create SSL context and initialize it with key managers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Create SSL server socket and bind to the port
        SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);

        System.out.println("VPN-like Server is running on port " + port);

        // Wait for client connection
        while (true) {
            SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    // Method to run VPN-like Client
    private static void runClient() throws Exception {
        String serverAddress = "localhost";
        int port = 4433;

        // Load the client truststore
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("clienttruststore.jks"), "password".toCharArray());

        // Initialize TrustManagerFactory with the truststore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(trustStore);

        // Create SSL context and initialize it with trust managers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create SSL socket and connect to the server
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket(serverAddress, port);

        System.out.println("Connected to VPN-like Server");

        // Create I/O streams for communication
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Send message to server
        out.write("Hello VPN Server\n");
        out.flush();

        // Read response from server
        String response = in.readLine();
        System.out.println("Server response: " + response);

        // Close connection
        socket.close();
    }

    // Inner class to handle multiple clients
    static class ClientHandler implements Runnable {
        private SSLSocket clientSocket;

        public ClientHandler(SSLSocket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    out.write("Echo from server: " + message + "\n");
                    out.flush();
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
