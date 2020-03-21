package com.mobiquity.utils;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InformationWriter {
    private static Logger LOGGER = LoggerFactory.getLogger(InformationWriter.class);

    private InformationWriter() {
    }

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
