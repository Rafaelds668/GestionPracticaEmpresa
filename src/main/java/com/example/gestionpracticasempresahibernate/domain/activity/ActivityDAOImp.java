package com.example.gestionpracticasempresahibernate.domain.activity;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO para la entidad Activity.
 * Proporciona métodos para realizar operaciones de acceso a datos específicas para la entidad Activity.
 */
public class ActivityDAOImp implements DAO<Activity> {

    /**
     * Obtiene todas las actividades almacenadas en la base de datos.
     *
     * @return Una lista de todas las actividades almacenadas.
     */
    @Override
    public List<Activity> getAll() {
        //Creo una lista de Activity para almacenar los estudiantes
        List<Activity> result = null;
        //Inicio sesion en hibernate
        try( Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Creo una consulta Query para obtener todos los objetos Activity
            Query<Activity> query = session.createQuery( "from Activity ", Activity.class);
            // Llevo a cabo la consulta y asigno los resultados a la lista de salida
            result = query.getResultList();
        }
        return result;
    }

    /**
     * Obtiene una actividad por su identificador único.
     *
     * @param id El identificador único de la actividad a buscar.
     * @return La actividad encontrada, o null si no se encuentra ninguna actividad con el ID especificado.
     */
    @Override
    public Activity get(Long id) {
        var salida = new Activity();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Activity.class, id);
        }
        return salida;
    }

    /**
     * Guarda una nueva actividad en la base de datos.
     *
     * @param data La actividad que se va a guardar.
     * @return La actividad guardada.
     */
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

    /**
     * Actualiza una actividad existente en la base de datos.
     *
     * @param data La actividad que se va a actualizar.
     */
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

    /**
     * Elimina una actividad de la base de datos.
     *
     * @param data La actividad que se va a eliminar.
     */
    @Override
    public void delete(Activity data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Activity activity = session.get(Activity.class, data.getActivity_id());
            session.remove(activity);
        }));
    }
}
