package easy.stars.system.language;

import java.util.Locale;

public class I18n {

    Locale locale = Locale.getDefault();

    public enum Messages {
        OS_INFO{
            @Override
            public String getMessage() {
                return """
                %s
                Операционная система: %s %s:%s x%i
                Время работы: %l
                Количество потоков (системных): %i
                Память (ПЗУ): %l/%l
                Количество дисплеев: %i
                Процессор: %s %s %s %s
                """;
            }
        },
        OS_FULL_INFO{
            @Override
            public String getMessage() {
                return """
                Имя пользователя: %s
                Операционная система:
                    Семейство: %s
                    Версия: %s
                    Сборка: %s
                    Код системы: %s
                    Разрядность: x%i
                TODO: Доделать
                """;
            }
        }
        ;

        public abstract String getMessage();
    }

}
