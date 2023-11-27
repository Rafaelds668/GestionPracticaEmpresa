package com.example.gestionpracticasempresahibernate.domain.activity;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ActivityDAOImp implements DAO<Activity> {
    @Override
    public ArrayList<Activity> getAll() {
        var salida = new ArrayList<Activity>(0);
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Activity> query = session.createQuery("from Activity ", Activity.class);
            salida = (ArrayList<Activity>) query.getResultList();
        }
        return salida;
    }

    @Override
    public Activity get(Long id) {
        var salida = new Activity();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Activity.class, id);
        }
        return salida;
    }

    @Override
    public Activity save(Activity data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                // Comenzar la transacción
                transaction = session.beginTransaction();

                // Guardar el nuevo pedido en la base de datos
                session.save(data);

                // Commit de la transacción
                transaction.commit();
            } catch (Exception e) {
                // Manejar excepción que pueda ocurrir durante la transacción
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return data;
        }
    }

    @Override
    public void update(Activity data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Actualizar el pedido en la base de datos
            session.update(data);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Activity data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Activity activity = session.get(Activity.class, data.getActivity_id());
            session.remove(activity);
        }));
    }
}
