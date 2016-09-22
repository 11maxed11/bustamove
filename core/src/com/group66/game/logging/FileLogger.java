package com.group66.game.logging;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileLogger extends Logger {
    private static FileHandle handle;

    /**
     * Constructor for a file logger
     * @param mt the message type to write
     */
    public FileLogger(MessageType mt){
        String date = DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        try {
            handle = Gdx.files.external("log_" + date + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.type = mt;
    }

    @Override
    protected void write(String message) {
        try {
            handle.writeString(message + System.getProperty("line.separator"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
