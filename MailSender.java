package giallo.zafferano;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class MailSender {

    private String smtpHost;
    private int port;
    private String username;
    private String password;
    private SSLSocket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    // Costruttore per inizializzare i dettagli del server SMTP
    public MailSender(String smtpHost, int port, String username, String password) {
        this.smtpHost = smtpHost;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    // Metodo per connettersi al server SMTP
    public void connect() throws IOException, InterruptedException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) factory.createSocket(smtpHost, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    // Metodo per inviare il messaggio
    public void sendMail(String to, String subject, String body) throws IOException, InterruptedException {
        connect();
        sendCommand("EHLO " + smtpHost);
        sendCommand("AUTH LOGIN");
        sendCommand(encodeBase64(username));
        sendCommand(encodeBase64(password));

        sendCommand("MAIL FROM:<" + username + ">");
        sendCommand("RCPT TO:<" + to + ">");
        sendCommand("DATA");
        sendCommand("Subject: " + subject, false);
        sendCommand("From: " + username, false);
        sendCommand("To: " + to, false);
        sendCommand("", false); // Righe vuote per separare l'header dal corpo
        sendCommand(body, false);
        sendCommand("."); // Fine del messaggio

        readResponse(); // Legge la conferma di invio del messaggio
        close();
    }

    private void sendCommand(String command) throws IOException, InterruptedException {
        sendCommand(command, false);
    }
    
    // Metodo per inviare un comando al server SMTP e leggere la risposta
    private void sendCommand(String command, boolean waitingResponse) throws IOException, InterruptedException {
        writer.println(command);
        writer.flush();
        Thread.sleep(200); // Aspetta 200ms tra l'invio e la lettura della risposta
        if (waitingResponse)
            readResponse();
    }

    // Metodo per leggere una risposta multilinea dal server
    private void readResponse() throws IOException {
        String responseLine;
        while ((responseLine = reader.readLine()) != null) {
            System.out.println("Server Response: " + responseLine);
            if (responseLine.charAt(3) != '-') {
                break;
            }
        }
    }

    // Metodo per chiudere la connessione
    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            try {
                sendCommand("QUIT");
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            socket.close();
            reader.close();
            writer.close();
        }
    }

    // Metodo di utilità per codificare le credenziali in Base64
    private String encodeBase64(String data) {
        return java.util.Base64.getEncoder().encodeToString(data.getBytes());
    }
}
