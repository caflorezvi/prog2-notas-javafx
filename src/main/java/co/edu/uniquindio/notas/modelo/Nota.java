package co.edu.uniquindio.notas.modelo;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Nota {

    @EqualsAndHashCode.Include
    private String id;

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String categoria;
    private LocalDateTime recordatorio;

    /**
     * Obtiene la diferencia entre la fecha actual y la fecha de recordatorio
     * @return Diferencia en días, horas, minutos y segundos
     */
    public String obtenerDiferencia() {
        LocalDateTime ahora = LocalDateTime.now();
        long diferencia = Duration.between(ahora, recordatorio).getSeconds();
        long dias = diferencia / 86400;
        long horas = (diferencia % 86400) / 3600;
        long minutos = ((diferencia % 86400) % 3600) / 60;
        long segundos = ((diferencia % 86400) % 3600) % 60;
        return dias + " días, " + horas + " horas, " + minutos + " minutos, " + segundos + " segundos";
    }
}
