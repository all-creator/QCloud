package easy.stars.system.os.object;

import easy.stars.system.os.interfaces.OperationSystem;
import easy.stars.system.os.interfaces.SoundController;
import easy.stars.system.os.utils.Dependency;

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

    @Override
    public SoundController getSoundController() {
        return null;
    }
}
