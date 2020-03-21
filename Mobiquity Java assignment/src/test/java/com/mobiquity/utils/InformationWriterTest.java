package com.mobiquity.utils;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import com.mobiquity.packer.helper.PackageValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * Test for InformationWriter Utility Class
 */
public class InformationWriterTest {
    private List<Package> packageList;

    @BeforeEach
    public void setup() {
        packageList = new ArrayList<>();
        for (int packageIndex = 0; packageIndex < 4; packageIndex++) {
            Package pckage = new Package();
            pckage.setWeight(80);

            List<Item> itemList = new ArrayList<>();
            for (int itemIndex = 0; itemIndex < 15; itemIndex++) {
                Item item = new Item();
                item.setIndexNumber(itemIndex + 1);
                item.setWeight(100);
                item.setPrice("90");

                itemList.add(item);
            }
            pckage.setItemList(itemList);
            packageList.add(pckage);
        }
    }

    @Test
    public void shouldReadFilesAndReturnLinesAsList() {
        String expectedValue="4\n" +
                "-\n" +
                "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15\n" +
                "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15\n" +
                "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15\n" +
                "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15\n";
        String printPackageInfo = InformationWriter.printPackageInfo(packageList);
        Assertions.assertNotNull(printPackageInfo);
        Assertions.assertTrue(printPackageInfo.equals(expectedValue));
    }
}
