package io.github.agorshkov23.mbtiles.server.controller;

import io.github.agorshkov23.mbtiles.server.config.MBTilesServerConfiguration;
import org.imintel.mbtiles4j.MBTilesReadException;
import org.imintel.mbtiles4j.MBTilesReader;
import org.imintel.mbtiles4j.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("tile")
@ConditionalOnBean(MBTilesServerConfiguration.class)
public class MBTilesTileController {

    private static final Logger LOG = LoggerFactory.getLogger(MBTilesTileController.class);

    private final MBTilesReader mbTilesReader;

    @Autowired
    public MBTilesTileController(MBTilesReader mbTilesReader) {
        this.mbTilesReader = mbTilesReader;
    }

    @GetMapping("{zoom}/{x}/{y}.png")
    public ResponseEntity getTile(@PathVariable int x,
                                  @PathVariable int y,
                                  @PathVariable int zoom) {
        try {
            Tile tile = mbTilesReader.getTile(zoom, x, y);
            int size = tile.getData().available();
            byte[] bytes = new byte[size];
            tile.getData().read(bytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/png");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(bytes);
        } catch (MBTilesReadException e) {
            LOG.info(e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("The tile with the parameters x=%d, y=%d & zoom=%d not found", x, y, zoom));
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error");
        }
    }
}
