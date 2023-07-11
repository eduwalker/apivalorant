import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TesteAgentLoc {
    public static void main(String[] args) {


        StringBuilder messageBuilder = new StringBuilder();
        SendMessage message = new SendMessage();

        try {

            String nomeAgent = "Omen";
            int contador = 1;
            String urlString = "https://valorant-api.com/v1/agents";
            URL url = new URL(urlString);

            // Lendo o JSON da URL
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(url);

            JsonNode agentsArray = jsonNode.get("data");
            for (JsonNode agentNode : agentsArray) {
                if(agentNode.get("displayName").asText().equals(nomeAgent)) {
                    String uuid = agentNode.get("uuid").asText();
                    String displayName = agentNode.get("displayName").asText();
                    System.out.println("Nome: "+displayName);
                    System.out.println("UUID " + contador + ": " + uuid);
                }
                // Enviar a foto da habilidade
                contador++;


            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
