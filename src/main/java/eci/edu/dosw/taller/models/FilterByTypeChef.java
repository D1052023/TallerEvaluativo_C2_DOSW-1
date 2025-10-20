package eci.edu.dosw.taller.models;


import eci.edu.dosw.taller.enums.TypeChef;
import java.util.List;


/**
 * Clase encargada de filtrar recetas por tipo de chef del programa
 */
public class FilterByTypeChef implements FilterStrategy {
    private TypeChef type;

    public FilterByTypeChef(TypeChef type) {
        this.type = type;
    }

    @Override
    public List<Receta> filter(List<Receta> recetas) {
        return recetas.stream()
                .filter(r -> r.getChef().getType().equals(type))
                .toList();
    }
}
