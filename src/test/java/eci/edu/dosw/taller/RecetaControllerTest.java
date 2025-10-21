package eci.edu.dosw.taller;


import com.fasterxml.jackson.databind.ObjectMapper;
import eci.edu.dosw.taller.controller.RecetaController;
import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.services.RecetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecetaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecetaService recetaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private RecetaDTO recetaDTO;

    @BeforeEach
    void setUp() {
        RecetaController controller = new RecetaController(recetaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        recetaDTO = new RecetaDTO();
        recetaDTO.setId("R-1");
        recetaDTO.setTitulo("Ceviche");
        recetaDTO.setIngredientes(List.of("Camarón", "Limón"));
        recetaDTO.setPasos(List.of("Mezclar ingredientes"));
        recetaDTO.setTypeChef("Televidente");
        recetaDTO.setTemporada(null);
    }

    @Test
    void registrarRecetaTelevidente() throws Exception {
        Mockito.when(recetaService.registrarRecetaTelevidente(any(RecetaDTO.class)))
                .thenReturn(recetaDTO);

        mockMvc.perform(post("/api/recetas/televidente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Ceviche"))
                .andExpect(jsonPath("$.typeChef").value("Televidente"));
    }

    @Test
    void registrarRecetaParticipante() throws Exception {
        recetaDTO.setTypeChef("Participante");
        recetaDTO.setTemporada(1);

        Mockito.when(recetaService.registrarRecetaParticipante(any(RecetaDTO.class)))
                .thenReturn(recetaDTO);

        mockMvc.perform(post("/api/recetas/participante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Ceviche"))
                .andExpect(jsonPath("$.typeChef").value("Participante"))
                .andExpect(jsonPath("$.temporada").value(1));
    }

    @Test
    void registrarRecetaJurado() throws Exception {
        recetaDTO.setTypeChef("Jurado");
        recetaDTO.setTemporada(null);

        Mockito.when(recetaService.registrarRecetaJurado(any(RecetaDTO.class)))
                .thenReturn(recetaDTO);

        mockMvc.perform(post("/api/recetas/jurado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Ceviche"))
                .andExpect(jsonPath("$.typeChef").value("Jurado"));
    }

    @Test
    void obtenerTodasRecetas() throws Exception {
        Mockito.when(recetaService.obtenerTodas()).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Ceviche"));
    }

    @Test
    void obtenerRecetaPorId() throws Exception {
        Mockito.when(recetaService.obtenerRecetaPorId("R-1")).thenReturn(recetaDTO);

        mockMvc.perform(get("/api/recetas/R-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("R-1"))
                .andExpect(jsonPath("$.titulo").value("Ceviche"));
    }

    @Test
    void obtenerRecetasParticipantes() throws Exception {
        recetaDTO.setTypeChef("Participante");
        Mockito.when(recetaService.obtenerRecetasPorParticipantes()).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas/participantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeChef").value("Participante"));
    }

    @Test
    void obtenerRecetasTelevidentes() throws Exception {
        recetaDTO.setTypeChef("Televidente");
        Mockito.when(recetaService.obtenerRecetasPorTelevidentes()).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas/televidentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeChef").value("Televidente"));
    }

    @Test
    void obtenerRecetasJurados() throws Exception {
        recetaDTO.setTypeChef("Jurado");
        Mockito.when(recetaService.obtenerRecetasPorJurados()).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas/jurados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeChef").value("Jurado"));
    }

    @Test
    void obtenerRecetasPorTemporada() throws Exception {
        recetaDTO.setTypeChef("Participante");
        recetaDTO.setTemporada(1);
        Mockito.when(recetaService.obtenerRecetasPorTemporadas(1)).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas/temporada/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].temporada").value(1));
    }

    @Test
    void obtenerRecetasPorIngrediente() throws Exception {
        Mockito.when(recetaService.obtenerRecetasPorIngredientes("Camarón")).thenReturn(List.of(recetaDTO));

        mockMvc.perform(get("/api/recetas/ingrediente/Camarón"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredientes[0]").value("Camarón"));
    }

    @Test
    void eliminarReceta() throws Exception {
        Mockito.doNothing().when(recetaService).eliminarReceta("R-1");

        mockMvc.perform(delete("/api/recetas/R-1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Receta eliminada correctamente"));
    }

    @Test
    void actualizarReceta() throws Exception {
        Mockito.when(recetaService.actualizarReceta(eq("R-1"), any(RecetaDTO.class)))
                .thenReturn(recetaDTO);

        mockMvc.perform(put("/api/recetas/R-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Ceviche"));
    }
}
