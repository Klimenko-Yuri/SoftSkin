package com.skinexpert.controller.parser;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@WebServlet(urlPatterns = "/parse")
@MultipartConfig
public class ParsePhoto extends HttpServlet {
    public static final String TESSERACT_LIB_PATH = "/home/mihail";
    private static ComponentService hibernateComponentDaoImpl = ComponentService.getInstance();
    private Logger logger;

    //Divide threads during recognizing
    private static final ExecutorService workers = Executors.newCachedThreadPool();

    //10Mb
    private final Integer FILE_MAX_SIZE = 10_048_576;
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

        //recognizing Russian text
        ITesseract tesseractRUS = new Tesseract();
        tesseractRUS.setDatapath(TESSERACT_LIB_PATH);
        tesseractRUS.setLanguage("rus");
        //recognizing English text
        ITesseract tesseractENG = new Tesseract();
        tesseractENG.setDatapath(TESSERACT_LIB_PATH);
        tesseractENG.setLanguage("eng");

        String fileName = Paths.get(getSubmittedFileName(filePart)).getFileName().toString();
        File targetFile = new File(TESSERACT_LIB_PATH + "/tessdata/img/" + fileName);

        if (!targetFile.exists()) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(filePart.getInputStream());
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile))) {

                int fileSize = bufferedInputStream.available();
                logger.debug("File size: " + fileSize);

                // mini validation // todo with validator
                if (fileSize > FILE_MAX_SIZE) {
                    outMessage = new Gson().toJson("file so big");
                } else if (fileSize == 0) {
                    outMessage = new Gson().toJson("where file issue?");
                } else if (fileName.length() == 0) {
                    outMessage = new Gson().toJson("it's realy file without name?");
                } else {

                    byte[] buffer = new byte[fileSize];
                    bufferedInputStream.read(buffer);
                    bufferedOutputStream.write(buffer);

                    //save recognized text
                    List<String> listRecognize = recognizeTest(targetFile, tesseractRUS, tesseractENG);

                    StringBuilder allLanguigeText = new StringBuilder();

                    listRecognize.stream().forEach(allLanguigeText::append);

                    Set<Component> contain = findInBase(allLanguigeText.toString());


                    outMessage = new Gson().toJson(contain);
                }
                resp.getWriter().write(outMessage);
            } catch (IOException e) {
                logger.error("Exception while file reading", e);
            } finally {
                targetFile.delete();
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
     * Find from text all components from base and put it in set
     */
    private Set<Component> findInBase(String parseText) {
        HashSet<Component> result = new HashSet<>();
        List<Component> all = hibernateComponentDaoImpl.getAll();

        for (Component component : all) {
            if(result.contains(component))
                continue;

            if (wordsIsExist(parseText, component.getName(), 5)
                    || wordsIsExist(parseText, component.getNameENG(), 5)) {
                result.add(component);
            }
        }
        return result;
    }


    private List<String> recognizeTest(File targetFile, ITesseract... languages) {
        //save recornized text
        List<String> listRecognize = new ArrayList<>();

        try {
            Collection<Callable<String>> tasks = new ArrayList<>();

            for (ITesseract t : languages) {
                tasks.add(() -> t.doOCR(targetFile));
            }

            List<Future<String>> results = workers.invokeAll(tasks);

            for (Future<String> f : results) {
                listRecognize.add(f.get());
            }
        //} catch (TesseractException e) {
          //  logger.error("Tesseract library exception", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.error("Execution Exception");
        }
        return listRecognize;
    }

    /**
     *
     * @param text
     * @param mismatch - symbols with 1 error (normal 5 for tesseract =)
     * @return
     */
    private boolean wordsIsExist(String text, String word, int mismatch) {
        if (word == null)
            return false;
        text = text.toUpperCase();
        word = word.toUpperCase();
        boolean finded = false;

        int position;

        for (int i = 0; i < text.length() - word.length(); i++) {
            if (finded) {
                break;
            }
            int different = word.length() / mismatch + 1;
            position = i;
            for (int j = 0; j < text.length(); j++) {

                if (word.charAt(j) != text.charAt(position)) {
                    different--;
                }

                if (different < 0)
                    break;

                if (j == text.length() - 1) {
                    finded = true;
                }
                position++;
            }
        }
        return finded;
    }
}
