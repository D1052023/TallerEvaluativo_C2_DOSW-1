package eci.edu.dosw.taller.models;

import eci.edu.dosw.taller.enums.TypeChef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase abstracta que representa a un Chef dentro del sistema Master Chef Celebrity.
 */
public abstract class Chef {
    private String id;
    private String nombre;
    private String especialidad;
    private TypeChef type;
}
