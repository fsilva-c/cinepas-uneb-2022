package model.datahora;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

public class AdapterDataHora implements IDataHora {

    private LocalDateTime localDateTime;

    public AdapterDataHora(String datahora) {
        this.localDateTime = LocalDateTime.parse(datahora, this.padronizador("dd/MM/yyyy HH:mm:ss"));
    }

    public AdapterDataHora(String datahora, String pattern) {
        this.localDateTime = LocalDateTime.parse(datahora, this.padronizador(pattern));
    }

    public AdapterDataHora() {
        this.localDateTime = LocalDateTime.now();
    }

    private DateTimeFormatter padronizador(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    public String hora() {
        return localDateTime.format(this.padronizador("HH:mm:ss"));
    }

    public String data() {
        return localDateTime.format(this.padronizador("dd/MM/yyyy"));
    }

    public String datahora() {
        return localDateTime.format(this.padronizador("dd/MM/yyyy HH:mm:ss"));
    }

    public String sqlDateTime() {
        return localDateTime.format(this.padronizador("yyyy-MM-dd HH:mm:ss"));
    }

    public String sqlDate() {
        return localDateTime.format(this.padronizador("yyyy-MM-dd"));
    }

    public boolean maiorQue(String datahora2) {
        LocalDateTime localDateTime2 = LocalDateTime.parse(datahora2, this.padronizador("dd/MM/yyyy HH:mm:ss"));
        return this.localDateTime.isAfter(localDateTime2);
    }

    public String somarMinutos(long minutos) {
        LocalDateTime dataSomada = this.localDateTime.plusMinutes(minutos);
        return dataSomada.format(this.padronizador("dd/MM/yyyy HH:mm:ss"));
    }

    public boolean isFDS() {
        DayOfWeek d = this.localDateTime.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }

}
