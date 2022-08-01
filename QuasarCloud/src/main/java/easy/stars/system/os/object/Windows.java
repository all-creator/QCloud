package easy.stars.system.os.object;

import easy.stars.system.os.interfaces.OperationSystem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Windows extends SystemInformation implements OperationSystem {

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
    public void createMainPath() {
        new File(getMainPath().toUri()).mkdir();
    }

    @Override
    public void createLibsPath() {
        new File(getLibsPath().toUri()).mkdir();
    }

    @Override
    public void createResourcePath() {
        new File(getResourcePath().toUri()).mkdir();
    }

    @Override
    public void registerPaths() {
        createMainPath();
        createLibsPath();
        createResourcePath();
    }

    @Override
    public List<Dependency> getDependencies() {
        return null;
    }
}
