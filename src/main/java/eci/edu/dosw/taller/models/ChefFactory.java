package eci.edu.dosw.taller.models;

import eci.edu.dosw.taller.enums.TypeChef;
/**
 * Clase que implementa el patrón de diseño Factory Method para la creación de objetos Chef.
 */
public class ChefFactory {
    public static Chef createChef(TypeChef type, String id, String nombre, String especialidad, Integer temporada) {
        switch (type) {
            case PARTICIPANTE:
                return new Participante(id, nombre, especialidad, temporada);
            case JURADO:
                return new Jurado(id, nombre, especialidad);
            case TELEVIDENTE:
                return new Televidente(id, nombre, especialidad);
            default:
                throw new IllegalArgumentException("Tipo de chef no válido: " + type);
        }
    }
    private ChefFactory() {
    }
}
