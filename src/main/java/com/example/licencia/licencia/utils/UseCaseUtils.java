package com.example.licencia.licencia.utils;

import com.example.licencia.licencia.models.Titular;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;

@Component
@NoArgsConstructor
public class UseCaseUtils {



    public LocalDate calcularVigencia(Titular titular) {
        LocalDate hoy = LocalDate.now();
        int edad = Period.between(titular.getFechaNacimiento(), hoy).getYears();
        int añosVigencia;

        // Determinar años de vigencia
        if (edad < 21) {
            añosVigencia = Boolean.TRUE.equals(titular.getPrimeraLicencia()) ? 1 : 3;
        } else if (edad <= 46) {
            añosVigencia = 5;
        } else if (edad <= 60) {
            añosVigencia = 4;
        } else if (edad <= 70) {
            añosVigencia = 3;
        } else {
            añosVigencia = 1;
        }

        // Sumar los años a la fecha actual
        LocalDate vencimientoTentativo = hoy.plusYears(añosVigencia);

        // Ajustar día y mes al de la fecha de nacimiento
        int diaNacimiento = titular.getFechaNacimiento().getDayOfMonth();
        int mesNacimiento = titular.getFechaNacimiento().getMonthValue();

        // Corregir si el día no existe en ese mes (e.g., 30/02)
        int ultimoDiaMes = YearMonth.of(vencimientoTentativo.getYear(), mesNacimiento).lengthOfMonth();
        int diaVencimiento = Math.min(diaNacimiento, ultimoDiaMes);

        LocalDate vencimiento = LocalDate.of(
                vencimientoTentativo.getYear(),
                mesNacimiento,
                diaVencimiento
        );

        // Si la fecha de vencimiento calculada es antes que hoy, restar un año
        if (vencimiento.isBefore(hoy)) {
            vencimiento = vencimiento.minusYears(1);
        }

        return vencimiento;
    }

    public double calcularCostoLicencia (String clase, int vigenciaAnios) {
        double[][] costos = {
                {40, 30, 25, 20}, // Clase A
                {40, 30, 25, 20}, // Clase B
                {47, 35, 30, 23}, // Clase C
                {59, 44, 39, 29}, // Clase E
                {40, 30, 25, 20}  // Clase G
        };

        // Mapeo de clases a índices de fila
        String[] clasesDisponibles = {"A", "B", "C", "E", "G"};
        int indiceFila = -1;
        for (int i = 0; i < clasesDisponibles.length; i++) {
            if (clasesDisponibles[i].equals(clase)) {
                indiceFila = i;
                break;
            }
        }

        if (indiceFila == -1) {
            System.out.println("Error: Clase de licencia '" + clase + "' no válida.");
            return -1;
        }

        int indiceColumna = -1;
        switch (vigenciaAnios) {
            case 5:
                indiceColumna = 0;
                break;
            case 4:
                indiceColumna = 1;
                break;
            case 3:
                indiceColumna = 2;
                break;
            case 1:
                indiceColumna = 3;
                break;
            default:
                System.out.println("Error: Vigencia en años '" + vigenciaAnios + "' no válida. Las vigencias permitidas son 1, 3, 4 o 5 años.");
                return -1;
        }

        double costoBase = costos[indiceFila][indiceColumna];
        double gastosAdministrativos = 8.0;

        return costoBase + gastosAdministrativos;
    }

    public int calcularVigenciaEnAnios(LocalDate fechaEmision, LocalDate fechaVencimiento) {

        Period period = Period.between(fechaVencimiento, fechaEmision);
        return period.getYears();
    }

}
