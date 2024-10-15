// Copyright 2023 The Casdoor Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Product;
import org.casbin.casdoor.service.ProductService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private final ProductService productService = new ProductService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testProduct() {
        String name = TestDefaultConfig.getRandomName("Product");

        // Add a new object
        Product product = new Product(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "https://cdn.casbin.org/img/casdoor-logo_1185x256.png",
                "Casdoor Website",
                "auto_created_product_for_plan",
                999,
                0,
                "Published"
        );
        product.providers = new ArrayList<>();
        product.providers.add("provider_payment_dummy");
        assertDoesNotThrow(() -> productService.addProduct(product));

        // Get all objects, check if our added object is inside the list
        List<Product> products;
        try {
            products = productService.getProducts();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = products.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Product retrievedProduct;
        try {
            retrievedProduct = productService.getProduct(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedProduct.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedProduct.description = updatedDescription;
        assertDoesNotThrow(() -> productService.updateProduct(retrievedProduct));

        // Validate the update
        Product updatedProduct;
        try {
            updatedProduct = productService.getProduct(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedProduct.description, "Failed to update object, description mismatch");

        // Test the buyProduct function
        Product boughtProduct;
        try {
            boughtProduct = productService.buyProduct(name, "provider_payment_dummy", "admin");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("The user") && errorMessage.contains("doesn't exist")) {
                assertTrue(true);
            } else {
                fail("Failed to buy product: " + e.getMessage());
            }
            return;
        }

        // Delete the object
        assertDoesNotThrow(() -> productService.deleteProduct(product));

        // Validate the deletion
        Product deletedProduct;
        try {
            deletedProduct = productService.getProduct(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedProduct, "Failed to delete object, it's still retrievable");
    }

}
