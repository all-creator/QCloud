package easy.stars.server.utils;

import easy.stars.server.Server;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;

public class FileManager {
    String currentPath = System.getProperty("user.home");
    String[] projectsNames = new String[]{"SkypeLeadss",
            "TeleLead",
            "VkLeadrar",
            "InstaLeadrar",
            "Instacoment",
            "VKRegistrator",
            "Skypereg",
            "Vkconnecting"};
    String[] directoriesNames = new String[]{"SkypeLead",
            "TeleLead",
            "VkLead",
            "InstaLead",
            "InstaCom",
            "Vkreg",
            "SkypeReg",
            "VkConnect"};

    public void work(String[] args) throws IOException {
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "cd":
                if (args[1].toLowerCase(Locale.ROOT).equals("в")) {
                    File file;
                    if (args[2].contains("/")) {
                        file = new File(args[2]);
                    } else {
                        file = new File(currentPath + "/" + args[2]);
                    }
                    currentPath = file.getAbsolutePath();
                    if (file.isDirectory()) printList(file);
                    else Server.getInstance().send("Это не директория");
                }
                break;
            case "dir":
                printList(currentPath);
                break;
            case "create":
                if (args[1].equals("auth")){
                    for (int i = 0; i < directoriesNames.length; i++) {
                        String program = projectsNames[i];
                        String directory = directoriesNames[i];
                        File file = Path.of(System.getProperty("user.home"), "AppData", "Local", "Programs", "quasar-desktop", "resources", "software", directory, "data", "project.xml").toFile();
                        try (FileOutputStream writerStream = new FileOutputStream(file)) {
                            file.delete();
                            file.createNewFile();
                            writerStream.write(Holder.AUTH_FILE.getFile(args[3], args[2], program).getBytes());
                            writerStream.flush();
                        }
                    }
                }
                break;
            case "open":
                File file = new File(args[1]);
                if (file.isDirectory()) printList(file);
                else {
                    StringBuilder fileData = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    char[] buf = new char[1024];
                    int numRead = 0;
                    while ((numRead = reader.read(buf)) != -1) {
                        String readData = String.valueOf(buf, 0, numRead);
                        fileData.append(readData);
                    }
                    reader.close();
                    Server.getInstance().send(fileData.toString());
                }
                break;
        }
    }

    public void printList(String path){
        StringBuilder stringBuilder = new StringBuilder();
        File dir = new File(path);
        if (dir.isDirectory()) {
            stringBuilder.append("Полный путь: "+dir.getAbsolutePath()).append("\n\n");
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) stringBuilder.append("Директория: ").append(item.getName()).append("\n");
                else stringBuilder.append("Файл: ").append(item.getName()).append("\n");
            }
        }
        try {
            Server.getInstance().send(stringBuilder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void printList(File dir){
        StringBuilder stringBuilder = new StringBuilder();
        if (dir.isDirectory()) {
            stringBuilder.append("Полный путь: "+dir.getAbsolutePath()).append("\n\n");
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) stringBuilder.append("Директория: ").append(item.getName()).append("\n");
                else stringBuilder.append("Файл: ").append(item.getName()).append("\n");
            }
        }
        try {
            Server.getInstance().send(stringBuilder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
