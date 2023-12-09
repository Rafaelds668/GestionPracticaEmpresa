package com.example.gestionpracticasempresahibernate.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz genérica para definir operaciones básicas de acceso a datos (DAO).
 *
 * @param <T> El tipo de entidad con la que trabaja el DAO.
 */
public interface DAO<T> {
    /**
     * Obtiene una lista de todos los elementos del tipo especificado.
     *
     * @return Una lista de elementos del tipo especificado.
     */
    public List<T> getAll();

    /**
     * Obtiene un elemento del tipo especificado según su identificador.
     *
     * @param id El identificador del elemento a obtener.
     * @return El elemento correspondiente al identificador especificado.
     */
    public T get(Long id);

    /**
     * Guarda o persiste un nuevo elemento del tipo especificado.
     *
     * @param data El elemento a guardar o persistir.
     * @return El elemento guardado o persistido.
     */
    public T save(T data);

    /**
     * Actualiza un elemento existente del tipo especificado.
     *
     * @param data El elemento a actualizar.
     */
    public void update(T data);

    /**
     * Elimina un elemento existente del tipo especificado.
     *
     * @param data El elemento a eliminar.
     */
    public void delete(T data);
}
