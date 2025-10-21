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
    @Test
    void obtenerTodasRecetas() {
        Receta receta1 = new Receta();
        receta1.setTitulo("Ceviche");
        receta1.setIngredientes(List.of("Camarón"));
        receta1.setChef(new Participante("P-1", "Juan", "Cocina de mar", 1));

        Receta receta2 = new Receta();
        receta2.setTitulo("Ensalada");
        receta2.setIngredientes(List.of("Lechuga"));
        receta2.setChef(new Participante("P-2", "Ana", "Vegetariana", 2));

        when(recetaRepository.findAll()).thenReturn(List.of(receta1, receta2));

        List<RecetaDTO> resultados = recetaService.obtenerTodas();

        assertEquals(2, resultados.size());
        assertEquals("Ceviche", resultados.get(0).getTitulo());
        assertEquals("Ensalada", resultados.get(1).getTitulo());
    }

    @Test
    void obtenerRecetasPorParticipantes() {
        Receta receta = new Receta();
        receta.setTitulo("Ceviche");
        receta.setChef(new Participante("P-1", "Juan", "Cocina de mar", 1));
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<RecetaDTO> resultados = recetaService.obtenerRecetasPorParticipantes();

        assertEquals(1, resultados.size());
        assertEquals("Ceviche", resultados.get(0).getTitulo());
    }

    @Test
    void obtenerRecetasPorTelevidentes() {
        Receta receta = new Receta();
        receta.setTitulo("Sopa de verduras");
        receta.setChef(new eci.edu.dosw.taller.models.Televidente("T-1", "Luis", "Cocina casera"));
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<RecetaDTO> resultados = recetaService.obtenerRecetasPorTelevidentes();

        assertEquals(1, resultados.size());
        assertEquals("Sopa de verduras", resultados.get(0).getTitulo());
    }

    @Test
    void obtenerRecetasPorJurados() {
        Receta receta = new Receta();
        receta.setTitulo("Pastel de chocolate");
        receta.setChef(new eci.edu.dosw.taller.models.Jurado("J-1", "Ana", "Repostería"));
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<RecetaDTO> resultados = recetaService.obtenerRecetasPorJurados();

        assertEquals(1, resultados.size());
        assertEquals("Pastel de chocolate", resultados.get(0).getTitulo());
    }

    @Test
    void obtenerRecetasPorTemporada() {
        Receta receta = new Receta();
        Participante chef = new Participante("P-1", "Juan", "Cocina de mar", 3);
        receta.setChef(chef);
        receta.setTitulo("Ceviche");
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<RecetaDTO> resultados = recetaService.obtenerRecetasPorTemporadas(3);

        assertEquals(1, resultados.size());
        assertEquals("Ceviche", resultados.get(0).getTitulo());
    }

    @Test
    void eliminarReceta_exitoso() {
        when(recetaRepository.existsById("R-1")).thenReturn(true);
        doNothing().when(recetaRepository).deleteById("R-1");

        assertDoesNotThrow(() -> recetaService.eliminarReceta("R-1"));
        verify(recetaRepository).deleteById("R-1");
    }

    @Test
    void actualizarReceta_exitoso() {
        Receta recetaExistente = new Receta();
        Participante chef = new Participante("P-1", "Juan", "Cocina de mar", 1);
        recetaExistente.setChef(chef);
        recetaExistente.setTitulo("Ceviche");
        recetaExistente.setIngredientes(List.of("Camarón"));
        recetaExistente.setPasos(List.of("Mezclar"));

        RecetaDTO recetaActualizada = new RecetaDTO();
        recetaActualizada.setTitulo("Ceviche Mejorado");
        recetaActualizada.setIngredientes(List.of("Camarón", "Limón"));
        recetaActualizada.setPasos(List.of("Mezclar ingredientes"));
        recetaActualizada.setTemporada(1);

        when(recetaRepository.findById("R-1")).thenReturn(Optional.of(recetaExistente));
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> i.getArguments()[0]);

        RecetaDTO resultado = recetaService.actualizarReceta("R-1", recetaActualizada);

        assertEquals("Ceviche Mejorado", resultado.getTitulo());
        assertEquals(2, resultado.getIngredientes().size());
        assertEquals(1, resultado.getTemporada());
    }
    @Test
    void registrarRecetaTelevidente_conTypeChefInvalido_lanzaExcepcion() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Error");
        dto.setTypeChef("Participante");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.registrarRecetaTelevidente(dto));

        assertEquals("Solo se pueden registrar recetas de televidentes en este endpoint.", exception.getMessage());
    }

    @Test
    void registrarRecetaParticipante_sinTemporada_lanzaExcepcion() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Error");
        dto.setTypeChef("Participante");
        dto.setTemporada(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.registrarRecetaParticipante(dto));

        assertEquals("La temporada es obligatoria para un participante.", exception.getMessage());
    }

    @Test
    void registrarRecetaJurado_conTemporada_lanzaExcepcion() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Error");
        dto.setTypeChef("Jurado");
        dto.setTemporada(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.registrarRecetaJurado(dto));

        assertEquals("Un jurado no tiene temporada asignada.", exception.getMessage());
    }
    @Test
    void actualizarReceta_inexistente_lanzaExcepcion() {
        RecetaDTO dto = new RecetaDTO();
        dto.setTitulo("Error");

        when(recetaRepository.findById("R-999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.actualizarReceta("R-999", dto));

        assertEquals("No existe una receta con el ID: R-999", exception.getMessage());
    }

    @Test
    void actualizarReceta_idModificado_lanzaExcepcion() {
        RecetaDTO recetaActualizada = new RecetaDTO();
        recetaActualizada.setId("R-2");
        recetaActualizada.setTitulo("Error");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.actualizarReceta("R-1", recetaActualizada));

        assertEquals("No se puede modificar el ID de la receta", exception.getMessage());
    }
    @Test
    void eliminarReceta_inexistente_lanzaExcepcion() {
        when(recetaRepository.existsById("R-999")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recetaService.eliminarReceta("R-999"));

        assertEquals("No existe una receta con el ID: R-999", exception.getMessage());
    }


}
