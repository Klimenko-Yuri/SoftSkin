package com.skinexpert.controller.parser;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.dao.impl.HibernateComponentDaoImpl;
import com.skinexpert.service.ComponentService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
    public static final String TESSERACT_LIB_PATH = "/home/kosmetika";
    private static ComponentService hibernateComponentDaoImpl = ComponentService.getInstance();
    private Logger logger;

    //10Mb
    private final Integer FILEMAXSIZE = 10000000;
    private String outMessage;

    @Override
    public void init() {
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json; charset = utf8");

        Part filePart;
        try {
            filePart = req.getPart("photo");
        } catch (ServletException | IOException e) {
            logger.error("Exception while filePart getting", e);
            throw new RuntimeException(e);
        }

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(TESSERACT_LIB_PATH);
        tesseract.setLanguage("rus");

        String fileName = Paths.get(getSubmittedFileName(filePart)).getFileName().toString();
        File targetFile = new File(TESSERACT_LIB_PATH + "/tessdata/img/" + fileName);

        if (!targetFile.exists()) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(filePart.getInputStream());
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile))) {

                int fileSize = bufferedInputStream.available();
                logger.debug("File size: " + fileSize);

                // mini validation // todo with validator
                if (fileSize > FILEMAXSIZE) {
                    outMessage = new Gson().toJson("file so big");
                } else if (fileSize == 0) {
                    outMessage = new Gson().toJson("where file issue?");
                } else if (fileName.length() == 0) {
                    outMessage = new Gson().toJson("it's realy file without name?");
                } else {

                    String parseResult = "";

                    try {
                        bufferedOutputStream.write(bufferedInputStream.read());
                        parseResult = tesseract.doOCR(targetFile);
                    } catch (TesseractException e) {
                        logger.error("Tesseract library exception", e);
                    } finally {
                        targetFile.delete();
                    }

                    logger.debug("Parse result " + parseResult);

                    Set<Component> contain = findInBase(parseResult);
                    outMessage = new Gson().toJson(contain);
                }
                resp.getWriter().write(outMessage);
            } catch (IOException e) {
                logger.error("Exception while file reading", e);
            }
        } else {
            System.out.println("file is exist");
        }
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return "";
    }

    /**
     * Find from text all components from base and put it in list
     */
    private Set<Component> findInBase(String parseText) {
        HashSet<Component> result = new HashSet<>();
        List<Component> all = hibernateComponentDaoImpl.getAll();

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
