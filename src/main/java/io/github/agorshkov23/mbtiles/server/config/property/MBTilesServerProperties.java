package io.github.agorshkov23.mbtiles.server.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mbtiles.server")
public class MBTilesServerProperties {

    private boolean active;

    private String path;

    public MBTilesServerProperties() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MBTilesServerProperties{");
        sb.append("active=").append(active);
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
