package com.test.creative;

import com.test.creative.domain.enums.Perfil;
import com.test.creative.dto.RequestUsuarioDto;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.service.UsuarioService;
import com.test.creative.util.TokenUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CreativeDriveTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreativeDriveTestApplication.class, args);
	}

	@Bean
	public TokenUtil tokenUtilBean() {
		return new TokenUtil();
	}

	@Bean
	CommandLineRunner initMongo(UsuarioService usuarioService){
		return args -> {
			try {
				usuarioService.getUsuarioPorEmail("admin@email.com");
			}catch (RegraNegocioException regraNegocioException){
				var usuario = new RequestUsuarioDto(null, "ADMIN",
						"admin@email.com", "123", null, null, Perfil.ADMIN);
				usuarioService.criarUsuario(usuario);
			}
		};
	}
}
