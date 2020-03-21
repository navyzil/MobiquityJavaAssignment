package com.mobiquity.utils;

import com.mobiquity.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * FileReader - Utility class for reading the Package information in the file
 * and converts it into a List of String that will be used by {@link com.mobiquity.packer.helper.PackageProcessor}
 */
public class FileReader {
    private static Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    private FileReader() {
    }

    /**
     * Converts the information in the file to a List of Strings that will be used by {@link com.mobiquity.packer.helper.PackageProcessor}
     * @param filePath - filepath of the file that contains Package information
     * @return List of Strings - The information in the file is converted and divided into List of Strings
     * @throws APIException - If the file is not found or unable to process the file then an {@link APIException} is thrown
     */
    public static List<String> processFile(String filePath) throws APIException {
        List<String> contentOfFileByLine = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            LOGGER.info("Processing file:{}", filePath);
            String line;
            while ((line = br.readLine()) != null) {
                contentOfFileByLine.add(line);
            }

        } catch (IOException e) {
            LOGGER.error("Unable to process file {} :", filePath, e);
            throw new APIException("Unable to process file:"+filePath, e);
        }
        return contentOfFileByLine;
    }
}
