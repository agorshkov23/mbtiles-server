package io.github.agorshkov23.mbtiles.server.config;

import io.github.agorshkov23.mbtiles.server.config.property.MBTilesServerProperties;
import org.imintel.mbtiles4j.MBTilesReadException;
import org.imintel.mbtiles4j.MBTilesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConditionalOnProperty(prefix = "mbtiles.server", name = "active", havingValue = "true")
public class MBTilesServerConfiguration {

    @Bean
    public MBTilesServerProperties mbTilesServerProperties() {
        MBTilesServerProperties mbTilesServerProperties = new MBTilesServerProperties();
        return mbTilesServerProperties;
    }

    @Bean
    public File file(@Autowired MBTilesServerProperties properties) {
        File file = new File(properties.getPath());
        return file;
    }

    @Bean
    public MBTilesReader mbTilesReader(@Autowired File file) throws MBTilesReadException {
        MBTilesReader mbTilesReader = new MBTilesReader(file);
        return mbTilesReader;
    }
}
