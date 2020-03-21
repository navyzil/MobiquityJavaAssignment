package com.mobiquity.packer.helper;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import com.mobiquity.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Denzil Gideon M. Daulo
 * PackageValidator -Validates the Package and Package's Items
 * Uses a singleton pattern so it will be initialized only once
 */
public class PackageValidator {
    private static Logger LOGGER = LoggerFactory.getLogger(PackageValidator.class);

    private static PackageValidator instance;

    private PackageValidator() {
    }

    /**
     * PackageValidator object initializer
     * @return PackageValidator instance
     */
    public static PackageValidator getInstance() {
        LOGGER.info("Initializing PackageValidator");

        if (instance == null) {
            instance = new PackageValidator();
        }
        return instance;
    }

    /**
     * Validates Package weight.
     * @param pckage - Instance of Package
     * @throws APIException -  If weight reaches more than 100 then it will throw an APIException
     */
    public void validatePackageMaxWeight(Package pckage) throws APIException {
        LOGGER.info("Validating Package Weight");
        if (pckage.getWeight() > 100) {
            LOGGER.error("Package has a weight of:{} is invalid!", pckage.getWeight());
            throw new APIException("Package:" + pckage + " has a weight of " + pckage.getWeight() + ". It has exceeded the 100 weight limit");
        }
    }

    /**
     * Validates Package's number of items.
     * @param pckage - Instance of Package
     * @throws APIException -  If Package contains more than 15 items it will throw an APIException
     */
    public void validatePackageNumberOfItems(Package pckage) throws APIException {
        LOGGER.info("Validating Package number of Items");
        int packageItemSize = pckage.getItemList().size();
        if (packageItemSize > 15) {
            LOGGER.error("Package has a number of {} items is invalid!", packageItemSize);
            throw new APIException("Package:" + pckage + " has a number of items " + packageItemSize + ". It has exceeded the 15 items limit");
        }
    }

    /**
     * Validates Weight and Price of each Items in a Package.
     * @param items - List of items in the Package
     * @throws APIException -  If the Item has exceeded the weight or cost limit
     */
    public void validateItemsMaxWeightAndPrice(List<Item> items) throws APIException {
        LOGGER.info("Validating Package Items");
        for (Item item : items) {
            validateItemWeightAndPrice(item);
        }
    }

    /**
     * Validates Weight and Price of the Item in a Package.
     * @param item - Instance of an Item
     * @throws APIException -  If the Item has exceeded the weight or cost limit
     */
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

    /**
     * Checks if Item Weight exceeded 100 or not
     * @param item - Instance of an Item
     * @return boolean - true if weight is lessthan or equal 100. Otherwise false
     */
    private static boolean isItemNotExceededMaxWeight(Item item) {
        return item.getWeight() <= 100 ? true : false;
    }

    /**
     * Checks if Item Price exceeded 100 or not
     * @param item - Instance of an Item
     * @return boolean - true if price is lessthan or equal 100. Otherwise false
     */
    private static boolean isItemNotExceededMaxPrice(Item item) {
        String priceAsString = item.getPrice()
                .replaceAll("[^\\d.,]","")
                .replace(",",".");
        Double price = Double.valueOf(priceAsString);
        return price <= 100 ? true : false;
    }
}
