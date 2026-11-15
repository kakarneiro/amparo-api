package br.com.fiap.amparo.config;

import br.com.fiap.amparo.model.Papel;
import br.com.fiap.amparo.model.Usuario;
import br.com.fiap.amparo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Usuarios de teste por papel (so no perfil dev).
 * A senha e codificada com BCrypt em tempo de execucao, por isso nao vai no data.sql.
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        criar("Administrador", "admin@amparo.com", "admin123", Papel.ADMIN);
        criar("Ana Paula (cuidadora)", "ana@amparo.com", "ana123", Papel.CUIDADOR);
    }

    private void criar(String nome, String email, String senha, Papel papel) {
        if (usuarioRepository.existsByEmail(email)) return;
        usuarioRepository.save(Usuario.builder()
                .nome(nome).email(email)
                .senha(passwordEncoder.encode(senha))
                .papel(papel).ativo(true).build());
    }
}
