package com.example.gestionpracticasempresahibernate.domain.company;

import com.example.gestionpracticasempresahibernate.domain.DAO;
import com.example.gestionpracticasempresahibernate.domain.HibernateUtil;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class CompanyDAOImp implements DAO<Company> {
    @Override
    public ArrayList<Company> getAll() {
        var salida = new ArrayList<Company>(0);
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Company> query = sesion.createQuery("from Company ", Company.class);
            salida = (ArrayList<Company>) query.getResultList();
        }
        return salida;
    }

    @Override
    public Company get(Long id) {
        var salida = new Company();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Company.class, id);
        }
        return salida;
    }

    @Override
    public Company save(Company data) {
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
    public void update(Company data) {
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
    public void delete(Company data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Company company = session.get(Company.class, data.getCompany_id());
            session.remove(company);
        }));
    }
    public void insertCompany(Company company){
        try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            //Crear un nuevo item
            Company newcompany = new Company( );
            newcompany.setCompany_name( company.getCompany_name() );
            newcompany.setPhone_number( company.getPhone_number() );
            newcompany.setEmail( company.getEmail() );
            newcompany.setCompany_contact( company.getCompany_contact() );
            newcompany.setIncidents( company.getIncidents() );
            s.persist( newcompany );
            t.commit( );
        }
    }

    public void updateCompany (Company company) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company companySelected = session.get(Company.class, company.getCompany_id());

            companySelected.setCompany_name(company.getCompany_name());
            companySelected.setEmail(company.getEmail());
            companySelected.setCompany_contact(company.getCompany_contact());
            companySelected.setPhone_number(company.getPhone_number());
            companySelected.setIncidents(company.getIncidents());
            transaction.commit();
        }
    }
}
