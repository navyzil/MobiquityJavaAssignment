package com.mobiquity.utils;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * Test for FileReader Utility Class
 */
public class FileReaderTest {
    private URI filePathUri;
    private URI filePathUri2;
    private ClassLoader classLoader;

    @BeforeEach
    public void setup() {
        classLoader = ClassLoader.getSystemClassLoader();
        try {
            filePathUri = classLoader.getResource("example_input").toURI();
            filePathUri2 = classLoader.getResource("example_input").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReadFilesAndReturnLinesAsList() {
        String filePath = Paths.get(filePathUri).toString();
        Assertions.assertDoesNotThrow(() -> {
            List<String> listOfContents = FileReader.processFile(filePath);
            Assertions.assertFalse(listOfContents.isEmpty());
        });
    }

    @Test
    public void shouldThrowAPIExceptionIfFileNotFound() {
        String filePath = Paths.get(filePathUri2).toString().concat("not_found");
        String containsExpectedMessage = "Unable to process file:" + filePath;
        APIException apiException = Assertions.assertThrows(APIException.class, () -> {
            FileReader.processFile(filePath);
        });
        Assertions.assertTrue(apiException.getMessage().contains(containsExpectedMessage));
    }
}
