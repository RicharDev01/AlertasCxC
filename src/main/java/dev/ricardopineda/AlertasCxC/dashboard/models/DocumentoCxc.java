
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
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author ricardopineda.dev
 * Date: 18 jun. 2025
 */

@Entity
@Data
@Table(name = "documento_cxc")
public class DocumentoCxc implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idDocumento;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_documento_id", nullable = false)
  private TipoDocumento tipoDocumentoId; 
  
  @NotNull
  @Column(name = "numero_documento", length = 50, nullable = false)
  private String numeroDocumento;
  
  @NotNull
  @Column(name = "fecha_factura", nullable = false)
  private LocalDateTime fechaFactura;
  
  @Column(name = "fecha_recepcion")
  private LocalDateTime fechaRecepcion;
  
  @Column(name = "fecha_emision_quedan")
  private LocalDateTime fechaEmisionQuedan;
  
  @NotNull
  @Column(name = "monto", nullable = false)
  private Double monto;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entidad_cobrable_id", nullable = false)
  private EntidadCobrable entidadCobrableId; 
  
  @Column(name = "fecha_vencimiento")
  private LocalDateTime fechaVencimiento; // Debe ser automatica
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estado_documento_id", nullable = false)
  private EstadoDocumento estadoDocumentoId; 
  
  private String observaciones;
  
  @Column(name = "creado_en")
  private LocalDateTime creadoEn = LocalDateTime.now();
  
  @Column(name = "actualizado_en")
  private LocalDateTime actualizadoEn;
  
}
