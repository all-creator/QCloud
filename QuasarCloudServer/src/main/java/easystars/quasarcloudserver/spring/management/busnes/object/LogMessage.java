package easystars.quasarcloudserver.spring.management.busnes.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {
    String version;
    byte[] client;
    String message;
}
