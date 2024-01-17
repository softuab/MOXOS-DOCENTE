package com.moxos.uab.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.moxos.uab.model.models.utility.ResultFileBase64;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
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

    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
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

    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static Image getImagenPerfil(int id_docente, MiFacade mi) throws BadElementException, IOException {
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

    public static String generateCaptchaTextMethod2(int captchaLength) {

        String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer captchaStrBuffer = new StringBuffer();
        java.util.Random rnd = new java.util.Random();
        while (captchaStrBuffer.length() < captchaLength) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            captchaStrBuffer.append(saltChars.substring(index, index + 1));
        }
        return captchaStrBuffer.toString();

    }

    public static String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder capitalizedText = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                char firstChar = Character.toUpperCase(word.charAt(0));
                String restOfWord = word.substring(1).toLowerCase();
                capitalizedText.append(firstChar).append(restOfWord).append(" ");
            }
        }

        return capitalizedText.toString().trim();
    }

    public static String capitalizeString(String text) {
        if (text.isEmpty() || text.isBlank())
            return "";
        return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
    }

    public static String QrEncoder(String texto) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;

        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 200, 200, hints);
        } catch (WriterException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
            return "";
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] qrBytes = outputStream.toByteArray();
            String base64QR = Base64.encodeBase64String(qrBytes);
            return base64QR;
        } catch (IOException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
            return "";
        }
    }
}
