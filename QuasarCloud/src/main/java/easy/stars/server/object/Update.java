package easy.stars.server.object;

import java.util.Arrays;

public class Update {
    String type;
    String description;
    String[] args;

    public Update(String type, String description, String[] args) {
        this.type = type;
        this.description = description;
        this.args = args;
    }

    public Update() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Update update = (Update) o;

        if (type != null ? !type.equals(update.type) : update.type != null) return false;
        if (description != null ? !description.equals(update.description) : update.description != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, update.args);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    @Override
    public String toString() {
        return "Update{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
