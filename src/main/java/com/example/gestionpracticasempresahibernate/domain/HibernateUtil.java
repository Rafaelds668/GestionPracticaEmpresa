package com.example.gestionpracticasempresahibernate.domain;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase de utilidad para la configuración de Hibernate y la gestión de SessionFactory.
 * Utiliza la configuración definida en el archivo hibernate.cfg.xml para crear la SessionFactory.
 */
@Log
public class HibernateUtil {
    private static SessionFactory sf = null;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            sf = cfg.buildSessionFactory();
            log.info("SessionFactory creada con éxito!");
        } catch (Exception ex) {
            log.severe("Error al crear SessionFactory()");
        }
    }

    /**
     * Obtiene la instancia de SessionFactory.
     *
     * @return La SessionFactory creada a partir de la configuración de Hibernate.
     */
    public static SessionFactory getSessionFactory() {return sf;}
}
