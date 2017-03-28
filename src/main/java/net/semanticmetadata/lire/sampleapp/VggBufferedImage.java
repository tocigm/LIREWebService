package net.semanticmetadata.lire.sampleapp;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * Created by SONY on 3/29/2017.
 */
public class VggBufferedImage extends BufferedImage {

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public VggBufferedImage(String filename, BufferedImage image, Hashtable<String,Object> properties) {
        super(image.getColorModel(), image.getRaster(), image.isAlphaPremultiplied(), properties);
        this.fileName = filename;
    }


}
