package com.vivern.arpg.util.ci;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

public final class StartupMarker {

    private static final String ENV = "MOD_STARTED_MARKER";

    private StartupMarker() {}

    public static void writeIfRequested(Logger logger) {
        String value = System.getenv(ENV);
        if (value == null || value.isEmpty()) return;

        try {
            Path marker = Paths.get(value).toAbsolutePath();
            Files.createDirectories(marker.getParent());
            Files.write(marker, "started".getBytes(StandardCharsets.UTF_8));
            logger.info("CI startup marker written: {}", marker);
        } catch (IOException e) {
            logger.error("Failed to write CI startup marker", e);
        }
    }
}