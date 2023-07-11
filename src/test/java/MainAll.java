import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainAll {
    public static void main(String[] args) throws MalformedURLException {

        try {
            String urlString = "https://valorant-api.com/v1/agents";
            URL url = new URL(urlString);

            // Fazendo a solicitação HTTP GET
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lendo a resposta
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Analisando o JSON
            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray agentsArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < agentsArray.length(); i++) {
                JSONObject agentObject = agentsArray.getJSONObject(i);
                String uuid = agentObject.getString("uuid");
                String displayName = agentObject.getString("displayName");
                String description = agentObject.getString("description");
                String displayIcon = agentObject.getString("displayIcon");

                System.out.println("UUID: " + uuid);
                System.out.println("Display Name: " + displayName);
                System.out.println("Description: " + description);
                System.out.println("Display Icon: " + displayIcon);
                System.out.println("-----------------------------------");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
