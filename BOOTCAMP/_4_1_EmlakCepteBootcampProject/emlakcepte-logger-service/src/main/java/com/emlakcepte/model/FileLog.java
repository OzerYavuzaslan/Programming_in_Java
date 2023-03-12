package com.emlakcepte.model;

import com.emlakcepte.model.enums.LogType;

import java.time.LocalDateTime;

public class FileLog extends Log{

    public FileLog(String logMessage, LocalDateTime logTime) {
        this.logMessage = logMessage;
        this.logTime = logTime;
        this.logType = LogType.FILE_LOG;
    }
}
