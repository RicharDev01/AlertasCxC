
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
 *-- Tipos de documento (Ej: Factura, CCF, Quedan)
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */
@Entity
@Data
@Table(name = "tipo_documento")
public class TipoDocumento implements Serializable {
  
  @Id
  @Column(name = "id_tipo_documento")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idTipoDoc;
  
  @NotNull
  @Column(name = "nombre", length = 30, unique = true, nullable = false)
  private String nombre;

  
}
