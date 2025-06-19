
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
 *-- Estados posibles del documento (Pendiente, despachado, 
 * pagado, anulado)
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */
@Entity
@Data
@Table(name = "estado_documento")
public class EstadoDocumento implements Serializable{

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idEstadoDoc;
  
  @NotNull
  @Column(name = "nombre", length = 30, unique = true, nullable = false)
  private String nombreEstado;
  
  
}
