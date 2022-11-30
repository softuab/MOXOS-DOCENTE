/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.util;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.apache.tomcat.util.codec.binary.Base64;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ResultFileBase64;

/**
 *
 * @author FNZABALETAA
 */
public class Util {

    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }

    public static String EsImagenModificado(String value, String comparar) {
        String valorretorno = "";
        if (value.contains(":")) {
            if (comparar.equals("image.png")) {
                valorretorno = value;
            } else {
                valorretorno = comparar;
            }
        } else {
            if (value.equals(comparar)) {
                valorretorno = value;
            } else {
                valorretorno = comparar;
            }
        }
        return valorretorno;
    }

    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static ResultFileBase64 Imagen64(String value, String Directorio) throws IOException {
        String imagen = "";
        String scontentype = "";
        String rootPath = "";
        if (value.contains(";")) {
            String[] str = value.split(";");
            scontentype = str[0];
            rootPath = Directorio + File.separator + str[1];
        } else {
            scontentype = "image/png";
            rootPath = Directorio + File.separator + value;
        }
        if (scontentype.equals("application/pdf")) {
            File file = new File(rootPath);
            byte[] bytes = Files.readAllBytes(file.toPath());
            imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(bytes);
        } else {
            File fnew = new File(rootPath);
            BufferedImage originalImage = ImageIO.read(fnew);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, scontentype.replaceAll("image/", "").replace("application/", ""), baos);
            byte[] imageInByte = baos.toByteArray();
            imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(imageInByte);
        }
        ResultFileBase64 base = new ResultFileBase64();
        base.setBase64(imagen);
        base.setContentType(scontentype);
        return base;
    }

    public static Image GetImagenPerfil(int id_docente, MiFacade mi) throws BadElementException, IOException {
        int iId_docente = id_docente;
        Image img = null;
        Docentes docente = new Docentes();
        docente.setId_docente(iId_docente);
        List<Docentes> docentes = mi.getDetallefotoadjunto(docente);
        if (!docentes.isEmpty()) {
            Docentes fotoadjunto = docentes.stream().findFirst().get();
            String rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + fotoadjunto.getNombre_archivo();
            File fnew = new File(rootPath);
            img = Image.getInstance(fnew.getAbsolutePath());
            img.scaleToFit(50, 50);
        } else {
            String rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + "nulo.png";
            File fnew = new File(rootPath);
            img = Image.getInstance(fnew.getAbsolutePath());
            img.scaleToFit(50, 50);
        }
        return img;
    }

    public static String Imagen64(String value, String Directorio, int w, int h) throws IOException {
        String imagen = "";
        String scontentype = "";
        String rootPath = "";
        if (value.contains(";")) {
            String[] str = value.split(";");
            scontentype = str[0];
            rootPath = Directorio + File.separator + str[1];
        } else {
            scontentype = "image/png";
            rootPath = Directorio + File.separator + value;
        }

        File fnew = new File(rootPath);
        BufferedImage originalImage = ImageIO.read(fnew);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resize(originalImage, w, h), scontentype.replaceAll("image/", ""), baos);
        byte[] imageInByte = baos.toByteArray();
        imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(imageInByte);

        return imagen;
    }

    public static void ComprimirImagen(String src, String dest) throws IOException {
        File input = new File(src);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(dest);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.05f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

   
}
