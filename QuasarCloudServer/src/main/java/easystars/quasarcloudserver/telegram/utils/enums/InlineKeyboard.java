package easystars.quasarcloudserver.telegram.utils.enums;

import easystars.quasarcloudserver.telegram.utils.ButtonAPI;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public enum InlineKeyboard {
    VOLUME{
        @Override
        public InlineKeyboardMarkup getKeyboard() {
            return ButtonAPI.createInlineKeyboard(
                    new String[][]{{
                        "-1", "-5", "+5", "+1"},{"-10", "-25", "+25", "+10"}, {"-50", "50%", "+50"},{
                        "mute", "0%", "100%", "unmute"
                    }
                    }, new String[][]{{
                        "v:-1", "v:-5", "v:+5", "v:+1"},{ "v:-10", "v:-25", "v:+25", "v:+10"}, {"v:-50", "v:50%", "v:+50"},{
                        "v:mute", "v:0%", "v:100%", "v:unmute"
                    }}
            );
        }
    },
    BRIGHTNESS{
        @Override
        public InlineKeyboardMarkup getKeyboard() {
            return ButtonAPI.createInlineKeyboard(
                    new String[][]{{
                        "-1", "-5", "+5", "+1"},{"-10", "-25", "+25", "+10"}, {"-50", "50%", "+50"},{
                        "0%", "100%"
                    }
                    }, new String[][]{{
                        "b:-1", "b:-5", "b:+5", "b:+1"},{ "b:-10", "b:-25", "b:+25", "b:+10"}, {"b:-50", "b:50%", "b:+50"},{
                        "b:0%", "b:100%"
                    }}
            );
        }
    },
    ;

    public abstract InlineKeyboardMarkup getKeyboard();
}
