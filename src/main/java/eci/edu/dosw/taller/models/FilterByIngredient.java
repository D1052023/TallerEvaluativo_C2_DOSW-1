package eci.edu.dosw.taller.models;

import java.util.List;


/**
 * Filtra las recetas por ingredientes especificos
 */
public class FilterByIngredient implements FilterStrategy {
    private String ingrediente;

    public FilterByIngredient(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public List<Receta> filter(List<Receta> recetas) {
        return recetas.stream()
                .filter(r -> r.getIngredientes().contains(ingrediente))
                .toList();
    }
}
