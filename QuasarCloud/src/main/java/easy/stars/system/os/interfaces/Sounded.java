package easy.stars.system.os.interfaces;

public interface Sounded {

    void setVolumeInPercent(int volume);

    void setVolume(int volume);

    void incVolume(int volume);

    void decVolume(int volume);

    void incVolumeInPercent(int volume);

    void decVolumeInPercent(int volume);

}
