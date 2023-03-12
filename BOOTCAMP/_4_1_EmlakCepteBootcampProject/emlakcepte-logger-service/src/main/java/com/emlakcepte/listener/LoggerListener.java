package com.emlakcepte.listener;

import com.emlakcepte.model.FileLog;
import com.emlakcepte.model.Log;
import com.emlakcepte.model.enums.LogType;
import com.emlakcepte.repository.LogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class LoggerListener
{

    private static final Logger logger = Logger.getLogger(FileLog.class.getName());
    FileHandler fh;

    private final LogRepository logRepository;

    public LoggerListener(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @RabbitListener(queues = "${emlakcepte.rabbitmq.logQueue}")
    public void logListener(Log log) {
        System.out.println(log);
        if(log.getLogType().equals(LogType.CONSOLE_LOG))
        {
            logger.info(log.toString());
            System.out.println(log);
        }
        else if(log.getLogType().equals(LogType.DB_LOG)) {
            logRepository.save(log);
        }
        else {
            try {
                // This block configure the logger with handler and formatter
                fh = new FileHandler("EmlakCepteLogs.log",true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                // the following statement is used to log any messages
                logger.info(log.toString());

            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }



}
