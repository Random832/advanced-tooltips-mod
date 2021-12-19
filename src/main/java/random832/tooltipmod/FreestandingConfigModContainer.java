package random832.tooltipmod;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FreestandingConfigModContainer  extends ModContainer {
    public FreestandingConfigModContainer(String modid) {
        super(new FakeModInfo(modid));
    }

    static class FakeModInfo implements IModInfo {
        private final String modid;

        public FakeModInfo(String modid) {
            this.modid = modid;
        }

        @Override
        public IModFileInfo getOwningFile() {
            return null;
        }

        @Override
        public String getModId() {
            return modid;
        }

        @Override
        public String getDisplayName() {
            return null;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public ArtifactVersion getVersion() {
            return null;
        }

        @Override
        public List<? extends ModVersion> getDependencies() {
            return null;
        }

        @Override
        public String getNamespace() {
            return null;
        }

        @Override
        public Map<String, Object> getModProperties() {
            return null;
        }

        @Override
        public Optional<URL> getUpdateURL() {
            return Optional.empty();
        }

        @Override
        public Optional<String> getLogoFile() {
            return Optional.empty();
        }

        @Override
        public boolean getLogoBlur() {
            return false;
        }

        @Override
        public IConfigurable getConfig() {
            return null;
        }
    }

    @Override
    public boolean matches(Object mod) {
        return false;
    }

    @Override
    public Object getMod() {
        return null;
    }
}
