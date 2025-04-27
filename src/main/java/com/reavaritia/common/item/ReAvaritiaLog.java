package com.reavaritia.common.item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReAvaritiaLog {

    public static final Logger logger = LogManager.getLogger("GTNotLeisure");

    public static void log(Level level, Throwable e, Object message) {
        log(level, message);
        e.printStackTrace();
    }

    public static void log(Level level, Object message) {
        logger.log(level, message);
    }

    public static void info(Object message) {
        log(Level.INFO, message);
    }
}
