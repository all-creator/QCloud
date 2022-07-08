package easy.stars.server.utils;

import easy.stars.server.data.FileUtils;
import easy.stars.server.log.LocalSystemError;
import easy.stars.server.Server;
import easy.stars.server.object.Update;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;


import java.io.IOException;

public class Worker {
    static FileManager manager = new FileManager();

    private Worker() {
    }

    public static void work(Update update) throws IOException {
        switch (update.getType()) {
            case "info" -> getInfo(update.getArgs());
            case "file" -> manager.work(update.getArgs());
            case "shutdown" -> shutdown(Integer.parseInt(update.getArgs()[1]), update.getArgs());
            case "volume" -> SystemController.setVolume(Integer.parseInt(update.getArgs()[0]));
            case "brightness" -> SystemController.setBrightness(Integer.parseInt(update.getArgs()[0]));
            case "command" -> runCommand(update.getArgs());
            case "script" -> runScript(update.getArgs());
            default -> Server.getInstance().send(LocalSystemError.ERROR110.getInformation());
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
                    script[0] = FileUtils.getResPath("nircmd.exe").toAbsolutePath().toString();
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
        Server.getInstance().send("Выполняю: " + builder.toString());
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Полная информация о системе:\n").append(" Подключённые устройства:\n")
                    .append("  Дисплеи:\n").append("   Кол-во Дисплеев:").append(hardware.getDisplays().size()).append("\n");
            stringBuilder.append("  Память:\n").append("   Доступная память: ").append(hardware.getMemory().getAvailable()).append("\n")
                    .append("   Всего памяти: ").append(hardware.getMemory().getTotal()).append("\n");
            stringBuilder.append(" Информация об ОС:\n  Семейство: ").append(operatingSystem.getFamily()).append("\n")
                    .append("  Версия: ").append(operatingSystem.getVersionInfo().getVersion()).append("\n")
                    .append("  Сборка: ").append(operatingSystem.getVersionInfo().getBuildNumber()).append("\n");
            stringBuilder.append("Имя пользователя: ").append(System.getProperty("user.name"));
            //stringBuilder.append("Ошибка получения информации о: Processor,NetworkIFs,DiskStores,GraphicsCards,Sensors,PowerSources,SoundCards,UsbDevices");
            Server.getInstance().send(stringBuilder.toString());
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
            Server.getInstance().send("При работе на системе Solaris данная функция может работать не коректно");
        } else {
            Server.getInstance().send(LocalSystemError.ERROR100.getInformation());
            return;
        }

        Server.getInstance().send("Выключение...");

        /*if(SystemUtils.IS_OS_AIX)
            shutdownCommand = "shutdown -Fh " + t;
        else if(SystemUtils.IS_OS_FREE_BSD || SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC|| SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_NET_BSD || SystemUtils.IS_OS_OPEN_BSD || SystemUtils.IS_OS_UNIX)
            shutdownCommand = "shutdown -h " + t;
        else if(SystemUtils.IS_OS_HP_UX)
            shutdownCommand = "shutdown -hy " + t;
        else if(SystemUtils.IS_OS_IRIX)
            shutdownCommand = "shutdown -y -g " + t;*/

        Runtime.getRuntime().exec(shutdownCommand);
    }


}
