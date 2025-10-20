package eci.edu.dosw.taller.models;

import java.util.List;
import eci.edu.dosw.taller.models.Receta;
import java.util.stream.Collectors;

/**
 * Interfaz encargada de filtrar las recetas
 */
public interface FilterStrategy {
    List<Receta> filter(List<Receta> recetas);
}
