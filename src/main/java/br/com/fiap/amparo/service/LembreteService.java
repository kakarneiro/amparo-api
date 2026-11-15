package br.com.fiap.amparo.service;

import br.com.fiap.amparo.dto.LembreteRequest;
import br.com.fiap.amparo.dto.LembreteResponse;
import br.com.fiap.amparo.exception.ResourceNotFoundException;
import br.com.fiap.amparo.model.Lembrete;
import br.com.fiap.amparo.repository.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LembreteService {

    private final LembreteRepository repository;
    private final CidadaoService cidadaoService;

    @Transactional(readOnly = true)
    public Page<LembreteResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(LembreteResponse::from);
    }

    @Transactional(readOnly = true)
    public LembreteResponse buscar(Long id) {
        return LembreteResponse.from(obter(id));
    }

    @Transactional
    public LembreteResponse criar(LembreteRequest req) {
        Lembrete l = new Lembrete();
        aplicar(l, req);
        return LembreteResponse.from(repository.save(l));
    }

    @Transactional
    public LembreteResponse atualizar(Long id, LembreteRequest req) {
        Lembrete l = obter(id);
        aplicar(l, req);
        return LembreteResponse.from(repository.save(l));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    @Transactional(readOnly = true)
    public Lembrete obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lembrete", id));
    }

    private void aplicar(Lembrete l, LembreteRequest req) {
        l.setTitulo(req.titulo());
        l.setDescricao(req.descricao());
        l.setHorario(req.horario());
        l.setAtivo(req.ativo() != null ? req.ativo() : Boolean.TRUE);
        l.setCidadao(cidadaoService.obter(req.cidadaoId()));
    }
}
