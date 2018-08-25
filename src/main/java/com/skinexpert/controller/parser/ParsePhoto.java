package com.skinexpert.controller.parser;

import com.google.gson.Gson;
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

/**
 * Created by Mihail Kolomiets on 8/24/18.
 */
@WebServlet(urlPatterns = "/parse")
@MultipartConfig
public class ParsePhoto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");

        Part filePart = req.getPart("photo");

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(Property.TESSDATA);
        tesseract.setLanguage("rus");

        InputStream is = filePart.getInputStream();

        byte[] buffer = new byte[is.available()];
        is.read(buffer);

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File targetFile = new File(Property.TESSDATA + "/tessdata/img/" + fileName);
       // targetFile.createNewFile();
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        String parseResult;

        try {
             parseResult = tesseract.doOCR(targetFile);
        } catch (TesseractException e) {
            parseResult = e.toString();
        }

        String outMessage = new Gson().toJson(parseResult);
        resp.getWriter().write(outMessage);



    }
}
