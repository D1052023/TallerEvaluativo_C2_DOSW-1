package eci.edu.dosw.taller.services;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import java.util.List;

public interface RecetaService {
    RecetaDTO registrarRecetaTelevidente(RecetaDTO receta);
    List<RecetaDTO> obtenerTodas();
}
