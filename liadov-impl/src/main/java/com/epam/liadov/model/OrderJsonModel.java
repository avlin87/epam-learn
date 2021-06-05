package com.epam.liadov.model;

import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * OrderJsonModel - Custom serialization and deserialization of Order entity
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class OrderJsonModel implements JsonDeserializer<Order>, JsonSerializer<Order> {

    @Override
    public Order deserialize(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        Order order = gson.fromJson(paramJsonElement, Order.class);
        try {
            List<Product> productId = new ArrayList<>();
            Iterator<JsonElement> iterator = paramJsonElement.getAsJsonObject()
                    .get("productId")
                    .getAsJsonArray()
                    .iterator();
            while (iterator.hasNext()) {
                JsonElement jsonElement = iterator.next();
                var product = new Product();
                int asInt = jsonElement.getAsInt();
                product.setProductId(asInt);
                productId.add(product);
            }
            order.setProductId(productId);
        } catch (IllegalArgumentException e) {
            log.error("Parsing of productId Failed ", e);
        }
        log.trace("parsed order: {}", order);
        return order;
    }

    @Override
    public JsonElement serialize(Order src, Type typeOfSrc, JsonSerializationContext context) {
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        obj.add("orderID", gson.toJsonTree(src.getOrderID()));
        obj.add("orderNumber", gson.toJsonTree(src.getOrderNumber()));
        obj.add("customerId", gson.toJsonTree(src.getCustomerId()));
        obj.add("orderDate", gson.toJsonTree(src.getOrderDate()));
        obj.add("totalAmount", gson.toJsonTree(src.getTotalAmount()));
        List<Integer> products = new ArrayList<>();
        for (Product product : src.getProductId()) {
            products.add(product.getProductId());
        }
        obj.add("productId", gson.toJsonTree(products));
        return obj;
    }
}