package giallo.zafferano;

    public class BodyMailEncoder {
    
    public static String createSimpleMessage(Ricetta ricetta){
        return ricetta.getTitolo() + "\n\r" + ricetta.getCalorie() + " kcal" +"\n\r" + ricetta.getUrl();
    }
    
    // Metodo per creare un messaggio MIME con testo e HTML
    public static String createMimeMessage(Ricetta ricetta) {
        String boundary = "----=_NextPart_000_001";
        
        StringBuilder mimeMessage = new StringBuilder();
        mimeMessage.append("MIME-Version: 1.0\r\n");
        mimeMessage.append("Content-Type: multipart/alternative; boundary=\"" + boundary + "\"\r\n");
        mimeMessage.append("\r\n");
    
        // HTML
        mimeMessage.append("--" + boundary + "\r\n");
        mimeMessage.append("Content-Type: text/html; charset=UTF-8\r\n");
        mimeMessage.append("Content-Transfer-Encoding: quoted-printable\r\n" + 
                        "\r\n" + 
                        "<!doctype html>\r\n" + 
                        "<html xmlns=3D\"http://www.w3.org/1999/xhtml\" xmlns:o=3D\"urn:schemas-microso=\r\n" + 
                        "ft-com:office:office\" xmlns:v=3D\"urn:schemas-microsoft-com:vml\" >\r\n" + 
                        "<body>\r\n");
        mimeMessage.append("\r\n");
        mimeMessage.append(("<h1>"+ricetta.getTitolo() + "</h1>" + ricetta.getCalorie() + " kcal" +"<br />"+ "<a href='" + ricetta.getUrl() + "'>" + ricetta.getUrl() + "</a>") + "\r\n</body>");
        
        // Fine del messaggio
        mimeMessage.append("--" + boundary + "--\r\n");

        return mimeMessage.toString();
    }
}
