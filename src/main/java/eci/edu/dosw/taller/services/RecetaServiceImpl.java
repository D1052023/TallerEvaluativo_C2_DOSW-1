package eci.edu.dosw.taller.services;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.mappers.RecetaMapper;
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
        Receta receta = RecetaMapper.toEntity(dto);
        recetaRepository.save(receta);
        return RecetaMapper.toDTO(receta);
    }

    @Override
    public List<RecetaDTO> obtenerTodas() {
        return recetaRepository.findAll().stream()
                .map(RecetaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
