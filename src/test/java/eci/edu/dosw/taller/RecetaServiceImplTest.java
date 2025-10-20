package eci.edu.dosw.taller;


import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.models.Participante;
import eci.edu.dosw.taller.models.Receta;
import eci.edu.dosw.taller.repositories.RecetaRepository;
import eci.edu.dosw.taller.services.RecetaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecetaServiceImplTest {

    private RecetaRepository recetaRepository;
    private RecetaServiceImpl recetaService;

    @BeforeEach
    void setUp() {
        recetaRepository = mock(RecetaRepository.class);
        recetaService = new RecetaServiceImpl(recetaRepository);
    }

    @Test
    void buscarPorIngrediente_devuelveResultadosCorrectos() {
        Participante chef1 = new Participante("P-1", "Juan", "Cocina de mar", 1);
        Participante chef2 = new Participante("P-2", "Ana", "Cocina vegetariana", 2);


        Receta receta1 = new Receta();
        receta1.setTitulo("Ceviche");
        receta1.setIngredientes(List.of("Camarón", "Limón"));
        receta1.setChef(chef1);

        Receta receta2 = new Receta();
        receta2.setTitulo("Ensalada");
        receta2.setIngredientes(List.of("Lechuga", "Tomate"));
        receta2.setChef(chef2);

        when(recetaRepository.findAll()).thenReturn(List.of(receta1, receta2));

        List<RecetaDTO> resultados = recetaService.obtenerRecetasPorIngredientes("Camarón");

        assertEquals(1, resultados.size());
        assertEquals("Ceviche", resultados.get(0).getTitulo());
    }

    @Test
    void registrarRecetaTelevidente_exitoso() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Ceviche");
        dto.setIngredientes(List.of("Camarón", "Limón"));
        dto.setPasos(List.of("Mezclar ingredientes"));

        Receta recetaGuardada = new Receta();
        recetaGuardada.setTitulo(dto.getTitulo());
        recetaGuardada.setIngredientes(dto.getIngredientes());
        recetaGuardada.setPasos(dto.getPasos());

        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaGuardada);

        RecetaDTO resultado = recetaService.registrarRecetaTelevidente(dto);

        assertEquals("Televidente", resultado.getTypeChef());
        assertEquals("Ceviche", resultado.getTitulo());

        ArgumentCaptor<Receta> captor = ArgumentCaptor.forClass(Receta.class);
        verify(recetaRepository).save(captor.capture());
        assertEquals("Ceviche", captor.getValue().getTitulo());
    }
    @Test
    void registrarRecetaParticipante_exitoso() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("MangoBiche");
        dto.setIngredientes(List.of("Mago", "Limón"));
        dto.setPasos(List.of("Mezclar ingredientes"));
        dto.setTemporada(8);

        Receta recetaGuardada = new Receta();
        recetaGuardada.setTitulo(dto.getTitulo());
        recetaGuardada.setIngredientes(dto.getIngredientes());
        recetaGuardada.setPasos(dto.getPasos());

        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaGuardada);

        RecetaDTO resultado = recetaService.registrarRecetaParticipante(dto);

        assertEquals("Participante", resultado.getTypeChef());
        assertEquals("MangoBiche", resultado.getTitulo());

        ArgumentCaptor<Receta> captor = ArgumentCaptor.forClass(Receta.class);
        verify(recetaRepository).save(captor.capture());
        assertEquals("MangoBiche", captor.getValue().getTitulo());
    }
    @Test
    void registrarRecetaJurado_exitoso() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Arroz con pollo");
        dto.setIngredientes(List.of("Arroz", "Pollo", "Agua"));
        dto.setPasos(List.of("Cocinar todo por aparte","Mezclar ingredientes"));

        Receta recetaGuardada = new Receta();
        recetaGuardada.setTitulo(dto.getTitulo());
        recetaGuardada.setIngredientes(dto.getIngredientes());
        recetaGuardada.setPasos(dto.getPasos());

        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaGuardada);

        RecetaDTO resultado = recetaService.registrarRecetaJurado(dto);

        assertEquals("Jurado", resultado.getTypeChef());
        assertEquals("Arroz con pollo", resultado.getTitulo());

        ArgumentCaptor<Receta> captor = ArgumentCaptor.forClass(Receta.class);
        verify(recetaRepository).save(captor.capture());
        assertEquals("Arroz con pollo", captor.getValue().getTitulo());
    }

    @Test
    void obtenerRecetaPorId_inexistente_lanzaExcepcion() {
        when(recetaRepository.findById("123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.obtenerRecetaPorId("123"));

        assertEquals("No existe una receta con el ID: 123", exception.getMessage());
    }
}
