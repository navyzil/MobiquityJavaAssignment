package com.mobiquity.packer.helper;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import com.mobiquity.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PackageValidator {
    private static Logger LOGGER = LoggerFactory.getLogger(PackageValidator.class);

    private static PackageValidator instance;

    private PackageValidator() {
    }

    public static PackageValidator getInstance() {
        LOGGER.info("Initializing PackageValidator");

        if (instance == null) {
            instance = new PackageValidator();
        }
        return instance;
    }

    public void validatePackageMaxWeight(Package pckage) throws APIException {
        LOGGER.info("Validating Package Weight");
        if (pckage.getWeight() > 100) {
            LOGGER.error("Package has a weight of:{} is invalid!", pckage.getWeight());
            throw new APIException("Package:" + pckage + " has a weight of " + pckage.getWeight() + ". It has exceeded the 100 weight limit");
        }
    }

    public void validatePackageNumberOfItems(Package pckage) throws APIException {
        LOGGER.info("Validating Package number of Items");
        int packageItemSize = pckage.getItemList().size();
        if (packageItemSize > 15) {
            LOGGER.error("Package has a number of {} items is invalid!", packageItemSize);
            throw new APIException("Package:" + pckage + " has a number of items " + packageItemSize + ". It has exceeded the 15 items limit");
        }
    }

    public void validateItemsMaxWeightAndPrice(List<Item> items) throws APIException {
        LOGGER.info("Validating Package Items");
        for (Item item : items) {
            validateItemWeightAndPrice(item);
        }
    }

    private void validateItemWeightAndPrice(Item item) throws APIException {
        boolean itemNotExceededMaxWeight = isItemNotExceededMaxWeight(item);
        boolean isItemNotExceededMaxPrice = isItemNotExceededMaxPrice(item);
        if (!itemNotExceededMaxWeight) {
            LOGGER.error("Item has a weight of:{} is invalid!", item.getWeight());
            throw new APIException("Item:" + item + " has a weight of " + item.getWeight() + ". It has exceeded the 100 weight limit");
        }

        if (!isItemNotExceededMaxPrice) {
            LOGGER.error("Item has a price of:{} is invalid!", item.getPrice());
            throw new APIException("Item:" + item + " has a price of " + item.getPrice() + ". It has exceeded the 100 EUR price limit");
        }
    }

    private static boolean isItemNotExceededMaxWeight(Item item) {
        return item.getWeight() <= 100 ? true : false;
    }

    private static boolean isItemNotExceededMaxPrice(Item item) {
        String priceAsString = item.getPrice()
                .replaceAll("[^\\d.,]","")
                .replace(",",".");
        Double price = Double.valueOf(priceAsString);
        return price <= 100 ? true : false;
    }
}
