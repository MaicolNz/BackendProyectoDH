package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.UsuarioEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import com.PI.Back.PIBackend.services.IUsuarioService;
import com.PI.Back.PIBackend.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Boolean alquilarInstrumento(InstrumentoSalidaDto instrumentoSalidaDto) {
        return null;
    }

    @Override
    public Boolean devolverInstrumento(InstrumentoSalidaDto instrumentoSalidaDto) {
        return null;
    }

    //Agregado para pruebas
    @Override
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) {

        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);
        UsuarioSalidaDto usuarioEncontrado = null;

        if (usuarioBuscado != null){
            usuarioEncontrado = modelMapper.map(usuarioBuscado, UsuarioSalidaDto.class);
            LOGGER.info("Usuario encontrado: {}", JsonPrinter.toString(usuarioEncontrado));
        } else LOGGER.error("Usuario no encontrado: {}", JsonPrinter.toString(usuarioBuscado));

        return usuarioEncontrado;
    }

    @Override
    @Transactional
    @Secured("ROLE_USUARIO")
    public UsuarioSalidaDto modificarUsuario(UsuarioEntradaDto usuarioEntradaDto, Long id) throws ResourceNotFoundException {

        Usuario usuarioIngresado = modelMapper.map(usuarioEntradaDto, Usuario.class);
        Usuario usuarioActualizar = usuarioRepository.findById(id).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto;

        if (usuarioActualizar != null){
            // Solo actualiza los campos que no son nulos en usuarioEntradaDto
            if (usuarioEntradaDto.getEmail() != null) {
                usuarioActualizar.setEmail(usuarioEntradaDto.getEmail());
            }
            if (usuarioEntradaDto.getCelular() != null) {
                usuarioActualizar.setCelular(usuarioEntradaDto.getCelular());
            }
            if (usuarioEntradaDto.getDireccion() != null) {
                usuarioActualizar.setDireccion(usuarioEntradaDto.getDireccion());
            }

            // Log antes de guardar
            LOGGER.info("Actualizando usuario con ID: " + id + " con los nuevos datos: " + usuarioActualizar);

            usuarioRepository.save(usuarioActualizar);

            // Log después de guardar
            LOGGER.info("Usuario con ID: " + id + " actualizado exitosamente.");

            usuarioSalidaDto = modelMapper.map(usuarioActualizar, UsuarioSalidaDto.class);
        } else {
            LOGGER.error("No fue posible actualizar los datos del usuario ya que no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No es posible actualizar el usuario con ID: " + id + "ya que no se encuentra en la base de datos");
        }

        return usuarioSalidaDto;
    }
}
