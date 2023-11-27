package com.example.gestionpracticasempresahibernate.domain.teacher;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImp implements DAO<Teacher> {
    @Override
    public ArrayList<Teacher> getAll() {
        var salida = new ArrayList<Teacher>(0);
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Teacher> query = sesion.createQuery("from Teacher", Teacher.class);
            salida = (ArrayList<Teacher>) query.getResultList();
        }
        return salida;
    }

    @Override
    public Teacher get(Long id) {
        var salida = new Teacher();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Teacher.class, id);
        }
        return salida;
    }

    @Override
    public Teacher save(Teacher data) {
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
    public void update(Teacher data) {
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
    public void delete(Teacher data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Teacher teacher = session.get(Teacher.class, data.getTeacher_id());
            session.remove(teacher);
        }));
    }

    public List<Student> alumnoDeProfesor(Teacher teacher ) {
        List<Student> salida = new ArrayList<>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where tutor.id =: tutor" , Student.class );
            q.setParameter( "tutor" , teacher.getTeacher_id() );
            salida = q.getResultList();
        }
        return salida;
    }

    public Student alumnoPorId( Long id ) {
        Student salida = null;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where student_id =: i" , Student.class );
            q.setParameter( "i" , id );
            salida = q.getSingleResult();
        }
        return salida;
    }

    public Teacher validateUser(String user, String pass){

        //Desde un lambda no se puede escribir desde una variable externa.
        Teacher result = null;

        //Si la sesión está dentro de un try con recursos se cierra sola.
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //Se hacen consultas a la entidad (clase User) no a la tabla.
            Query<Teacher> query = session.createQuery("from Teacher where first_name =: name and password=:p", Teacher.class);

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
}
