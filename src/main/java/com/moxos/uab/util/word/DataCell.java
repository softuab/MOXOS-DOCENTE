package com.moxos.uab.util.word;

public class DataCell {

    private Configuration config;
    private String data;

    public DataCell(Configuration config, String data) {
        this.config = config;
        this.data = data;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
