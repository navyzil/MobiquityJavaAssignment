package com.mobiquity.packer.helper;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import com.mobiquity.packer.helper.PackageProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * Test for PackageProcessor Helper Class
 */
public class PackageProcessorTest {
    private List<String> packagesAsStringList;

    @BeforeEach
    public void setup() {
        String packageAsString1 = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        String packageAsString2 = "8 : (1,15.3,€34)";
        String packageAsString3 = "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        String packageAsString4 = "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";

        packagesAsStringList = new ArrayList<>();
        packagesAsStringList.add(packageAsString1);
        packagesAsStringList.add(packageAsString2);
        packagesAsStringList.add(packageAsString3);
        packagesAsStringList.add(packageAsString4);
    }

    @Test
    public void shouldProcessStringListToPackageList() {
        PackageProcessor packageProcessor = PackageProcessor.getInstance();

        List<Package> packages = packageProcessor.processPackageInfoFromFile(packagesAsStringList);
        Assertions.assertFalse(packages.isEmpty());
        Package pckage = packages.get(0);
        Assertions.assertNotNull(pckage);
        Assertions.assertTrue(pckage.getWeight() == 81);
        Assertions.assertTrue(pckage.getItemList().size() == 6);

        Item item = pckage.getItemList().get(0);
        Assertions.assertTrue(item.getWeight() == 53.38);
    }
}
