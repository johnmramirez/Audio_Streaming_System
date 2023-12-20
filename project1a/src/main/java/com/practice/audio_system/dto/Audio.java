package com.practice.audio_system.dto;

import java.io.IOException;
import java.io.InputStream;

public class Audio {
    private String title;
    private InputStream inputStream;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] getStream() throws IOException {
        return this.inputStream.readAllBytes();
    }
}
