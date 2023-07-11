import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class MainJackson{
    public static void main(String[] args) {


        try {
            String urlString = "https://valorant-api.com/v1/agents";
            URL url = new URL(urlString);

            // Lendo o JSON da URL
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(url);

            JsonNode agentsArray = jsonNode.get("data");
            for (JsonNode agentNode : agentsArray) {
                String uuid = agentNode.get("uuid").asText();
                String displayName = agentNode.get("displayName").asText();
              //  System.out.println("UUID: " + uuid);
                System.out.println("> Nome: /" + displayName+" <");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
