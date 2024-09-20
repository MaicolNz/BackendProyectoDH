package com.PI.Back.PIBackend.dto.salida;

import com.PI.Back.PIBackend.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalidaDto {

    private long id;;

    private String nombre;

    private String apellido;

    private String email;

    private Role role;
    private String direccion;
    private String celular;

}
