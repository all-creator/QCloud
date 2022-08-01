package easy.stars.system.os.interfaces;

import easy.stars.system.os.object.Dependency;
import easy.stars.system.os.object.SystemInformation;

import java.nio.file.Path;
import java.util.List;

public interface OperationSystem {

    SystemInformation getInformation();

    Path getMainPath();

    Path getResourcePath();

    Path getLibsPath();

    void createMainPath();

    void createLibsPath();

    void createResourcePath();

    void registerPaths();

    List<Dependency> getDependencies();

}
