package com.example.gestionpracticasempresahibernate.domain.teacher;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO para la entidad Teacher.
 * Proporciona métodos para realizar operaciones de acceso a datos específicas para la entidad Teacher.
 */
@Log
public class TeacherDAOImp implements DAO<Teacher> {

    // Implementación de métodos de la interfaz DAO
    @Override
    public List<Teacher> getAll() {
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

    /**
     * Obtiene una lista de estudiantes asignados a un profesor.
     *
     * @param teacher El profesor del que se obtienen los estudiantes asignados.
     * @return Una lista de estudiantes asignados al profesor especificado.
     */
    //Obteniene el profesor del alumno, la cual se usa para inicializar la tabla e incluir los datos
    public List<Student> alumnoDeProfesor(Teacher teacher ) {
        List<Student> salida = new ArrayList<>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where tutor.id =: tutor" , Student.class );
            q.setParameter( "tutor" , teacher.getTeacher_id() );
            salida = q.getResultList();
        }
        return salida;
    }

    /**
     * Obtiene un estudiante por su identificador.
     *
     * @param id El identificador del estudiante.
     * @return El estudiante correspondiente al identificador especificado.
     */
    //Obtiene el id del alumno, usado para meter en un lista al usuario
    public Student alumnoPorId( Long id ) {
        Student salida = null;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where student_id =: i" , Student.class );
            q.setParameter( "i" , id );
            salida = q.getSingleResult();
        }
        return salida;
    }

    /**
     * Obtiene una compañía por su nombre.
     *
     * @param companyName El nombre de la compañía.
     * @return La compañía correspondiente al nombre especificado.
     */
    //Obtiene el nombre de la compañía para usarlo en el combobox
    public Company nombreCompañia(String companyName ) {
        Company salida = null;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Company> q = s.createQuery( "from Company where company_name =: name" , Company.class );
            q.setParameter( "name" , companyName );
            salida = q.getSingleResult();
        }
        return salida;
    }

    /**
     * Valida un usuario profesor mediante su nombre de usuario y contraseña.
     *
     * @param user El nombre de usuario del profesor.
     * @param pass La contraseña del profesor.
     * @return El profesor validado si coincide con el nombre de usuario y contraseña proporcionados.
     */
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

    /**
     * Añade un nuevo alumno.
     *
     * @param student El alumno a añadir.
     */
    public void addAlumno(Student student) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            //Crear un nuevo alumno
            Student newAlumno = new Student();
            newAlumno.setDni( student.getDni() );
            newAlumno.setCompany( student.getCompany() );
            newAlumno.setPassword( student.getPassword() );
            newAlumno.setEmail( student.getEmail() );
            newAlumno.setContact_phone( student.getContact_phone() );
            newAlumno.setDiary_activities( student.getDiary_activities() );
            newAlumno.setDate_of_birth( student.getDate_of_birth() );
            newAlumno.setTotal_dual_hours( student.getTotal_dual_hours() );
            newAlumno.setFirst_name( student.getFirst_name() );
            newAlumno.setLast_name( student.getLast_name() );
            newAlumno.setTotal_fct_hours( student.getTotal_fct_hours() );
            newAlumno.setObservations( student.getObservations() );
            newAlumno.setTutor( student.getTutor() );
            session.persist(newAlumno);
            transaction.commit();
        }catch (Exception e){
            log.severe("Error al insertar un nuevo alumno");
        }
    }

    /**
     * Elimina un alumno existente.
     *
     * @param alumno El alumno a eliminar.
     */
    public void deleteAlumno(Student alumno){
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Student student = session.get(Student.class, alumno.getStudent_id());
            session.remove(student);
        }));
    }

    /**
     * Actualiza la información de un alumno existente.
     *
     * @param student La información actualizada del alumno.
     */
    public void updateAlumno (Student student){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Student alumno = session.get(Student.class, student.getStudent_id());

            alumno.setFirst_name( student.getFirst_name() );
            alumno.setLast_name( student.getLast_name() );
            alumno.setDni( student.getDni() );
            alumno.setDate_of_birth( student.getDate_of_birth() );
            alumno.setEmail( student.getEmail() );
            alumno.setCompany( student.getCompany() );
            alumno.setTotal_dual_hours( student.getTotal_dual_hours()) ;
            alumno.setTotal_fct_hours( student.getTotal_fct_hours() );
            alumno.setObservations( student.getObservations() );
            transaction.commit();
        }
    }
}
