package org.kaczucha.repository;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.kaczucha.Client;

import java.sql.SQLException;
import java.util.Optional;

public class HibernateRepository implements ClientRepository {
    @Override
    public void save(Client client) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();

    }

    @Override
    public void deleteClient(String email) {

    }

    @Override
    public Optional<Client> findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client where mail=:mail", Client.class);
        query.setParameter("mail", email);
        return (Optional<Client>) query.uniqueResult();
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
