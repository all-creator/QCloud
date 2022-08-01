package easy.stars.system.os.object;

import easy.stars.system.os.interfaces.OperationSystem;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MacOS extends SystemInformation implements OperationSystem {

    @Override
    public SystemInformation getInformation() {
        return this;
    }

    @Override
    public Path getMainPath() {
        return Path.of(System.getProperty("user.home"), "Library", "QCloud");
    }

    @Override
    public Path getResourcePath() {
        return Path.of(getMainPath().toString(), "Resource");
    }

    @Override
    public Path getLibsPath() {
        return Path.of(getMainPath().toString(), "Libs");
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
        createResourcePath();
        createLibsPath();
    }

    @Override
    public List<Dependency> getDependencies() {
        return null;
    }
}
