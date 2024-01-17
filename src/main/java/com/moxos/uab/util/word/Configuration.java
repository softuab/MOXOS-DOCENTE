package com.moxos.uab.util.word;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

public class Configuration {

    private ParagraphAlignment aligment;
    private boolean bold;
    private String fontfamily;
    private int size;
    private Borders border;

    public String getFontfamily() {
        return fontfamily;
    }

    public void setFontfamily(String fontfamily) {
        this.fontfamily = fontfamily;
    }

    public Borders getBorder() {
        return border;
    }

    public void setBorder(Borders border) {
        this.border = border;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public ParagraphAlignment getAligment() {
        return aligment;
    }

    public void setAligment(ParagraphAlignment aligment) {
        this.aligment = aligment;
    }

}
