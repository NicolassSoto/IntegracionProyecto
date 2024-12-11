package conexion;

import conexion.Conector;
import conexion.Puerto;
import java.io.StringWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class SalidaAPI extends Conector {

    private static final String TELEGRAM_BOT_TOKEN = "7817997364:AAFnifrRU1wY7qVoeVJVqF4LTyPBoacrzr8";
    private static final String TELEGRAM_CHAT_ID = "1107690811";

    public SalidaAPI(Puerto p) {
        super(p);
    }

    public void sendToTelegram() {
        try {
            // Convertir el Document en un String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(getPuerto().leerDoc()), new StreamResult(writer));
            String xmlContent = writer.toString();

            // Construir el mensaje
            String message = "Documento XML:\n" + xmlContent;

            // Enviar el mensaje usando la API de Telegram
            URL url = new URL("https://api.telegram.org/bot" + TELEGRAM_BOT_TOKEN + "/sendMessage");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = String.format(
                "{\"chat_id\": \"%s\", \"text\": \"%s\"}",
                TELEGRAM_CHAT_ID,
                message.replace("\"", "\\\"") // Escapar comillas dobles
            );

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonPayload.getBytes());
            outputStream.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Mensaje enviado correctamente a Telegram.");
            } else {
                System.out.println("Error al enviar el mensaje a Telegram. Código de respuesta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}