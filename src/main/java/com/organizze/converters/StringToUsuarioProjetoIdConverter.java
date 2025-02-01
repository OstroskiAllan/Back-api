package com.organizze.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.organizze.model.usuario_projeto.UsuarioProjetoId;

@Component
public class StringToUsuarioProjetoIdConverter implements Converter<String, UsuarioProjetoId> {

    @Override
    public UsuarioProjetoId convert(String source) {
        String[] parts = source.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for UsuarioProjetoId");
        }
        Long projetoId = Long.valueOf(parts[0]);
        Long usuarioId = Long.valueOf(parts[1]);
        return new UsuarioProjetoId(projetoId, usuarioId);
    }
}