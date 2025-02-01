package com.organizze.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.organizze.converters.StringToUsuarioProjetoIdConverter;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final StringToUsuarioProjetoIdConverter stringToUsuarioProjetoIdConverter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Permitir solicitações do frontend Angular em localhost:4200
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permitir todos os métodos HTTP
                .allowedHeaders("*"); // Permitir todos os cabeçalhos
    }

    public WebConfig(StringToUsuarioProjetoIdConverter stringToUsuarioProjetoIdConverter) {
        this.stringToUsuarioProjetoIdConverter = stringToUsuarioProjetoIdConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUsuarioProjetoIdConverter);
    }
}
