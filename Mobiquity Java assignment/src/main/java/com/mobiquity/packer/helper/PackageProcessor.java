package com.mobiquity.packer.helper;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageProcessor {
    private static Logger LOGGER = LoggerFactory.getLogger(PackageProcessor.class);

    private static PackageProcessor instance;

    private PackageProcessor() {
    }

    public static PackageProcessor getInstance() {
        LOGGER.info("Initializing PackageProcessor");

        if(instance==null){
            instance = new PackageProcessor();
        }
        return instance;
    }

    public List<Package> processPackageInfoFromFile(List<String> packagesFromFile) {
        LOGGER.info("Processing packages information");

        List<Package> packageList = new ArrayList<>();
        packagesFromFile.forEach(packageFromFile -> {
            Package pckage = new Package();
            String[] splitPackageWeightToItem = packageFromFile.split(":");
            Integer packageWeight = Integer.valueOf(splitPackageWeightToItem[0].trim());
            String packageItems = splitPackageWeightToItem[1].trim();
            pckage.setWeight(packageWeight);

            processPackageItems(pckage, packageItems);
            packageList.add(pckage);
        });
        return packageList;
    }

    private void processPackageItems(Package pckage, String packageItems) {
        LOGGER.info("Processing item information");

        List<String> groupItemList = groupItems(packageItems);
        List<Item> itemList = new ArrayList<>();
        groupItemList.forEach(groupItem -> {
            String[] itemSplitToSpecs = groupItem.split(",");
            Integer itemIndex = Integer.valueOf(itemSplitToSpecs[0].trim());
            Double itemWeight = Double.valueOf(itemSplitToSpecs[1].trim());
            String itemPrice = fixItemPriceFormat(itemSplitToSpecs[2].trim());

            Item item = new Item();
            item.setIndexNumber(itemIndex);
            item.setWeight(itemWeight);
            item.setPrice(itemPrice);
            itemList.add(item);
        });
        pckage.setItemList(itemList);
    }

    private String fixItemPriceFormat(String itemPrice) {
        LOGGER.info("Fixing item price format");

        String itemPriceNewFormat = itemPrice.replaceAll("[^\\d.]+", "");
        Double itemPriceAsDouble = Double.valueOf(itemPriceNewFormat);
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
        numberFormat.setCurrency(Currency.getInstance("EUR"));

        return numberFormat.format(itemPriceAsDouble);
    }

    private List<String> groupItems(String packageItems) {
        List<String> itemGroupList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(packageItems);
        int itemGroupIndex = 0;
        while (matcher.find()) {
            String itemGroup = matcher.group(1);
            itemGroupList.add(itemGroup);
            itemGroupIndex++;
        }
        return itemGroupList;
    }
}
