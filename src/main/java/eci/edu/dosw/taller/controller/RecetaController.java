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
        RecetaDTO creada = recetaService.registrarRecetaTelevidente(recetaDTO);
        return ResponseEntity.ok(creada);
    }

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> obtenerTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }
}
