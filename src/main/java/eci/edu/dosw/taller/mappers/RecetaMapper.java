package eci.edu.dosw.taller.mappers;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.enums.TypeChef;
import eci.edu.dosw.taller.models.*;

public class RecetaMapper {
    private RecetaMapper() {
    }
    public static Receta toEntity(RecetaDTO dto) {
        Chef chef = ChefFactory.createChef(
                TypeChef.fromDisplayName(dto.getTypeChef()),
                dto.getChefId(),
                dto.getNombreChef(),
                dto.getEspecialidad(),
                dto.getTemporada()
        );

        return new Receta(
                dto.getId(),
                dto.getTitulo(),
                dto.getIngredientes(),
                dto.getPasos(),
                chef
        );
    }

    public static RecetaDTO toDTO(Receta receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setId(receta.getId());
        dto.setTitulo(receta.getTitulo());
        dto.setIngredientes(receta.getIngredientes());
        dto.setPasos(receta.getPasos());
        dto.setChefId(receta.getChef().getId());
        dto.setNombreChef(receta.getChef().getNombre());
        dto.setEspecialidad(receta.getChef().getEspecialidad());
        dto.setTypeChef(receta.getChef().getType().getDisplayName());
        if (receta.getChef() instanceof Participante p) {
            dto.setTemporada(p.getTemporada());
        }
        return dto;
    }
}
