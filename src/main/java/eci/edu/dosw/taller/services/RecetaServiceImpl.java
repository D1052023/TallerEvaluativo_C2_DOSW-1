package eci.edu.dosw.taller.services;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.enums.TypeChef;
import eci.edu.dosw.taller.mappers.RecetaMapper;
import eci.edu.dosw.taller.models.FilterByTypeChef;
import eci.edu.dosw.taller.models.FilterStrategy;
import eci.edu.dosw.taller.models.Receta;
import eci.edu.dosw.taller.repositories.RecetaRepository;
import eci.edu.dosw.taller.services.RecetaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @Override
    public RecetaDTO registrarRecetaTelevidente(RecetaDTO dto) {
        if (dto.getTypeChef() != null &&
                !dto.getTypeChef().equalsIgnoreCase("Televidente")) {
            throw new IllegalArgumentException("Solo se pueden registrar recetas de televidentes en este endpoint.");
        }

        if (dto.getTemporada() != null) {
            throw new IllegalArgumentException("Un televidente no tiene temporada asignada.");
        }

        dto.setTypeChef("Televidente");
        Receta receta = RecetaMapper.toEntity(dto);
        recetaRepository.save(receta);
        return RecetaMapper.toDTO(receta);
    }

    @Override
    public RecetaDTO registrarRecetaParticipante(RecetaDTO dto) {
        if (dto.getTypeChef() != null &&
                !dto.getTypeChef().equalsIgnoreCase("Participante")) {
            throw new IllegalArgumentException("Solo se pueden registrar recetas de participantes en este endpoint.");
        }
        if (dto.getTemporada() == null) {
            throw new IllegalArgumentException("La temporada es obligatoria para un participante.");
        }

        dto.setTypeChef("Participante");
        Receta receta = RecetaMapper.toEntity(dto);
        recetaRepository.save(receta);
        return RecetaMapper.toDTO(receta);
    }

    @Override
    public RecetaDTO registrarRecetaJurado(RecetaDTO dto) {
        if (dto.getTypeChef() != null &&
                !dto.getTypeChef().equalsIgnoreCase("Jurado")) {
            throw new IllegalArgumentException("Solo se pueden registrar recetas de Jurados en este endpoint.");
        }
        if (dto.getTemporada() != null) {
            throw new IllegalArgumentException("Un jurado no tiene temporada asignada.");
        }

        dto.setTypeChef("Jurado");
        Receta receta = RecetaMapper.toEntity(dto);
        recetaRepository.save(receta);
        return RecetaMapper.toDTO(receta);
    }

    @Override
    public List<RecetaDTO> obtenerTodas() {
        return recetaRepository.findAll().stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
    @Override
    public RecetaDTO obtenerRecetaPorId(String id) {
        return recetaRepository.findById(id)
                .map(RecetaMapper::toDTO)
                .orElseThrow(() ->
                        new IllegalArgumentException("No existe una receta con el ID: " + id));
    }
    @Override
    public List<RecetaDTO> obtenerRecetasPorParticipantes() {
        List<Receta> todas = recetaRepository.findAll();
        FilterStrategy filtro = new FilterByTypeChef(TypeChef.PARTICIPANTE);
        return filtro.filter(todas)
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
}
