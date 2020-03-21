package com.mobiquity.utils;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * InformationWriter - Utility class for writing the processed Package and Item information
 */
public class InformationWriter {
    private static Logger LOGGER = LoggerFactory.getLogger(InformationWriter.class);

    private InformationWriter() {
    }

    /**
     * Prints the Packages Information
     * @param packageList - List of Packages
     * @return String - Information regarding the number of packages and the indexes of each item of each package
     */
    public static String printPackageInfo(List<Package> packageList) {
        LOGGER.info("Writing Package Information");

        StringBuilder packageInfo = new StringBuilder();
        int numberOfPackages = packageList.size();
        packageInfo.append(numberOfPackages)
                .append("\n")
                .append("-")
                .append("\n");
        packageList.forEach(pckage -> {
            List<Item> itemList = pckage.getItemList();
            writeItemInfo(packageInfo, itemList);
            packageInfo.append("\n");
        });
        return packageInfo.toString();
    }

    /**
     * A specialized writer for Items
     * @param packageInfo - StringBuilder instance that contains the information of the package
     * @param itemList - List of Items in the Package
     */
    private static void writeItemInfo(StringBuilder packageInfo, List<Item> itemList) {
        LOGGER.info("Writing Items Information");
        for (int itemIndex = 0; itemIndex < itemList.size(); itemIndex++) {
            Item item = itemList.get(itemIndex);
            packageInfo.append(item.getIndexNumber());
            if (itemIndex != itemList.size() - 1) {
                packageInfo.append(",");
            }
        }
    }
}
