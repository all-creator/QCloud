package easy.stars.server.log;

public enum LocalSystemError {
    ERROR100(100, "system|all", "Unsupported system OS"),
    ERROR110(110, "system|work", "Unsupported protocol type"),
    ERROR310(110, "shutdown|shutdown", "Zero shutdown args"),
    ERROR240(240, "info|getInfo", "Zero info args"),
    ERROR241(241, "info|getInfo", "Unexpected info args"),
    ;

    String description;
    String type;
    int number;

    LocalSystemError(int number, String type, String description) {
        this.description = description;
        this.type = type;
        this.number = number;
    }

    public String getInformation() {
        return number +"|Error:"+ description;
    }
}
