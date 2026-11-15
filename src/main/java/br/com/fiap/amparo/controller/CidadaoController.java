package br.com.fiap.amparo.controller;

import br.com.fiap.amparo.dto.*;
import br.com.fiap.amparo.service.CuidadorService;
import br.com.fiap.amparo.service.EventoService;
import br.com.fiap.amparo.dto.CidadaoRequest;
import br.com.fiap.amparo.dto.CidadaoResponse;
import br.com.fiap.amparo.service.CidadaoService;
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
@RequestMapping("/cidadaos")
@RequiredArgsConstructor
public class CidadaoController {

    private final CidadaoService service;
    private final CuidadorService cuidadorService;
    private final EventoService eventoService;

    /** GET /cidadaos?nome=&condicao=&ativo=&page=&size=&sort= (filtros via Specification) */
    @GetMapping
    public Page<CidadaoResponse> listar(
            CidadaoFiltro filtro,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.listar(filtro, pageable);
    }

    /** Cadeia de cuidadores do cidadao, em ordem de prioridade. */
    @GetMapping("/{id}/cuidadores")
    public List<CuidadorResponse> cuidadores(@PathVariable Long id) {
        return cuidadorService.listarPorCidadao(id);
    }

    /** Historico de eventos do cidadao (@Query JPQL). */
    @GetMapping("/{id}/eventos")
    public Page<EventoResponse> eventos(@PathVariable Long id,
            @PageableDefault(size = 10) Pageable pageable) {
        return eventoService.listarPorCidadao(id, pageable);
    }

    @GetMapping("/{id}")
    public CidadaoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<CidadaoResponse> criar(@RequestBody @Valid CidadaoRequest req) {
        CidadaoResponse criado = service.criar(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public CidadaoResponse atualizar(@PathVariable Long id, @RequestBody @Valid CidadaoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
