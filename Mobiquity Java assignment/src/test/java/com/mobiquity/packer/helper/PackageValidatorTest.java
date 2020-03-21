package com.mobiquity.packer.helper;

import com.mobiquity.entities.Item;
import com.mobiquity.entities.Package;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Denzil Gideon M. Daulo
 * Test for PackageValidator Helper Class
 */
public class PackageValidatorTest {
    private Package pckage;
    private List<Item> itemList;
    private PackageValidator packageValidator;

    @BeforeEach
    public void setup() {
        packageValidator = PackageValidator.getInstance();

        pckage = new Package();
        pckage.setWeight(80);

        itemList = new ArrayList<>();
        for (int itemIndex = 0; itemIndex < 15; itemIndex++) {
            Item item = new Item();
            item.setIndexNumber(itemIndex + 1);
            item.setWeight(100);
            item.setPrice("90");

            itemList.add(item);
        }
        pckage.setItemList(itemList);
    }

    @Test
    public void shouldThrowAnApiExceptionIfPackageWeightIsInvalid() {
        pckage.setWeight(110);
        String expectedMessage="Package:" + pckage + " has a weight of " + pckage.getWeight() + ". It has exceeded the 100 weight limit";
        Throwable exception = assertThrows(APIException.class, () -> {
            packageValidator.validatePackageMaxWeight(pckage);
        });
        Assertions.assertTrue(exception.getMessage().equals(expectedMessage));
    }

    @Test
    public void shouldThrowAnApiExceptionIfPackageItemCountIsInvalid() {
        Item item = new Item();
        item.setIndexNumber(99);
        item.setWeight(100);
        item.setPrice("90");

        itemList.add(item);
        pckage.setItemList(itemList);

        String expectedMessage="Package:" + pckage + " has a number of items " + pckage.getItemList().size() + ". It has exceeded the 15 items limit";
        Throwable exception = assertThrows(APIException.class, () -> {
            packageValidator.validatePackageNumberOfItems(pckage);
        });

        Assertions.assertTrue(exception.getMessage().equals(expectedMessage));
    }

    @Test
    public void shouldThrowAnApiExceptionIfItemWeightIsInvalid() {
        Item item = new Item();
        item.setIndexNumber(99);
        item.setWeight(110);
        item.setPrice("90");
        itemList.add(item);

        String expectedMessage="Item:" + item + " has a weight of " + item.getWeight() + ". It has exceeded the 100 weight limit";
        Throwable exception = assertThrows(APIException.class, () -> {
            packageValidator.validateItemsMaxWeightAndPrice(itemList);
        });

        Assertions.assertTrue(exception.getMessage().equals(expectedMessage));
    }

    @Test
    public void shouldThrowAnApiExceptionIfItemPriceIsInvalid() {
        Item item = new Item();
        item.setIndexNumber(99);
        item.setWeight(100);
        item.setPrice("100.50");
        itemList.add(item);

        String expectedMessage="Item:" + item + " has a price of " + item.getPrice() + ". It has exceeded the 100 EUR price limit";
        Throwable exception = assertThrows(APIException.class, () -> {
            packageValidator.validateItemsMaxWeightAndPrice(itemList);
        });

        Assertions.assertTrue(exception.getMessage().equals(expectedMessage));
    }
}