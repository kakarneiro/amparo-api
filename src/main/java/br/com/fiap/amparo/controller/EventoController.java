package br.com.fiap.amparo.controller;

import br.com.fiap.amparo.dto.AtendimentoResponse;
import br.com.fiap.amparo.dto.EventoFiltro;
import br.com.fiap.amparo.dto.EventoRequest;
import br.com.fiap.amparo.dto.EventoResponse;
import br.com.fiap.amparo.dto.EventoStatusRequest;
import br.com.fiap.amparo.service.AtendimentoService;
import br.com.fiap.amparo.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;
    private final AtendimentoService atendimentoService;

    /** GET /eventos?status=&tipo=&cidadaoId=&inicio=&fim=&page=&size=&sort= */
    @GetMapping
    public Page<EventoResponse> listar(
            EventoFiltro filtro,
            @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.listar(filtro, pageable);
    }

    /** Linha do tempo de acionamentos do evento. */
    @GetMapping("/{id}/atendimentos")
    public List<AtendimentoResponse> atendimentos(@PathVariable Long id) {
        return atendimentoService.listarPorEvento(id);
    }

    /** Cuidador atual nao respondeu: aciona o proximo da cadeia. */
    @PostMapping("/{id}/escalar")
    public EventoResponse escalar(@PathVariable Long id) {
        return service.escalar(id);
    }

    @GetMapping("/{id}")
    public EventoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** Endpoint que o ESP32 chama. */
    @PostMapping
    public ResponseEntity<EventoResponse> registrar(@RequestBody @Valid EventoRequest req) {
        EventoResponse criado = service.registrar(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public EventoResponse atualizar(@PathVariable Long id, @RequestBody @Valid EventoRequest req) {
        return service.atualizar(id, req);
    }

    @PatchMapping("/{id}/status")
    public EventoResponse alterarStatus(@PathVariable Long id, @RequestBody @Valid EventoStatusRequest req) {
        return service.alterarStatus(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
