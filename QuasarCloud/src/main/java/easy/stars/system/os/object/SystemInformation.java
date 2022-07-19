package easy.stars.system.os.object;

import easy.stars.system.language.I18n;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public abstract class SystemInformation {

    public static final OperatingSystem SOFTWARE = new SystemInfo().getOperatingSystem();
    public static final HardwareAbstractionLayer HARDWARE = new SystemInfo().getHardware();

    public String getInfo() {
        return I18n.Messages.OS_INFO.getMessage()
                .formatted(
                        System.getProperty("user.name"),

                        SOFTWARE.getFamily(),
                        SOFTWARE.getVersionInfo().getVersion(),
                        SOFTWARE.getVersionInfo().getBuildNumber(),
                        SOFTWARE.getBitness(),

                        SOFTWARE.getSystemUptime(),

                        SOFTWARE.getThreadCount(),

                        HARDWARE.getMemory().getTotal(),
                        HARDWARE.getMemory().getAvailable(),

                        HARDWARE.getDisplays().size(),
                        HARDWARE.getProcessor().getProcessorIdentifier().getFamily(),
                        HARDWARE.getProcessor().getProcessorIdentifier().getModel(),
                        HARDWARE.getProcessor().getProcessorIdentifier().getVendor(),
                        HARDWARE.getProcessor().getProcessorIdentifier().getName()
                );
    }

    public String getFullInfo() {
        return I18n.Messages.OS_FULL_INFO.getMessage()
                .formatted(
                        SOFTWARE.getFamily(),
                        SOFTWARE.getVersionInfo().getVersion(),
                        SOFTWARE.getVersionInfo().getBuildNumber(),
                        SOFTWARE.getVersionInfo().getCodeName(),
                        SOFTWARE.getBitness()
                );
    }
}
