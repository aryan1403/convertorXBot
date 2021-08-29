package com.hellionbots.Plugins;

import com.hellionbots.Master;
import com.hellionbots.convertor;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class rename extends convertor implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equals(getHandler()+"rename "+cmd.replace(getHandler()+"rename ", ""))) {
            if(update.getMessage().getReplyToMessage().hasDocument()) {
                Document doc = update.getMessage().getReplyToMessage().getDocument();
                GetFile getFile = new GetFile(doc.getFileId());

                String filename = cmd.replace(getHandler()+"rename ", "");
                int start = 0;

                for (int i = 0; i < doc.getFileName().length(); i++) {
                    if(doc.getFileName().charAt(i) == '.') {
                        start = i+1;
                        break;
                    }
                }

                String extension = doc.getFileName().substring(start);

                try {
                    File f = execute(getFile);
                    java.io.File file = downloadFile(f, new java.io.File(filename+"."+extension));
                    SendDocument document = new SendDocument(chatId(update), new InputFile(file));
                    execute(document);
                    file.delete();
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
    }
    
}
