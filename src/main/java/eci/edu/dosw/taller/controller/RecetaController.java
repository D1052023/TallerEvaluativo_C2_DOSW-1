package eci.edu.dosw.taller.controller;

import eci.edu.dosw.taller.dtos.RecetaDTO;
import eci.edu.dosw.taller.services.RecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping("/televidente")
    public ResponseEntity<RecetaDTO> registrarRecetaTelevidente(@RequestBody RecetaDTO recetaDTO) {
        return ResponseEntity.ok(recetaService.registrarRecetaTelevidente(recetaDTO));
    }

    @PostMapping("/participante")
    public ResponseEntity<RecetaDTO> registrarRecetaParticipante(@RequestBody RecetaDTO recetaDTO) {
        return ResponseEntity.ok(recetaService.registrarRecetaParticipante(recetaDTO));
    }

    @PostMapping("/jurado")
    public ResponseEntity<RecetaDTO> registrarRecetaJurado(@RequestBody RecetaDTO recetaDTO) {
        return ResponseEntity.ok(recetaService.registrarRecetaJurado(recetaDTO));
    }

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> obtenerTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(recetaService.obtenerRecetaPorId(id));
    }
    @GetMapping("/participantes")
    public ResponseEntity<List<RecetaDTO>> obtenerRecetasParticipantes() {
        return ResponseEntity.ok(recetaService.obtenerRecetasPorParticipantes());
    }
    @GetMapping("/televidentes")
    public ResponseEntity<List<RecetaDTO>> obtenerRecetasTelevidentes() {
        return ResponseEntity.ok(recetaService.obtenerRecetasPorTelevidentes());
    }
    @GetMapping("/jurados")
    public ResponseEntity<List<RecetaDTO>> obtenerRecetasJurados() {
        return ResponseEntity.ok(recetaService.obtenerRecetasPorJurados());
    }


}
