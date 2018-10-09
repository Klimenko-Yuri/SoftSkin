package com.skinexpert.controller.parser;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import com.skinexpert.util.Property;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mihail Kolomiets on 8/24/18.
 */
@WebServlet(urlPatterns = "/parse")
@MultipartConfig
public class ParsePhoto extends HttpServlet {

    ComponentService service;
    //10Mb
    private final Integer FILEMAXSIZE = 10000000;
    private String outMessage;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");

        service = new ComponentService();
        Part filePart = req.getPart("photo");

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(Property.TESSDATA);
        tesseract.setLanguage("rus");

        InputStream is = filePart.getInputStream();
        int fileSize = is.available();
        System.out.println(fileSize);
        if (fileSize > FILEMAXSIZE) {
            outMessage = new Gson().toJson("file so big");
        } else {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            String fileName = Paths.get(getSubmittedFileName(filePart)).getFileName().toString();
            File targetFile = new File(Property.TESSDATA + "/tessdata/img/" + fileName);

            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);

            String parseResult = "";

            try {
                parseResult = tesseract.doOCR(targetFile);
            } catch (TesseractException e) {
                for (StackTraceElement ste : e.getStackTrace()) {
                    parseResult += ste + "\n";
                }
            } finally {
                targetFile.delete();
            }

            System.out.println(parseResult);

            Set<Component> contain = findInBase(parseResult);

            outMessage = new Gson().toJson(contain);
        }
        resp.getWriter().write(outMessage);
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    /**
     * Find from text all components from base and put it in list
     */
    private Set<Component> findInBase(String parseText) {
        HashSet<Component> result = new HashSet<>();
        List<Component> all = service.getAll();

        String name;
        parseText = parseText.toUpperCase();

        for (int textPosition = 0; textPosition < parseText.length(); textPosition++) {

            for (Component component : all) {
                name = component.getName();
                name = name.toUpperCase();

                if (name.length() <= parseText.length() - textPosition
                        && name.charAt(0) == parseText.charAt(textPosition)             //now find begin
                        & name.equals(parseText.substring(textPosition, textPosition + name.length()))) {

                    result.add(component);
                    textPosition += name.length();
                }
            }
        }

        return result;
    }
}
