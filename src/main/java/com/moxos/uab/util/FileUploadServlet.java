package com.moxos.uab.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadServlet {

    public static void MultipartRequest(HttpServletRequest request, MultipartFile multipartFile, String saveDirectory, String field, String fileName) {
        if (multipartFile != null || !multipartFile.isEmpty()) {
            if (!multipartFile.getOriginalFilename().equals("")) {
                try {
                    String content = multipartFile.getContentType().replace("image/", "").replace("application/", "");
                    String filePath = saveDirectory + File.separator + fileName + "." + content;
                    Files.copy(multipartFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                    request.setAttribute(field, multipartFile.getContentType() + ";" + fileName + "." + content);
                } catch (IOException ex) {
                    request.setAttribute(field, "image.png");
                }
            } else {
                request.setAttribute(field, "image.png");
            }
        } else {
            request.setAttribute(field, "image.png");
        }
    }
}
