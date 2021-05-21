package com.epam.liadov;

import lombok.extern.slf4j.Slf4j;

/**
 * MainImpl - class for initiation of demonstration for CRUD implementation
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class MainImpl {

    public static void main(String[] args) {
        CrudController crudController = new CrudController();
        crudController.demonstrate();
    }

}