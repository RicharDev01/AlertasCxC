
package dev.ricardopineda.AlertasCxC.auth.models;

/**
 *
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();
}

