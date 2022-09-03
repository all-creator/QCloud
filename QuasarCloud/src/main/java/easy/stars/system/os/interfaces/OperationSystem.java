package easy.stars.system.os.interfaces;

import easy.stars.system.os.object.Dependency;
import easy.stars.system.os.object.SystemInformation;

import java.nio.file.Path;
import java.util.List;

public interface OperationSystem extends FileSystem {

    SystemInformation getInformation();

    List<Dependency> getDependencies();

    void loadAllDependencies();

}
