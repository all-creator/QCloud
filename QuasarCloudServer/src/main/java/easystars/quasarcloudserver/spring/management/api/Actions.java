package easystars.quasarcloudserver.spring.management.api;

import lombok.Getter;

public enum Actions {
    GET("action=get"),
    UPDATE("action=update"),
    INSERT("action=insert"),
    DELETE("action=delete");

    @Getter
    private final String action;

    Actions(String action) {
        this.action = action;
    }
}
