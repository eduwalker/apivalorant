import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainSelectedAgent {
    public static void main(String[] args) {


        String language = "?language=pt-BR";
        String agentUuid_Wraith = "/8e253930-4c05-31dd-1b6c-968525494517";


        try {
            URL url = new URL("https://valorant-api.com/v1/agents"+agentUuid_Wraith);
            ObjectMapper objectMapper = new ObjectMapper();
            AgentData agentData = objectMapper.readValue(url, AgentData.class);
            System.out.println("\n\nCONSUMINDO API VALORANT. AGENT: "+agentData.getData().getDeveloperName()+"\n\n\n");


            // Acesso aos dados do agente
            System.out.println("UUID: " + agentData.getData().getUuid());
            System.out.println("DisplayName: " + agentData.getData().getDisplayName());
            System.out.println("Description: " + agentData.getData().getDescription());
            System.out.println("\n\nHABILIDADES\n");

            // Acesso às habilidades do agente
            List<Ability> abilities = agentData.getData().getAbilities();
            for (Ability ability : abilities) {
                System.out.println("Ability: " + ability.getDisplayName());
                System.out.println("Description: " + ability.getDescription());
                System.out.println("Icon: " + ability.getDisplayIcon());
                System.out.println("----------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
