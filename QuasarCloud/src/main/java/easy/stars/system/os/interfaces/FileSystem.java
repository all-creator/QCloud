package easy.stars.system.os.interfaces;

import java.nio.file.Path;

public interface FileSystem {

    Path getMainPath();

    Path getResourcePath();

    Path getLibsPath();

    boolean createMainPath();

    boolean createLibsPath();

    boolean createResourcePath();

    void registerPaths();

    Path getResourceByName(String name);

    Path getLibByName(String name);
}
