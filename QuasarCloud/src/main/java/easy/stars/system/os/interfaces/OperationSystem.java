package easy.stars.system.os.interfaces;

import easy.stars.system.os.object.Dependency;
import easy.stars.system.os.object.SystemInformation;

import java.nio.file.Paths;
import java.util.List;

public interface OperationSystem {

    SystemInformation getInformation();

    Paths getMainPath();

    Paths getResourcePath();

    Paths getLibsPath();

    List<Dependency> getDependencies();

    SoundController getSoundController();



}
