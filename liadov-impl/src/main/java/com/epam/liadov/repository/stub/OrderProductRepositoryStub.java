package com.epam.liadov.repository.stub;

import com.epam.liadov.repository.OrderProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderProductRepositoryImpl - class for stubbed operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("local")
public class OrderProductRepositoryStub implements OrderProductRepository {

    @Override
    public boolean saveId(int orderId, List<Integer> productIds) {
        log.debug("OrderProduct table populated");
        return true;
    }

    @Override
    public boolean updateId(int orderId, List<Integer> productIds) {
        log.debug("OrderProduct table up to date");
        return true;
    }

}
