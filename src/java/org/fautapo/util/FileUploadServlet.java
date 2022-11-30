/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author FNZABALETAA
 */
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
