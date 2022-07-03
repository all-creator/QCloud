package easystars.quasarcloudserver.spring.management.busnes.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {
    String message;
    byte[] clientId;
}
