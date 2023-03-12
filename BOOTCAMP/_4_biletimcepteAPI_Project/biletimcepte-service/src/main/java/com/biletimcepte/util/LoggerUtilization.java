package com.biletimcepte.util;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@NoArgsConstructor
public class LoggerUtilization {
    private static Logger logger;
    private static FileHandler fileHandler;
    private static SimpleFormatter formatter;

    public static Logger getLogger() {
        if (formatter == null) {
            formatter = new SimpleFormatter();
        }

        if (fileHandler == null) {
            try {
                fileHandler = new FileHandler("finalProject.log", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            fileHandler.setFormatter(formatter);
        }

        if (logger == null) {
            logger = Logger.getLogger("LoggerUtil");
            logger.addHandler(fileHandler);
        }

        return logger;
    }
}
