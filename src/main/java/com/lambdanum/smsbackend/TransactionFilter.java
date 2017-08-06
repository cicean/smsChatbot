package com.lambdanum.smsbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.persistence.EntityManager;
import javax.servlet.*;
import java.io.IOException;

@Component
public class TransactionFilter extends GenericFilterBean implements Filter {

    private EntityManager entityManager;


    @Autowired
    public TransactionFilter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        entityManager.getTransaction().begin();

        try {
            chain.doFilter(request,response);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
