package com.hellionbots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.aspose.ocr.AsposeOCR;
import com.hellionbots.Helpers.configuration;
import com.hellionbots.Plugins.help;
import com.hellionbots.Plugins.meow;
import com.hellionbots.Plugins.start;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
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
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class convertor extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        GetChatMember getChatMember;
        if (update.hasCallbackQuery()) {
            getChatMember = new GetChatMember("@HellionBotSupport",
                    update.getCallbackQuery().getMessage().getFrom().getId());

        } else {
            getChatMember = new GetChatMember("@HellionBotSupport", update.getMessage().getFrom().getId());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(15);
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    ChatMember c = execute(getChatMember);
                    if (!c.getStatus().equals("left")) {
                        if (update.hasMessage())
                            sendRequests(update, update.getMessage().getText());
                        if (update.hasCallbackQuery())
                            test(update);
                    } else {
                        sendMessage(update, "Join @HellionBots\nJoin @HellionBotSupport\n\nIn order to use me :)");
                    }
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }

            }
        });
        executorService.shutdown();

    }

    public void test(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            String id = callbackQuery.getMessage().getChatId().toString();

            if (data.equals("ttpdf")) {
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
            if (data.equals("ttdoc")) {
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
            if (data.equals("ttimage")) {
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
            if (data.equals("dtpdf")) {
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
            if (data.equals("dtimage")) {
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
            if (data.equals("dtdoc")) {
                // File file = new
                // com.hellionbots.Helpers.convertor().generatePDFFromImage(file);
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
            if (data.equals("itt")) {
                List<PhotoSize> arr = update.getCallbackQuery().getMessage().getReplyToMessage().getPhoto();

                PhotoSize biggSize = null;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < arr.size(); j++) {
                        if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                            biggSize = arr.get(i);
                    }
                }
                PhotoSize photos = biggSize;
                GetFile getFiled = new GetFile();
                getFiled.setFileId(photos.getFileId());

                org.telegram.telegrambots.meta.api.objects.File file;
                try {
                    file = execute(getFiled);
                    File res = downloadFile(file);

                    BufferedImage buffImg = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

                    try {
                        buffImg = ImageIO.read(res);
                    } catch (IOException e) {
                    }
                    Message m;
                    SendMessage sendMessage = new SendMessage(id, "Reading Image...");
                    sendMessage.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());

                    m = execute(sendMessage);

                    // Create api instance
                    AsposeOCR api = new AsposeOCR();

                    // Recognize page by full path to file
                    String result = api.RecognizePage(buffImg);

                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setMessageId(m.getMessageId());
                    editMessageText.setText(result);
                    editMessageText.setChatId(id);

                    execute(editMessageText);
                    res.delete();
                } catch (IOException | TelegramApiException e) {
                }
            }
            if (data.equals("itpng")) {
                List<PhotoSize> arr = update.getCallbackQuery().getMessage().getReplyToMessage().getPhoto();

                PhotoSize biggSize = null;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < arr.size(); j++) {
                        if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                            biggSize = arr.get(i);
                    }
                }
                PhotoSize photos = biggSize;
                GetFile getFiled = new GetFile();
                getFiled.setFileId(photos.getFileId());

                org.telegram.telegrambots.meta.api.objects.File file;

                try {
                    file = execute(getFiled);
                    File res = downloadFile(file);

                    BufferedImage buffImg = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

                    try {
                        buffImg = ImageIO.read(res);
                    } catch (IOException e) {
                    }
                    res.delete();

                    File outputfile = new File("image.png");
                    try {
                        ImageIO.write(buffImg, "png", outputfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SendPhoto sendMessage = new SendPhoto(id, new InputFile(outputfile));
                    sendMessage.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());

                    execute(sendMessage);
                    outputfile.delete();
                } catch (TelegramApiException e) {
                }
            }
            if (data.equals("itjpg")) {
                List<PhotoSize> arr = update.getCallbackQuery().getMessage().getReplyToMessage().getPhoto();

                PhotoSize biggSize = null;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < arr.size(); j++) {
                        if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                            biggSize = arr.get(i);
                    }
                }
                PhotoSize photos = biggSize;
                GetFile getFiled = new GetFile();
                getFiled.setFileId(photos.getFileId());

                org.telegram.telegrambots.meta.api.objects.File file;

                try {
                    file = execute(getFiled);
                    File res = downloadFile(file);

                    BufferedImage buffImg = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

                    try {
                        buffImg = ImageIO.read(res);
                    } catch (IOException e) {
                    }
                    res.delete();

                    File outputfile = new File("image.jpg");
                    try {
                        ImageIO.write(buffImg, "jpg", outputfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SendPhoto sendMessage = new SendPhoto(id, new InputFile(outputfile));
                    sendMessage.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());

                    execute(sendMessage);
                    outputfile.delete();
                } catch (TelegramApiException e) {
                }
            }
        }

    }

    class ImageConverter {
        public File convertTo(String filename, String formatName) throws IOException {
            FileInputStream inputStream = new FileInputStream(filename);
            FileOutputStream outputStream = new FileOutputStream(filename);

            BufferedImage inputImage = ImageIO.read(inputStream);

            ImageIO.write(inputImage, formatName, outputStream);

            outputStream.close();
            inputStream.close();

            return new File(filename);
        }
    }

    public PhotoSize getPhoto(List<PhotoSize> arr) {
        PhotoSize biggSize = null;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                    biggSize = arr.get(i);
            }
        }
        return biggSize;
    }

    public void sendRequests(Update update, String cmd) {
        new meow().handleRequests(update, cmd);
        new start().handleRequests(update, cmd);
        new help().handleRequests(update, cmd);
    }

    public String getHandler() {
        return configuration.handler;
    }

    public String chatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public void editMessage(Update update, int mid, String text) {
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

    public String support() {
        return "@HellionBotSupport";
    }

    public String channel() {
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
