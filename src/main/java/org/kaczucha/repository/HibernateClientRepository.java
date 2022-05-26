package org.kaczucha.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.kaczucha.repository.entity.Client;

import java.util.Optional;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
//        client
//                .getAccounts()
//                .forEach(session::save);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteClient(String email) {

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
