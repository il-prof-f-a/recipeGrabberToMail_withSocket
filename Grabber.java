package giallo.zafferano;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Grabber {
    private URL url;

    @SuppressWarnings("deprecation")
    public Grabber(String urlString) {
        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String fetchPageContent() {
        StringBuilder content = new StringBuilder();

        try {
            String host = url.getHost();
            String path = url.getPath().isEmpty() ? "/" : url.getPath();

            int port = url.getDefaultPort(); // Default SSL port 443
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream writer = socket.getOutputStream();

            // Send HTTP GET request
            writer.write(("GET " + path + " HTTP/1.1\r\n").getBytes());
            writer.write(("Host: " + host + "\r\n").getBytes());
            writer.write("Connection: close\r\n".getBytes());
            writer.write("\r\n".getBytes());
            writer.flush();

            // Read the response
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                // Skip headers and read only content
                if (isHeader && line.isEmpty()) {
                    isHeader = false;
                }
                if (!isHeader) {
                    content.append(line).append("\n");
                }
            }

            // Close the streams and socket
            reader.close();
            writer.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}

