package easystars.quasarcloudserver.spring.management.busnes.object;

import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Update {
    String type;
    String description;
    String[] args;

    public Update(UpdateType type) {
        this.type = type.getTypeName();
        this.description = type.getDescription();
        this.args = type.getArguments();
    }
}
