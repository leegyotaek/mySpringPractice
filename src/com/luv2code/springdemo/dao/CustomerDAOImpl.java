package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        Session theSession = sessionFactory.getCurrentSession();

        Query<Customer> theQuery = theSession
                .createQuery("from Customer order by lastName", Customer.class);

        List<Customer> customers = theQuery.getResultList();

        return customers;

    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        Session theSession = sessionFactory.getCurrentSession();

        theSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();

        Customer theCustomer = currentSession.get(Customer.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");

        theQuery.setParameter("customerId", theId);

        theQuery.executeUpdate();

    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery = null;

        if(theSearchName!=null && theSearchName.trim().length() > 0){

            theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName"+
                    " or lower(lastName) like :theName", Customer.class);

            theQuery.setParameter("theName", "%"+theSearchName.toLowerCase()+"%");

            List<Customer> customers = theQuery.getResultList();

            return customers;
        }

        return null;


    }


}
