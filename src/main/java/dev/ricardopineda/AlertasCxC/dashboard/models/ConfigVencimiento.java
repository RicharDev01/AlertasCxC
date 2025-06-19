
package dev.ricardopineda.AlertasCxC.dashboard.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

/**
 *-- Configuración de prórrogas por tipo de documento y entidad
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */
@Entity
@Data
@Table(name = "config_vencimiento")
public class ConfigVencimiento implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotNull
  @Column(name = "entidad_cobrable_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entidad_cobrable_id", nullable = false)
  private Long entidadCobrableId;
  
  @NotNull
  @Column(name = "dias_prorroga", nullable = false)
  private Integer diasProrroga;
  
}
