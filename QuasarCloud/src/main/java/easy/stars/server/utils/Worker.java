package easy.stars.server.utils;

import easy.stars.App;
import easy.stars.server.Server;
import easy.stars.server.log.LocalSystemError;
import easy.stars.server.object.Update;
import easy.stars.system.os.utils.OSUtils;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static easy.stars.App.system;

public class Worker {
    static FileManager manager = new FileManager();

    private Worker() {
    }

    public static void work(Update update) throws IOException {
        System.out.println("Выполнен пакет типа: " + update.getType() + " со значением: " + Arrays.toString(update.getArgs()) + " с описанием: " + update.getDescription());
        switch (update.getType()) {
            case "info" -> getInfo(update.getArgs());
            case "file" -> manager.work(update.getArgs());
            case "shutdown" -> shutdown(Integer.parseInt(update.getArgs()[1]), update.getArgs());
            case "volume" -> parsVolume(Integer.parseInt(update.getArgs()[0]), "%");
            case "brightness" -> SystemController.setBrightness(Integer.parseInt(update.getArgs()[0]));
            case "command" -> runCommand(update.getArgs());
            case "script" -> runScript(update.getArgs());
            case "ping" -> System.out.println("ping");
            default -> Server.getInstance().send(LocalSystemError.ERROR110.getInformation());
        }
    }

    private static void parsVolume(int vol, String type) {
        System.out.println(vol);
        switch (type) {
            case "%" -> system.getOsController().getCurrentOS().setVolumeInPercent(vol);
            case "+" -> system.getOsController().getCurrentOS().incVolumeInPercent(vol);
            case "-" -> system.getOsController().getCurrentOS().decVolumeInPercent(vol);
        }
    }

    private static void runScript(String... args) throws IOException {
        if (args.length < 1) {
            Server.getInstance().send("Ошибка: Неверное кол-во аргументов");
            return;
        }
        if (OSUtils.isWindows()){
            String[] script = new String[args.length+1];
            for (int i = 0; i < args.length+1; i++) {
                if (i == 0) {
                    script[0] = Paths.get(system.getOsController().getCurrentOS().getResourcePath().toString(),"nircmd.exe")
                            .toAbsolutePath().toString();
                    continue;
                }
                script[i] = args[i-1];
            }
            Runtime.getRuntime().exec(script);
        } else if (OSUtils.isMac()) {
            String[] script = new String[args.length+1];
            for (int i = 0; i < args.length+1; i++) {
                if (i == 0) {
                    script[0] = "osascript";
                    continue;
                }
                script[i] = args[i-1];
            }
            Runtime.getRuntime().exec(script);
        } else {
            Server.getInstance().send(LocalSystemError.ERROR100.getInformation());
        }
    }

    private static void runCommand(String... args) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String argument : args) {
            builder.append(" ").append(argument);
        }
        Server.getInstance().send("Выполняю: " + builder);
        Runtime.getRuntime().exec(builder.toString());
    }

    public static void getInfo(String... args) throws IOException {
        if (args.length < 1){
            Server.getInstance().send(LocalSystemError.ERROR240.getInformation());
        }
        SystemInfo systemInfo = new SystemInfo();
        if (args[0].equals("full")){
            HardwareAbstractionLayer hardware = systemInfo.getHardware();
            OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
            String stringBuilder = "Полная информация о системе:\n" + " Подключённые устройства:\n" +
                    "  Дисплеи:\n" + "   Кол-во Дисплеев:" + hardware.getDisplays().size() + "\n" +
                    "  Память:\n" + "   Доступная память: " + hardware.getMemory().getAvailable() + "\n" +
                    "   Всего памяти: " + hardware.getMemory().getTotal() + "\n" +
                    " Информация об ОС:\n  Семейство: " + operatingSystem.getFamily() + "\n" +
                    "  Версия: " + operatingSystem.getVersionInfo().getVersion() + "\n" +
                    "  Сборка: " + operatingSystem.getVersionInfo().getBuildNumber() + "\n" +
                    "Имя пользователя: " + System.getProperty("user.name");
            //stringBuilder.append("Ошибка получения информации о: Processor,NetworkIFs,DiskStores,GraphicsCards,Sensors,PowerSources,SoundCards,UsbDevices");
            Server.getInstance().send(stringBuilder);
        } else if (args[0].equals("low")) {
            String info = "Информация о системе: \n Семейство: " + systemInfo.getOperatingSystem().getFamily() + "\n" +
                    " Версия: " + systemInfo.getOperatingSystem().getVersionInfo().getVersion() + "\n" +
                    " Сборка: " + systemInfo.getOperatingSystem().getVersionInfo().getBuildNumber() + "\n" +
                    " Разрядность: " + System.getProperty("os.arch") + "\n" +
                    "Имя пользователя: " + System.getProperty("user.name");
            Server.getInstance().send(info);
        } else Server.getInstance().send(LocalSystemError.ERROR241.getInformation());
    }

    public static void shutdown(int time, String... args) throws IOException {
        if (args.length < 2){
            Server.getInstance().send(LocalSystemError.ERROR310.getInformation());
        }
        String shutdownCommand;
        String t = time == 0 ? "now" : String.valueOf(time);

        if (OSUtils.isWindows()) {
            shutdownCommand = "shutdown.exe /"+args[0]+" /t " + t;
        } else if (OSUtils.isMac() || OSUtils.isUnix()){
            shutdownCommand = "shutdown -"+args[1]+" " + t;
        } else if (OSUtils.isSolaris()){
            shutdownCommand = "shutdown -y -i5 -g" + t;
            Server.getInstance().send("При работе на системе Solaris данная функция может работать не корректно");
        } else {
            Server.getInstance().send(LocalSystemError.ERROR100.getInformation());
            return;
        }

        Server.getInstance().send("Выключение...");

        /*if(SystemUtils.IS_OS_AIX)
            shutdownCommand = "shutdown -Fh " + t;
        else if(SystemUtils.IS_OS_FREE_BSD || SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC|| SystemUtils.IS_OS_MAC_OSX
        || SystemUtils.IS_OS_NET_BSD || SystemUtils.IS_OS_OPEN_BSD || SystemUtils.IS_OS_UNIX)
            shutdownCommand = "shutdown -h " + t;
        else if(SystemUtils.IS_OS_HP_UX)
            shutdownCommand = "shutdown -hy " + t;
        else if(SystemUtils.IS_OS_IRIX)
            shutdownCommand = "shutdown -y -g " + t;*/

        Runtime.getRuntime().exec(shutdownCommand);
    }


}
