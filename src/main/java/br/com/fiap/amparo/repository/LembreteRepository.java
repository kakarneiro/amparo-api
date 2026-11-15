package br.com.fiap.amparo.repository;

import br.com.fiap.amparo.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
}
