package com.hellionbots.Plugins;

import com.hellionbots.Master;
import com.hellionbots.convertor;

import org.telegram.telegrambots.meta.api.objects.Update;

public class start extends convertor implements Master{

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"start")){
            sendMessage(update, "Hello "
                + update.getMessage().getFrom().getFirstName()
                + "\nWelcome to ConvertorXBot I will help you convert "
                + "text, Files, Document's & Images into Other extensions.\n"
                + "Type /help to see All the available commands."
            );
        }
        
    }
    
}
