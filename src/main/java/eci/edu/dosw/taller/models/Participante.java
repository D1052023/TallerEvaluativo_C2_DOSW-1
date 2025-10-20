package eci.edu.dosw.taller.models;

import eci.edu.dosw.taller.enums.TypeChef;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * Clase que representa al participante dentro del sistema Master Chef Celebrity.
 */
public class Participante extends Chef {
    private int temporada;

    public Participante(String id, String nombre, String especialidad, int temporada) {
        super(id, nombre, especialidad, TypeChef.PARTICIPANTE);
        this.temporada = temporada;
    }
}
