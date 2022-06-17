package org.kaczucha.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
//        client
//                .getAccounts()
//                .forEach(session::save);

        session.save(client);

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteClient(String email) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();

        client.getAccounts().forEach(session::remove);
        transaction.commit();

        transaction.begin();

        session.remove(client);
        transaction.commit();
//        session.getTransaction().commit();

        sessionFactory.close();
    }

    @Override
    public void withdraw(String email, double amount, int accountId) {
        System.out.println("sadggsfg FDSGD" );

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();

        Optional<Client> byEmail = findByEmail(email);
        Account account = byEmail.get().getAccounts().get(accountId);



        System.out.println("sadggsfg FDSGD" + account.getId());
        session.getTransaction().commit();
        session.close();
    }

    public void transfer(String emailFrom, String emailTo, double amount) {

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


    @Override
    public void modifyUserName(String email, String newName) {
        findByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();
        client.setName(newName);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void modifyUserEmail(String email, String newEmail) {
        findByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", email);
        Client client = (Client) query.uniqueResult();
        client.setEmail(newEmail);
        session.getTransaction().commit();
        session.close();
    }
}
