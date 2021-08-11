package com.hellionbots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.hellionbots.Helpers.configuration;
import com.hellionbots.Plugins.meow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class convertor extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) new meow().handleRequests(update, update.getMessage().getText());
        if(update.hasCallbackQuery()) test(update);
    }
    
    public void test(Update update){
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            String id = callbackQuery.getMessage().getChatId().toString();

            if(data.equals("ttpdf")){
                String text = callbackQuery.getMessage().getReplyToMessage().getText();
                File file = new com.hellionbots.Helpers.convertor().convertTextToPdf(text);
                SendDocument sendDocument = new SendDocument(id, new InputFile(file));
                try {
                    execute(sendDocument);
                    file.delete();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if(data.equals("ttdoc")){
                String text = callbackQuery.getMessage().getReplyToMessage().getText();
                File file = new File("hey");
                
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.append(text);
                    fileWriter.close();

                    SendDocument photo = new SendDocument(id, new InputFile(file));
                
                    execute(photo);
                    file.delete();
                } catch (TelegramApiException | IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(data.equals("ttimage")){
                String text = callbackQuery.getMessage().getReplyToMessage().getText();
                File file = new com.hellionbots.Helpers.convertor().convertTextToPdf(text);
                try {
                    SendPhoto sendPhoto = new SendPhoto(id, new InputFile(file));

                    execute(sendPhoto);
                    file.delete();
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
            }
            if(data.equals("dtpdf")) {
                Document document = update.getCallbackQuery().getMessage().getReplyToMessage().getDocument();
                GetFile getFile = new GetFile(document.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file;
                try {
                    file = execute(getFile);
                    File res = downloadFile(file);
                    execute(new SendDocument(id, new InputFile(res)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if(data.equals("dtimage")) {
                Document document = update.getCallbackQuery().getMessage().getReplyToMessage().getDocument();
                GetFile getFile = new GetFile(document.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file;
                try {
                    file = execute(getFile);
                    File res = downloadFile(file);
                    execute(new SendPhoto(id, new InputFile(res)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if(data.equals("dtdoc")){
                //File file = new com.hellionbots.Helpers.convertor().generatePDFFromImage(file);
                Document doc = update.getCallbackQuery().getMessage().getReplyToMessage().getDocument();
                GetFile getFile = new GetFile(doc.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file;
                try {
                    file = execute(getFile);
                    File res = downloadFile(file);
                    execute(new SendDocument(id, new InputFile(res)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public PhotoSize getPhoto(List<PhotoSize> arr){
        PhotoSize biggSize = null;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                    biggSize = arr.get(i);
            }
        }
        return biggSize;
    }

    public void sendRequests(Update update, String cmd){
        new meow().handleRequests(update, cmd);
    }

    public String getHandler() {
        return configuration.handler;
    }

    public String chatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public void editMessage(Update update, int mid, String text){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId(update));
        editMessageText.setMessageId(mid);
        editMessageText.setText(text);

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Message sendMessage(Update update, String text) {
        Message m;
        SendMessage sMessage = new SendMessage(chatId(update), text);
        sMessage.enableMarkdown(true);

        try {
            m = execute(sMessage);
            return m;
        } catch (TelegramApiException e) {
            e.getMessage();
        }

        return null;
    }

    public String support(){
        return "@HellionBotSupport";
    }

    public String channel(){
        return "@HellionBots";
    }

    @Override
    public String getBotUsername() {
        return configuration.botUserName;
    }

    @Override
    public String getBotToken() {
        return configuration.botToken;
    }
    
}
