import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WalkerBotComands extends TelegramLongPollingBot {

    private String agentUuid;
    private String nomeAgent;

    @Override
    public String getBotUsername() {
        return "WalkerBot";
    }
    @Override
    public String getBotToken() {
        return "6237039815:AAFLAaCmpslyXIbxH9BI1VLzGsevssb5FHk";
    }
    @Override
    public void onUpdateReceived(Update update) {

    }
    @Override
    public void onUpdatesReceived(List<Update> updates) {  // Lista de acionamento dos comandos.
        super.onUpdatesReceived(updates);
        for (Update update : updates) {

            if (update.hasMessage() && update.getMessage().hasText()) {                        // Executa somente se reconhecer mensagens recebidas

                String message = update.getMessage().getText();                               // Seta a mensagem a ser enviada


                if("/start".equals(message)){                                                 // Lista de comandos
                    long chatId = update.getMessage().getChatId();

                    sendApresentation(chatId);
                    this.nomeAgent = "";
                    this.agentUuid = "";
                    break;
                }

                else if("/verLista".equals(message)){
                    long chatId = update.getMessage().getChatId();

                    listaAgentes(chatId);
                    this.nomeAgent = "";
                    this.agentUuid = "";
                    break;
                }
                else if ("/help".equals(message)) {
                    long chatId = update.getMessage().getChatId();

                    sendHelp(chatId);
                    this.nomeAgent = "";
                    this.agentUuid = "";
                    break;
                }

                else if ("/verAgentes".equals(message)) {
                    long chatID = update.getMessage().getChatId();

                    try {
                        obterFotosAgentes(chatID);
                        this.nomeAgent = "";
                        this.agentUuid = "";
                        break;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
                 if ("/descricaoAgente".equals(message)) {
                    long chatId = update.getMessage().getChatId();

                    String infoMessage = "*SELECIONE A OPÇÃO DESEJADA*\n\n" +
                            "/nomeDoAgente - Exibe diretamete as informações do Agente.\n" +
                            "/verLista - Exibe uma lista com todos os agentes disponíveis.";

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    sendMessage.setText(infoMessage);
                    sendMessage.setParseMode("Markdown");

                    try {
                        execute(sendMessage);
                        this.nomeAgent = "";
                        this.agentUuid = "";
                        break;
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }

                }

//                ("/".equalsIgnoreCase(String.valueOf(message.charAt(0))))
                else if(!"/help".equalsIgnoreCase(message) || "/verAgentes".equalsIgnoreCase(message) || "/verLista".equalsIgnoreCase(message) || "/start".equalsIgnoreCase(message)) {

                  long  chatId = update.getMessage().getChatId();
                     this.nomeAgent = message.replace("/","");
                     System.out.println("Mensagem captada: "+message);
                   // this.nomeAgent = "/Gekko".replace("/","");
                    System.out.println(message);
                    geradorLinkUuid(nomeAgent);

                    try {
                        obterDadosValorant(chatId);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
                }
            }
        }


    public void listaAgentes(long chatId){
        SendMessage message = new SendMessage();
        StringBuilder messageBuilder = new StringBuilder();

        try {
            String urlString = "https://valorant-api.com/v1/agents";
            URL url = new URL(urlString);

            // Lendo o JSON da URL
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(url);

            JsonNode agentsArray = jsonNode.get("data");
            for (JsonNode agentNode : agentsArray) {
              //  String uuid = agentNode.get("uuid").asText();
                String displayName = agentNode.get("displayName").asText();

                messageBuilder.append("| *Nome:*   /").append(displayName).append(" <\n");
                //  System.out.println("UUID: " + uuid);
              //  System.out.println("> Nome: /" + displayName+" <");

            }

            message.setChatId(String.valueOf(chatId));
            message.setText(messageBuilder.toString());
            message.setParseMode("Markdown");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public void sendApresentation(long chatId){
        String apresentationMessage = "Olá humano(a)!\n\n" +
                "Me chamo *WALKER BOT* e sou um BOT que está sendo desenvolvido para lhe fornecer informações sobre VALORANT.\n" +
                "\nFique à vontade para utilizar o comando:\n'/help' e lhe enviarei um breve resumo das minhas funções.";

        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chatId));
        message.setText(apresentationMessage);
        message.setParseMode("Markdown");

        try {
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendHelp(long chatId){


        String helpMessage = "*Comandos disponíveis:* \n\n\n" +
                "/start - Inicializa o WalkerBot (É necessário apenas 1 vez)\n\n" +
                "/help - Exibe as opções de comandos disponíveis\n\n" +
                "/verAgentes - Retorna imagens de perfil de todos agentes\n\n" +
                "/nomeDoAgente - Retorna a descrição completa do agente informado\n\n" +
                "/verLista - Retorna uma lista com os nomes de todos os agentes";

        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chatId));
        message.setText(helpMessage);
        message.setParseMode("Markdown");

        try {
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    private void obterDadosValorant(long chatID) throws TelegramApiException {
        geradorLinkUuid(nomeAgent);
        StringBuilder messageBuilder = new StringBuilder();
        SendMessage message = new SendMessage();

        String language = "?language=pt-BR";
        String agentUuidSelect = agentUuid;
        String response = "";
        String urlComplete = "https://valorant-api.com/v1/agents/"+agentUuidSelect+language;
        System.out.println("URL completa de retorno: "+urlComplete);

        try {

            URL url = new URL("https://valorant-api.com/v1/agents/"+agentUuidSelect+language);
            ObjectMapper objectMapper = new ObjectMapper();
            AgentData agentData = objectMapper.readValue(url, AgentData.class);
            messageBuilder.append("\n\n*DESCRIÇÃO DE AGENTE*\n").append("              "+agentData.getData().getDeveloperName()).append("\n\n\n\n");


            // Acesso aos dados do agente
        //    messageBuilder.append("UUID: ").append(agentData.getData().getUuid()).append("\n");
            messageBuilder.append("*Nome:*\n").append(agentData.getData().getDisplayName()).append("\n\n");
            messageBuilder.append("*História:*\n").append(agentData.getData().getDescription()).append("\n\n");
            messageBuilder.append("\n\n*HABILIDADES*\n").append("\n\n");

            // Obter o link do ícone da habilidade
            String iconUrl = agentData.getData().getDisplayIcon();

            // Criar o objeto InputFile para enviar a imagem como parte da mensagem
            InputFile inputFile = new InputFile(iconUrl);

            // Criar o objeto SendPhoto para enviar a foto da habilidade
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatID));
            sendPhoto.setPhoto(inputFile);
            sendPhoto.setCaption(agentData.getData().getDisplayName());

            // Enviar a foto da habilidade
            execute(sendPhoto);
            
            // Acesso às habilidades do agente
            List<Ability> abilities = agentData.getData().getAbilities();
            int cont = 1;
            for (Ability ability : abilities) {

                messageBuilder.append("\n\n*Habilidade"+" ["+cont+"]"+":*\n").append(ability.getDisplayName()).append("\n");
                messageBuilder.append("\n*Descrição:*\n").append(ability.getDescription()).append("\n");
                //messageBuilder.append("Icon: ").append(ability.getDisplayIcon()).append("\n");
                messageBuilder.append("-------------------------------------------\n");
                cont++;
            }
            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }

        message.setChatId(String.valueOf(chatID));
        message.setText(messageBuilder.toString());
        message.setParseMode("Markdown");

        execute(message);

    }
    private void obterFotosAgentes(long chatId) throws TelegramApiException {

        StringBuilder messageBuilder = new StringBuilder();
        SendMessage message = new SendMessage();

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
                String description = agentNode.get("description").asText();
                String displayIcon = agentNode.get("displayIcon").asText();
             //   messageBuilder.append("*Nome:*\n").append(displayName).append("\n\n");
              //  System.out.println("UUID: " + uuid);

                String iconUrl = displayIcon;

                // Criar o objeto InputFile para enviar a imagem como parte da mensagem
                InputFile inputFile = new InputFile(iconUrl);

                // Criar o objeto SendPhoto para enviar a foto da habilidade
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(String.valueOf(chatId));
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setCaption(displayName);

                // Enviar a foto da habilidade
                execute(sendPhoto);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        message.setChatId(String.valueOf(chatId));
        message.setText(messageBuilder.toString());
        message.setParseMode("Markdown");

        execute(message);
    }
    private void selectAgent(String agentUuid){
        this.agentUuid = agentUuid;

    }

    private void geradorLinkUuid (String nomeAgent) {
        this.nomeAgent = nomeAgent;
        try {

            int contador = 1;
            String urlString = "https://valorant-api.com/v1/agents";
            URL url = new URL(urlString);

            // Lendo o JSON da URL
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(url);

            JsonNode agentsArray = jsonNode.get("data");

            for (JsonNode agentNode : agentsArray) {
                if(agentNode.get("displayName").asText().equals(nomeAgent)) { // Filtro para achar o agente selecionado
                    String uuid = agentNode.get("uuid").asText();
                    String displayName = agentNode.get("displayName").asText();
                    System.out.println("Nome: "+displayName);
                    System.out.println("UUID" + uuid);
                    this.agentUuid=uuid;
                } contador++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
