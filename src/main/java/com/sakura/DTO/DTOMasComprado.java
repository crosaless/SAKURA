
package com.sakura.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOMasComprado {
    
    private LocalDate fechaDesde;
    
    private LocalDate fechaHasta;
}
