package com.emlakcepte.model;

import com.emlakcepte.model.enums.LogType;

import java.time.LocalDateTime;

public class DbLog extends Log{

    public DbLog(String logMessage, LocalDateTime logTime) {
        this.logMessage = logMessage;
        this.logTime = logTime;
        this.logType = LogType.DB_LOG;
    }


}
