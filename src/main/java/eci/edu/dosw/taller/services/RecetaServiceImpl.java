package eci.edu.dosw.taller.services;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.enums.TypeChef;
import eci.edu.dosw.taller.mappers.RecetaMapper;
import eci.edu.dosw.taller.models.*;
import eci.edu.dosw.taller.repositories.RecetaRepository;
import eci.edu.dosw.taller.services.RecetaService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public List<RecetaDTO> obtenerRecetasPorTelevidentes() {
        List<Receta> todas = recetaRepository.findAll();
        FilterStrategy filtro = new FilterByTypeChef(TypeChef.TELEVIDENTE);
        return filtro.filter(todas)
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
    @Override
    public List<RecetaDTO> obtenerRecetasPorJurados() {
        List<Receta> todas = recetaRepository.findAll();
        FilterStrategy filtro = new FilterByTypeChef(TypeChef.JURADO);
        return filtro.filter(todas)
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
    @Override
    public List<RecetaDTO> obtenerRecetasPorTemporadas(int temporada) {
        List<Receta> todas = recetaRepository.findAll();
        FilterStrategy filtro = new FilterByTemporada(temporada);
        return filtro.filter(todas)
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
    @Override
    public List<RecetaDTO> obtenerRecetasPorIngredientes(String ingrediente) {
        List<Receta> todas = recetaRepository.findAll();
        FilterStrategy filtro = new FilterByIngredient(ingrediente);
        return filtro.filter(todas)
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }
    @Override
    public void eliminarReceta(String id) {
        if (!recetaRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una receta con el ID: " + id);
        }
        recetaRepository.deleteById(id);
    }
    @Override
    public RecetaDTO actualizarReceta(String id, RecetaDTO recetaActualizada) {
        if (recetaActualizada.getId() != null && !recetaActualizada.getId().equals(id)) {
            throw new IllegalArgumentException("No se puede modificar el ID de la receta");
        }

        Receta recetaExistente = recetaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una receta con el ID: " + id));

        recetaExistente.setTitulo(recetaActualizada.getTitulo());
        recetaExistente.setIngredientes(recetaActualizada.getIngredientes());
        recetaExistente.setPasos(recetaActualizada.getPasos());

        if (recetaExistente.getChef() instanceof Participante participante) {
            participante.setTemporada(recetaActualizada.getTemporada());
        }

        recetaRepository.save(recetaExistente);
        return RecetaMapper.toDTO(recetaExistente);
    }
}
