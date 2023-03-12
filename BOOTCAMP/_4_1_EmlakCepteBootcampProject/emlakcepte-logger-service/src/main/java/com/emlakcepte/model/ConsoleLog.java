package com.emlakcepte.model;

import com.emlakcepte.model.enums.LogType;

import java.time.LocalDateTime;

public class ConsoleLog extends Log {

    public ConsoleLog(String logMessage, LocalDateTime logTime) {
        this.logMessage = logMessage;
        this.logTime = logTime;
        this.logType = LogType.CONSOLE_LOG;
    }
}
