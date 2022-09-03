package easy.stars.system.os.object;

import easy.stars.system.os.interfaces.OperationSystem;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MacOS extends OS {

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
        createResourcePath();
        createLibsPath();
    }

    @Override
    public Path getResourceByName(String name) {
        return null;
    }

    @Override
    public Path getLibByName(String name) {
        return null;
    }

    @Override
    public List<Dependency> getDependencies() {
        return null;
    }

    @Override
    public void loadAllDependencies() {

    }

    @Override
    public void setVolumeInPercent(int volume) {

    }

    @Override
    public void setVolume(int volume) {

    }

    @Override
    public void incVolume(int volume) {

    }

    @Override
    public void decVolume(int volume) {

    }

    @Override
    public void incVolumeInPercent(int volume) {

    }

    @Override
    public void decVolumeInPercent(int volume) {

    }
}
