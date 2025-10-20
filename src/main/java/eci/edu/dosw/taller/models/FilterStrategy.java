package eci.edu.dosw.taller.models;

import java.util.List;


/**
 * Interfaz encargada de filtrar las recetas
 */
public interface FilterStrategy {
    List<Receta> filter(List<Receta> recetas);
}
