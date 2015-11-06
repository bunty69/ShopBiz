package com.purefaithstudio.shopbiz;

import java.io.Serializable;

/**
 * Created by MY System on 11/6/2015.
 */
public class UserSaveData implements Serializable {
    String username;
    byte bytes[];

    public UserSaveData(String username, byte bytes[]) {
        this.username = username;
        this.bytes = bytes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte bytes[]) {
        this.bytes = bytes;
    }
}
