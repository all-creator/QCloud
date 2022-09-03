package easy.stars.system.os.object;

import easy.stars.App;
import easy.stars.server.utils.Download;
import easy.stars.server.utils.Zip;
import easy.stars.system.os.interfaces.OperationSystem;
import easy.stars.system.os.interfaces.Sounded;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Windows extends OS {

    @Override
    public SystemInformation getInformation() {
        return this;
    }

    @Override
    public Path getMainPath() {
        if (new File(Path.of("C:\\Program Files").toUri()).exists()) {
            return Paths.get("C:\\Program Files\\QCloud");
        } else {
            return Path.of(System.getProperty("user.home"), "QCloud");
        }
    }
    @Override
    public Path getResourcePath() {
        return Path.of(getMainPath().toString(), "res");
    }

    @Override
    public Path getLibsPath() {
        return Path.of(getMainPath().toString(), "lib");
    }

    @Override
    public boolean createMainPath() {
        return new File(getMainPath().toUri()).mkdir();
    }

    @Override
    public boolean createLibsPath() {
        return new File(getLibsPath().toUri()).mkdir();
    }

    @Override
    public boolean createResourcePath() {
        return new File(getResourcePath().toUri()).mkdir();
    }

    @Override
    public void registerPaths() {
        createMainPath();
        createLibsPath();
        createResourcePath();
    }

    @Override
    public Path getResourceByName(String name) {
        return Path.of(getResourcePath().toString(), name);
    }

    @Override
    public Path getLibByName(String name) {
        return  Path.of(getLibsPath().toString(), name);
    }

    @Override
    public List<Dependency> getDependencies() {
        try {
            return List.of(new Dependency(() -> {},"nircmd"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void loadAllDependencies() {
        getDependencies().forEach(Dependency::load);
    }


    @Deprecated(since = "4.0.0")
    public void loadDependency(){
        Download download = new Download("nircmd.exe.zip", "res/download");
        try {
            download.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Zip.extract(this.getResourceByName("nircmd.exe.zip").toFile(), this.getLibsPath().toFile());
    }

    @Override
    public void setVolumeInPercent(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "setsysvolume", String.valueOf(volume*655.35)).start();
            System.out.println(builder.command());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "setsysvolume", String.valueOf(volume)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incVolume(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "changesysvolume", String.valueOf(volume)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decVolume(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "changesysvolume", String.valueOf(-volume)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incVolumeInPercent(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "changesysvolume", String.valueOf(volume*655.35)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decVolumeInPercent(int volume) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(this.getLibByName("nircmd.exe").toAbsolutePath().toString(), "changesysvolume", String.valueOf(-volume*655.35)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
