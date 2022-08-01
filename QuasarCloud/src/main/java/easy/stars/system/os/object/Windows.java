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
    public Paths getMainPath() {
        return null;
    }

    @Override
    public Paths getResourcePath() {
        return null;
    }

    @Override
    public Paths getLibsPath() {
        return null;
    }

    @Override
    public List<Dependency> getDependencies() {
        return null;
    }
}
