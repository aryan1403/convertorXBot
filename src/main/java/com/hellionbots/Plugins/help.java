package com.hellionbots.Plugins;

import java.io.File;
import com.hellionbots.Master;
import com.hellionbots.convertor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class help extends convertor implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"help")) {
            SendPhoto sendPhoto = new SendPhoto(chatId(update), new InputFile(new File("src/main/java/com/hellionbots/res/bot.jpg")));
            sendPhoto.setCaption("Welcome to ConvertorX Help Service\n"
                + "I can convert any text, Document's into PDF, Docs & Image\n\n"
                + "1. Type any text\n"
                + "2. Send me any Document\n"
                + "3. Send me any Image\n\n"
                + "For any Feedback or Help Join " + support() + "\n\n"
                + "Powered by " + channel()
            );

            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
