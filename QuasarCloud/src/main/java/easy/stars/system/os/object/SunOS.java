package easy.stars.system.os.object;

import easy.stars.system.os.interfaces.OperationSystem;

import java.nio.file.Path;
import java.util.List;

public class SunOS implements OperationSystem {

    @Override
    public SystemInformation getInformation() {
        return null;
    }

    @Override
    public Path getMainPath() {
        return null;
    }

    @Override
    public Path getResourcePath() {
        return null;
    }

    @Override
    public Path getLibsPath() {
        return null;
    }

    @Override
    public void createMainPath() {

    }

    @Override
    public void createLibsPath() {

    }

    @Override
    public void createResourcePath() {
    }

    @Override
    public void registerPaths() {

    }

    @Override
    public List<Dependency> getDependencies() {
        return null;
    }
}
