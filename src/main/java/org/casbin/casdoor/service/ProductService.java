package org.casbin.casdoor.service;

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Product;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.ProductOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ProductService extends Service {

    public ProductService(Config config) {
        super(config);
    }

    public Product getProduct(String name) throws IOException {
        CasdoorResponse<Product, Object> response = doGet("get-product",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Product, Object>>() {
                });
        return response.getData();
    }

    public List<Product> getProducts() throws IOException {
        CasdoorResponse<List<Product>, Object> response = doGet("get-products",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Product>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addProduct(Product product) throws IOException {
        return modifyProduct(ProductOperations.ADD_PRODUCT, product, null);
    }

    public CasdoorResponse<String, Object> deleteProduct(Product product) throws IOException {
        return modifyProduct(ProductOperations.DELETE_PRODUCT, product, null);
    }

    public CasdoorResponse<String, Object> updateProduct(Product product) throws IOException {
        return modifyProduct(ProductOperations.UPDATE_PRODUCT, product, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyProduct(ProductOperations method, Product product, java.util.Map<String, String> queryMap) throws IOException {
        String id = product.owner + "/" + product.name;
        product.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(product);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}