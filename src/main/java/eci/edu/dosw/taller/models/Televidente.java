package eci.edu.dosw.taller.models;

import eci.edu.dosw.taller.enums.TypeChef;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * Clase que representa al Televidente dentro del sistema Master Chef Celebrity.
 */
public class Televidente extends Chef {

    public Televidente(String id, String nombre, String especialidad){
        super(id, nombre, especialidad, TypeChef.TELEVIDENTE);
    }
}
