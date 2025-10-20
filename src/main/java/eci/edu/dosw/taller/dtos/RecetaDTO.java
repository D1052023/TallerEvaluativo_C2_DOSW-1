package eci.edu.dosw.taller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDTO {
    private String id;
    private String titulo;
    private List<String> ingredientes;
    private List<String> pasos;
    private String chefId;
    private String nombreChef;
    private String especialidad;
    private String typeChef;
    private Integer temporada;
}
