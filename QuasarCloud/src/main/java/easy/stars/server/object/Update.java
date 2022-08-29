package easy.stars.server.object;

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
}
