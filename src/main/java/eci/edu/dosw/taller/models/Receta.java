package eci.edu.dosw.taller.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase que representa una receta dentro del sistema Máster Chef Celebrity.
 */
public class Receta {
    private String id;
    private String titulo;
    private List<String> ingredientes;
    private List<String> pasos;
    private Chef chef;
}
