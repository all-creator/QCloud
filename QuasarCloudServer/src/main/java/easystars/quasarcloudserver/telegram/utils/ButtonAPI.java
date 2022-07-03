package easystars.quasarcloudserver.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonAPI {

    private ButtonAPI() {
    }

    public static ReplyKeyboardMarkup createKeyboard(String[][] buttonsNames){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardButton keyboardButton = new KeyboardButton();
        for (String[] buttons : buttonsNames) {
            for (String button : buttons) {
                keyboardButton.setText(button);
                keyboardRow.add(keyboardButton);
                keyboardButton = new KeyboardButton();
            }
            keyboardRowList.add(keyboardRow);
            keyboardRow = new KeyboardRow();
        }
        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createInlineKeyboard(String[][] buttonsNames, String[][] callbackData){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        for (int i = 0, buttonsNamesLength = buttonsNames.length; i < buttonsNamesLength; i++) {
            String[] buttons = buttonsNames[i];
            for (int j = 0, buttonsLength = buttons.length; j < buttonsLength; j++) {
                String button = buttons[j];
                keyboardButton.setText(button);
                keyboardButton.setCallbackData(callbackData[i][j]);
                keyboardRow.add(keyboardButton);
                keyboardButton = new InlineKeyboardButton();
            }
            keyboardRowList.add(keyboardRow);
            keyboardRow = new ArrayList<>();
        }
        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }
}
