
package dev.ricardopineda.AlertasCxC.dashboard.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

/**
 *-- Entidades que reciben crédito: aseguradoras, médicos, embajadas, etc.
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */
@Entity
@Data
@Table(name = "entidad_cobrable")
public class EntidadCobrable implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idEntidadCobrable;
  
  @NotNull
  @Column(name = "nombre", length = 150, unique = true, nullable = false)
  private String nombreEntidadCobrable;
  
  @Column(name = "telefono", length = 20)
  private String telefono;
  
  @Column(name = "correo", length = 100)
  private String correo;
  
  private String direccion;
  
}
