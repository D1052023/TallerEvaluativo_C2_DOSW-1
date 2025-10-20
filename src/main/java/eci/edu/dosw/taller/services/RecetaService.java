package eci.edu.dosw.taller.services;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import java.util.List;

public interface RecetaService {
    RecetaDTO registrarRecetaTelevidente(RecetaDTO receta);
    RecetaDTO registrarRecetaParticipante(RecetaDTO receta);
    RecetaDTO registrarRecetaJurado(RecetaDTO receta);
    List<RecetaDTO> obtenerTodas();
    RecetaDTO obtenerRecetaPorId(String id);
    List<RecetaDTO> obtenerRecetasPorParticipantes();
    List<RecetaDTO> obtenerRecetasPorTelevidentes();
}
