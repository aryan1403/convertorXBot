package com.hellionbots.Plugins;

import java.util.ArrayList;
import java.util.List;
import com.hellionbots.Master;
import com.hellionbots.convertor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class meow extends convertor implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if (update.getMessage().hasText() && !cmd.startsWith("/")) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

            inlineKeyboardButton1.setText("Convert to PDF");
            inlineKeyboardButton1.setCallbackData("ttpdf");

            inlineKeyboardButton2.setText("Convert to Image");
            inlineKeyboardButton2.setCallbackData("ttimage");

            inlineKeyboardButton3.setText("Convert to Doc");
            inlineKeyboardButton3.setCallbackData("ttdoc");

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

            keyboardButtonsRow1.add(inlineKeyboardButton1);
            keyboardButtonsRow2.add(inlineKeyboardButton2);
            keyboardButtonsRow3.add(inlineKeyboardButton3);

            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);
            rowList.add(keyboardButtonsRow3);

            inlineKeyboardMarkup.setKeyboard(rowList);

            SendMessage sm = new SendMessage();
            sm.setReplyToMessageId(update.getMessage().getMessageId());
            sm.setChatId(chatId(update));
            sm.setText("Choose From the Below!");
            sm.setReplyMarkup(inlineKeyboardMarkup);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().hasDocument()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

            inlineKeyboardButton1.setText("Convert to PDF");
            inlineKeyboardButton1.setCallbackData("dtpdf");

            inlineKeyboardButton2.setText("Convert to Image");
            inlineKeyboardButton2.setCallbackData("dtimage");

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

            keyboardButtonsRow1.add(inlineKeyboardButton1);
            keyboardButtonsRow2.add(inlineKeyboardButton2);

            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);

            inlineKeyboardMarkup.setKeyboard(rowList);

            SendMessage sm = new SendMessage();
            sm.setReplyToMessageId(update.getMessage().getMessageId());
            sm.setChatId(chatId(update));
            sm.setText("Choose From the Below!");
            sm.setReplyMarkup(inlineKeyboardMarkup);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(update.getMessage().hasPhoto()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

            inlineKeyboardButton2.setText("Convert to Png");
            inlineKeyboardButton2.setCallbackData("itpng");

            inlineKeyboardButton3.setText("Convert to Jpg");
            inlineKeyboardButton3.setCallbackData("itjpg");

            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

            keyboardButtonsRow2.add(inlineKeyboardButton2);
            keyboardButtonsRow3.add(inlineKeyboardButton3);

            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow2);
            rowList.add(keyboardButtonsRow3);

            inlineKeyboardMarkup.setKeyboard(rowList);

            SendMessage sm = new SendMessage();
            sm.setReplyToMessageId(update.getMessage().getMessageId());
            sm.setChatId(chatId(update));
            sm.setText("Choose From the Below!");
            sm.setReplyMarkup(inlineKeyboardMarkup);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
