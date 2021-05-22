package com.epam.liadov;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * MainImpl - class for initiation of demonstration for CRUD implementation
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class MainImpl {

    public static void main(String[] args) {
        EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
        var crudController = new CrudService(entityPU);
        crudController.demonstrate();
    }

}