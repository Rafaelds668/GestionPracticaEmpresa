package com.example.gestionpracticasempresahibernate.domain.student;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import com.example.gestionpracticasempresahibernate.domain.activity.Activity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO para la entidad Student.
 * Proporciona métodos para realizar operaciones de acceso a datos específicas para la entidad Student.
 */
public class StudentDAOImp implements DAO<Student> {

    // Implementación de métodos de la interfaz DAO
    @Override
    public List<Student> getAll() {
        var salida = new ArrayList<Student>(0);
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = sesion.createQuery("from Student", Student.class);
            salida = (ArrayList<Student>) query.getResultList();
        }
        return salida;
    }

    @Override
    public Student get(Long id) {
        var salida = new Student();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Student.class, id);
        }
        return salida;
    }

    @Override
    public Student save(Student data) {
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
    public void update(Student data) {
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
    public void delete(Student data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Student student = session.get(Student.class, data.getStudent_id());
            session.remove(student);
        }));
    }

    /**
     * Valida un usuario estudiante mediante su nombre de usuario y contraseña.
     *
     * @param user El nombre de usuario del estudiante.
     * @param pass La contraseña del estudiante.
     * @return El estudiante validado si coincide con el nombre de usuario y contraseña proporcionados.
     */
    public Student validateUser(String user, String pass){
        //Desde un lambda no se puede escribir desde una variable externa.
        Student result = null;

        //Si la sesión está dentro de un try con recursos se cierra sola.
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //Se hacen consultas a la entidad (clase User) no a la tabla.
            Query<Student> query = session.createQuery("from Student where first_name =: name and password=:p", Student.class);

            //Se refieren a los que entran por el método.
            query.setParameter("name", user);
            query.setParameter("p", pass);
            //

            try {
                result = query.getSingleResult();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Obtiene una lista de actividades asociadas a un estudiante.
     *
     * @param student El estudiante del que se obtienen las actividades asociadas.
     * @return Una lista de actividades asociadas al estudiante especificado.
     */
    public List<Activity> actividadDeAlumno(Student student) {
        List<Activity> salida = new ArrayList<>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Activity> q = s.createQuery( "from Student where student_id =: student" , Activity.class );
            q.setParameter( "student" , student.getStudent_id() );
            salida = q.getResultList();
        }
        return salida;
    }
}
