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
    public void saveAccount(Account account, int clientId) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Client client = session.find(Client.class, (long) clientId);
        System.out.println(client.getName() + " " + client.getId());
        List<Account> accounts = client.getAccounts();

        accounts.add(account);

        client.setAccounts(accounts);
        transaction.commit();
        sessionFactory.close();
    }
    @Override
    public void deleteAccount(int accountId) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Account account = session.find(Account.class, (long)accountId);
        session.remove(account);

        transaction.commit();
        sessionFactory.close();
    }


    @Override
    public void deleteClient(String email) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Account where account_id=:account_id");
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
    public void withdraw(double amount, int accountId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Account where account_id=:account_id");
        query.setParameter("account_id", accountId);
        Account account = (Account) query.uniqueResult();

        account.setBalance(account.getBalance() - amount);

        System.out.println("Your current balance is " + account.getBalance());
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deposit(String email, double amount, int accountId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Account where account_id=:account_id");
        query.setParameter("account_id", accountId);
        Account account = (Account) query.uniqueResult();

        account.setBalance(account.getBalance() + amount);

        System.out.println("Your current balance is " + account.getBalance());
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void transfer(String emailFrom, String emailTo, double amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail");
        query.setParameter("mail", emailFrom);
        Client client = (Client) query.uniqueResult();
        client.setBalance(client.getBalance() - amount);

        Query query1 = session.createQuery("from Client where mail=:mail");

        query1.setParameter("mail", emailTo);
        Client client1 = (Client) query1.uniqueResult();
        client1.setBalance(client1.getBalance() + amount);
        session.getTransaction().commit();
        session.close();

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
