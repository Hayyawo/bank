package org.kaczucha.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;

import java.util.Objects;
import java.util.Optional;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
//        client
//                .getAccounts()
//                .forEach(session::save);
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteClient(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail",Client.class);
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();
//        Client cliet1 = session.find(Client.class, client.getId());
        session.delete(client);
        System.out.println(client.getId());
        System.out.println(client.getName());
        System.out.println(client.getEmail());
        System.out.println(client.getAccounts());



        session.getTransaction().commit();
        session.close();

        //Create session factory object
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        //getting session object from session factory
//        Session session = sessionFactory.openSession();
//        //getting transaction object from session object
//        session.beginTransaction();
////        Client client = (Client) session.load(Client.class, 3);
//        Query query = session.createQuery("from Client where mail=:mail");
//        query.setParameter("mail", email);
//        Client client = (Client) query.uniqueResult();
//
//        Client client1 = session.find(Client.class, 3L);
//
//        session.delete(client1);
//
//        System.out.println("Deleted Successfully");
//        session.getTransaction().commit();
//        sessionFactory.close();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();
        session.close();
        return Optional.ofNullable(client);


    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean notExistsByEmail(String email) {
        return false;
    }
}
