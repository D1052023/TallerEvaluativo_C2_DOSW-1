package eci.edu.dosw.taller.models;

import eci.edu.dosw.taller.enums.TypeChef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * Clase que representa al jurado dentro del sistema Master Chef Celebrity.
 */
public class Jurado extends Chef{
    public Jurado(String id, String nombre, String especialidad) {
        super(id, nombre,especialidad, TypeChef.JURADO);
    }

}
