package by.matrosov.telegrambot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message!=null && message.hasText()){
            if (message.getText().equals("Привет")){
                //sendMsgReply(message,message.getText());

                SendMessage s = new SendMessage().setChatId(message.getChatId()).setText("ну привет");

                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rows = new ArrayList<>();
                List<InlineKeyboardButton> row = new ArrayList<>();

                row.add(new InlineKeyboardButton().setText("Update message text").setCallbackData("update_msg_text"));
                //row.add(new InlineKeyboardButton().setText("Vay vay").setCallbackData("vay_vay"));

                // Set the keyboard to the markup
                rows.add(row);

                // Add it to the message
                markup.setKeyboard(rows);
                s.setReplyMarkup(markup);

                try {
                    execute(s);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }else if (update.hasCallbackQuery()){

            if (update.getCallbackQuery().getData().equals("update_msg_text")){
                EditMessageText s = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .setText("Updated message");

                try {
                    execute(s);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    //not used
    private void sendMsgReply(Message message, String text){
        SendMessage s = new SendMessage().setChatId(message.getChatId()).setText(text);
        try {
            execute(s);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
