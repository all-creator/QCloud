package easy.stars.server;

import easy.stars.App;
import easy.stars.exceptions.LoggingNotSupportFormat;
import easy.stars.server.log.LogBase;
import easy.stars.server.log.Logging;
import easy.stars.server.object.LogMessage;
import easy.stars.server.object.TelegramMessage;
import easy.stars.server.object.Update;
import easy.stars.server.protocol.QCProtocol;
import easy.stars.server.utils.Worker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static easy.stars.App.parser;
import static easy.stars.App.system;

public final class Server {

    public static final String MAIN_URL = "http://88.99.240.171:8081/";

    static Server instance;
    Logging logger;

    public Server() {
        this.logger = Logging.createSystemLog(system.isSendLog());
    }

    public void send(String message) throws IOException {
        QCProtocol protocol = new QCProtocol(()->{}, QCProtocol.ConnectionType.SEND);
        protocol.setOut(parser.toJson(new TelegramMessage(message, system.getLicenseKey().getUuid())).getBytes());
        protocol.jsonContentType();
        protocol.startProcess();
    }

    public void start() {
        AtomicReference<QCProtocol> protocol = new AtomicReference<>();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            protocol.set(new QCProtocol(() -> {
                Update update = parser.fromJson(protocol.get().getIn(), Update.class);
                if (update != null) {
                    try {
                        Worker.work(update);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, QCProtocol.ConnectionType.UPDATE));
            protocol.get().setOut(parser.toJson(system.getLicenseKey().getUuidLocal()).getBytes(StandardCharsets.UTF_8));
            protocol.get().plainTextContentType();
            protocol.get().setDoInput(true);
            protocol.get().startProcess();
        },0, 300, TimeUnit.MILLISECONDS);
    }

    public <T> void log(T logging) throws LoggingNotSupportFormat {
        if (logger == null) return;
        if (logging instanceof LogBase) logger.logIn((LogBase) logging);
        else if (logging instanceof LogMessage) logger.logIn((LogMessage) logging);
        else if (logging instanceof String) logger.logIn((String) logging);
        else throw new LoggingNotSupportFormat("Не поддерживаемый формат логирования: "+logging.getClass().getName());
    }

    public static void register() {
        var protocol = new QCProtocol(()->{}, QCProtocol.ConnectionType.REGISTER);
        protocol.setOut(parser.toJson(App.system.getLicenseKey()).getBytes(StandardCharsets.UTF_8));
        protocol.jsonContentType();
        protocol.startProcess();
    }

    public Logging getLogger() {
        return logger;
    }

    public static Server getInstance() {
        return instance;
    }

    public static void setInstance(Server instance) {
        Server.instance = instance;
    }
}
