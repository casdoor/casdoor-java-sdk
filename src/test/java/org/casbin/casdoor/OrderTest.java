// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

import org.casbin.casdoor.entity.Order;
import org.casbin.casdoor.entity.Payment;
import org.casbin.casdoor.entity.Product;
import org.casbin.casdoor.entity.ProductInfo;
import org.casbin.casdoor.service.OrderService;
import org.casbin.casdoor.service.ProductService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private final OrderService orderService = new OrderService(TestDefaultConfig.InitConfig());
    private final ProductService productService = new ProductService(TestDefaultConfig.InitConfig());

    @Test
    public void testOrder() throws IOException {
        String productName = TestDefaultConfig.getRandomName("OrderProduct");
        String orderName = TestDefaultConfig.getRandomName("Order");

        Product product = new Product();
        product.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        product.name = productName;
        product.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        product.displayName = productName;
        product.image = "https://cdn.casbin.org/img/casdoor-logo_1185x256.png";
        product.description = "Casdoor Website";
        product.tag = "auto_created_product_for_plan";
        product.quantity = 999;
        product.state = "Published";
        product.providers = java.util.Collections.singletonList("provider_payment_dummy");
        product.price = 1;
        product.currency = "USD";
        productService.addProduct(product);

        ProductInfo productInfo = new ProductInfo(productName, 1);
        productInfo.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        productInfo.displayName = productName;
        productInfo.price = 1;
        productInfo.currency = "USD";

        Order order = new Order();
        order.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        order.name = orderName;
        order.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        order.displayName = orderName;
        order.products = new String[]{productName};
        order.productInfos = new ProductInfo[]{productInfo};
        order.user = "admin";
        order.price = 1;
        order.currency = "USD";
        order.state = "Created";
        assertEquals("Affected", orderService.addOrder(order).getData());

        assertTrue(orderService.getOrders().stream().anyMatch(item -> item.name.equals(orderName)));
        assertTrue(((Number) orderService.getPaginationOrders(1, 10, null).get("data2")).longValue() > 0);
        assertTrue(orderService.getUserOrders("admin").stream().anyMatch(item -> item.name.equals(orderName)));

        Order retrieved = orderService.getOrder(orderName);
        assertEquals(orderName, retrieved.name);

        retrieved.message = "Updated order message";
        assertEquals("Affected", orderService.updateOrder(retrieved).getData());
        assertEquals("Updated order message", orderService.getOrder(orderName).message);

        assertEquals("Affected", orderService.cancelOrder(orderName).getData());

        assertEquals("Affected", orderService.deleteOrder(retrieved).getData());
        assertNull(orderService.getOrder(orderName));

        productService.deleteProduct(product);
    }

    @Test
    public void testOrderPay() throws IOException {
        String productName = TestDefaultConfig.getRandomName("OrderPayProduct");

        Product product = new Product();
        product.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        product.name = productName;
        product.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        product.displayName = productName;
        product.image = "https://cdn.casbin.org/img/casdoor-logo_1185x256.png";
        product.description = "Casdoor Website";
        product.tag = "auto_created_product_for_plan";
        product.quantity = 999;
        product.state = "Published";
        product.providers = java.util.Collections.singletonList("provider_payment_dummy");
        product.price = 1;
        product.currency = "USD";
        productService.addProduct(product);

        Order order = orderService.placeOrder(new ProductInfo[]{new ProductInfo(productName, 1)}, "admin");
        assertNotNull(order.name);

        Payment payment = orderService.payOrder(order.name, "provider_payment_dummy");
        assertNotNull(payment);

        productService.deleteProduct(product);
    }
}
