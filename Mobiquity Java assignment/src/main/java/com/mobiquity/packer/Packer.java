package com.mobiquity.packer;

import com.mobiquity.entities.Package;
import com.mobiquity.exception.APIException;
import com.mobiquity.packer.helper.PackageProcessor;
import com.mobiquity.packer.helper.PackageValidator;
import com.mobiquity.utils.FileReader;
import com.mobiquity.utils.InformationWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Packer {
    private static Logger LOGGER = LoggerFactory.getLogger(Packer.class);

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        LOGGER.info("Processing Package Information from file:{}", filePath);

        List<String> listOfContents = FileReader.processFile(filePath);
        List<Package> packageList = PackageProcessor.getInstance()
                .processPackageInfoFromFile(listOfContents);
        validatePackages(packageList);
        String packageInfo = InformationWriter.printPackageInfo(packageList);

        LOGGER.info("Package Information from file:\n{}", packageInfo);
        return packageInfo;
    }

    private static void validatePackages(List<Package> packageList) throws APIException {
        PackageValidator packageValidator = PackageValidator.getInstance();
        for (Package pckage : packageList) {
            LOGGER.info("Validating Package:{} Information", pckage);

            packageValidator.validatePackageNumberOfItems(pckage);
            packageValidator.validatePackageMaxWeight(pckage);
            packageValidator.validateItemsMaxWeightAndPrice(pckage.getItemList());
        }
    }
}
