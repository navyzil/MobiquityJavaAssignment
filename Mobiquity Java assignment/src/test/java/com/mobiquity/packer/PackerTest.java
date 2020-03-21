package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Denzil Gideon M. Daulo
 * Integration Test for Packer Class
 */
public class PackerTest {
    private URI filePathUri;
    private URI filePathUri2;
    private URI filePathUri3;
    private URI filePathUri4;
    private URI filePathUri5;
    private ClassLoader classLoader;

    @BeforeEach
    public void setup() {
        classLoader = ClassLoader.getSystemClassLoader();
        try {
            filePathUri = classLoader.getResource("example_input").toURI();
            filePathUri2 = classLoader.getResource("example_input_invalid_package_weight").toURI();
            filePathUri3 = classLoader.getResource("example_input_invalid_package_item_count").toURI();
            filePathUri4 = classLoader.getResource("example_input_invalid_item_weight").toURI();
            filePathUri5 = classLoader.getResource("example_input_invalid_item_cost").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldPrintPackageContentsFromInputFile() {
        String filePath = Paths.get(filePathUri).toString();
        String expectedOutput = "4\n" +
                "-\n" +
                "1,2,3,4,5,6\n" +
                "1\n" +
                "1,2,3,4,5,6,7,8,9\n" +
                "1,2,3,4,5,6,7,8,9\n";

        assertDoesNotThrow(() -> {
            String packInfo = Packer.pack(filePath);
            assertTrue(packInfo.equals(expectedOutput));
        });
    }

    @Test
    public void shouldThrowIfExceededPackageWeight() {
        String filePath = Paths.get(filePathUri2).toString();
      String containsExpectedMessage= "It has exceeded the 100 weight limit";

        Throwable exception = assertThrows(APIException.class, () -> {
            Packer.pack(filePath);
        });
        Assertions.assertTrue(exception.getMessage().contains(containsExpectedMessage));

    }

    @Test
    public void shouldThrowIfPackageExceededItemCount() {
        String filePath = Paths.get(filePathUri3).toString();
      String containsExpectedMessage= "It has exceeded the 15 items limit";

        Throwable exception = assertThrows(APIException.class, () -> {
            Packer.pack(filePath);
        });
        Assertions.assertTrue(exception.getMessage().contains(containsExpectedMessage));

    }

    @Test
    public void shouldThrowIfItemExceededWeight() {
        String filePath = Paths.get(filePathUri4).toString();
      String containsExpectedMessage= "It has exceeded the 100 weight limit";

        Throwable exception = assertThrows(APIException.class, () -> {
            Packer.pack(filePath);
        });
        Assertions.assertTrue(exception.getMessage().contains(containsExpectedMessage));

    }

    @Test
    public void shouldThrowIfItemExceededCost() {
        String filePath = Paths.get(filePathUri5).toString();
      String containsExpectedMessage= "It has exceeded the 100 EUR price limit";

        Throwable exception = assertThrows(APIException.class, () -> {
            Packer.pack(filePath);
        });
        Assertions.assertTrue(exception.getMessage().contains(containsExpectedMessage));

    }
}
