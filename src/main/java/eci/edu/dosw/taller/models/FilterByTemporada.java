package eci.edu.dosw.taller.models;

import java.util.List;


/**
 * Clase encargada de filtrar las recetas por temporada
 */
public class FilterByTemporada implements FilterStrategy{
    private int temporada;
    public FilterByTemporada(int temporada) { this.temporada = temporada; }

    @Override
    public List<Receta> filter(List<Receta> recetas) {
        return recetas.stream()
                .filter(r -> r.getChef() instanceof Participante &&
                        ((Participante) r.getChef()).getTemporada() == temporada)
                .toList();
    }
}
