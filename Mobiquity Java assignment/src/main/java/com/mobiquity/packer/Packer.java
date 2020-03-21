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

/**
 * @author Denzil Gideon M. Daulo
 * Packer - The main api class for processing Package Information from file and converting it into String
 * The convertes String will be used for other purposes
 */
public class Packer {
    private static Logger LOGGER = LoggerFactory.getLogger(Packer.class);

    private Packer() {
    }

    /**
     * Converts package information from the file to String
     * This will give the total number of packages and the index number of each package
     * @param filePath - File path of the package information stored in file
     * @return String - The total number of packages and the index number of each package
     * @throws APIException - Throws APIException if the package or items doesn't pass the validation
     */
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

    /**
     * Validates each Packages and each of its items
     * @param packageList - List of Packages
     * @throws APIException - Throws APIException if the package or items doesn't pass the validation
     */
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
