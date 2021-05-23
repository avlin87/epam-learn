package com.epam.liadov;


import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CrudControllerTest - test class for {@link CrudServiceMain}
 *
 * @author Aleksandr Liadov
 */

@RunWith(MockitoJUnitRunner.class)
public class CrudServiceMainTest {

    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;
    private Query query;


    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);
        when(entityManagerMock.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyInt(), anyInt())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
    }

    @Test
    public void demonstrateTest() {
        CrudServiceMain crudServiceMain = new CrudServiceMain(entityManagerFactoryMock);

        crudServiceMain.main(null);
    }
}