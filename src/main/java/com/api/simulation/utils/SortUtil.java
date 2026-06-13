package com.api.simulation.utils;

import com.api.simulation.common.SortField;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class SortUtil {

    public static Sort build(Object filterDto) {
        List<Sort.Order> orders = new ArrayList<>();

        for (Field field : filterDto.getClass().getDeclaredFields()) {

            SortField sortField = field.getAnnotation(SortField.class);
            if (sortField == null) continue;

            field.setAccessible(true);

            try {
                Object value = field.get(filterDto);

                if (value == null) continue;

                String dir = value.toString().trim();
                if (!"ASC".equalsIgnoreCase(dir) &&
                        !"DESC".equalsIgnoreCase(dir)) {
                    continue;
                }

                Sort.Order order =
                        "ASC".equalsIgnoreCase(dir)
                                ? Sort.Order.asc(sortField.property())
                                : Sort.Order.desc(sortField.property());

                if (sortField.nullsLast()) {
                    order = order.nullsLast();
                }

                orders.add(order);

            } catch (IllegalAccessException ignored) {}
        }

        return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
    }

    public static Sort build(String sortBy, String sortOrder) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.unsorted();
        }

        String column = toColumnName(sortBy);

        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(new Sort.Order(direction, column).nullsLast());
    }

    private static String toColumnName(String sortBy) {
        return sortBy.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
