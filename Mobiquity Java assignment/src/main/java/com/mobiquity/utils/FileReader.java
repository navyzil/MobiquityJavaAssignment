package com.mobiquity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    private FileReader() {
    }

    public static List<String> processFile(String filePath) {
        List<String> contentOfFileByLine = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            LOGGER.info("Processing file:{}", filePath);
            String line;
            while ((line = br.readLine()) != null) {
                contentOfFileByLine.add(line);
            }

        } catch (IOException e) {
            LOGGER.error("Unable to process file {} :", filePath, e);
        }
        return contentOfFileByLine;
    }
}
