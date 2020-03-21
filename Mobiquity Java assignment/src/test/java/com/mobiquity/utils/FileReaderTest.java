package com.mobiquity.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderTest {
    private URI filePathUri;
    private ClassLoader classLoader;

    @BeforeEach
    public void setup() {
        classLoader = ClassLoader.getSystemClassLoader();
        try {
            filePathUri = classLoader.getResource("example_input").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReadFilesAndReturnLinesAsList() {
        String filePath = Paths.get(filePathUri).toString();
        List<String> listOfContents = FileReader.processFile(filePath);
        Assertions.assertFalse(listOfContents.isEmpty());
    }
}
