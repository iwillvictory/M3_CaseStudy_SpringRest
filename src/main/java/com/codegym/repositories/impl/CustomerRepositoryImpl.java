package com.codegym.repositories.impl;

import com.codegym.models.Customer;
import com.codegym.repositories.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {
    @PersistenceContext
     private EntityManager em;
    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query= em.createQuery("Select c from Customer c ",Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query= em.createQuery("Select c from Customer c where c.id=:id",Customer.class);
        query.setParameter("id",id);
        try{
            return query.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public void save(Customer model) {
        if(model.getId() !=null){
            em.merge(model);
        }else{
            em.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        Customer cus= findById(id);
        if(cus !=null){
            em.remove(cus);
        }
    }
}
