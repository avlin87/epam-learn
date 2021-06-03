package com.epam.liadov.repository.stub;

import com.epam.liadov.repository.OrderProductRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OrderProductRepositoryStubTest
 *
 * @author Aleksandr Liadov
 */
class OrderProductRepositoryStubTest {

    private final OrderProductRepository orderProductRepository = new OrderProductRepositoryStub();

    @Test
    void saveIdReturnTrue() {
        List<Integer> list = new ArrayList<>(List.of(1,2,3,4));

        boolean saveResult = orderProductRepository.saveId(1, list);

        assertTrue(saveResult);
    }

    @Test
    void updateIdReturnTrue() {
        List<Integer> list = new ArrayList<>(List.of(1,2,3,4));

        boolean updateResult = orderProductRepository.updateId(1, list);

        assertTrue(updateResult);
    }
}