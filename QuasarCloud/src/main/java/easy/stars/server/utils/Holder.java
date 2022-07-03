package easy.stars.server.utils;

public enum Holder {
    AUTH_FILE("""
            <?xml version="1.0" encoding="UTF-8"?>
            <Remote Mode="1" Password="%s" Username="%s" Version="last" ScriptName="%s"/>
            """){
        @Override
        public String getFile(String password, String username, String program) {
            return file.formatted(password, username, program);
        }
    },
    ;

    String file;

    Holder(String file) {
        this.file = file;
    }

    public abstract String getFile(String password, String username, String program);
}
