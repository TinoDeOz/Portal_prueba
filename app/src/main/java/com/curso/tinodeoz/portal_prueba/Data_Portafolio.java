package com.curso.tinodeoz.portal_prueba;

public class Data_Portafolio {

    //private String id;
    private String Distrito;
    private String Juzgado;
    private String Expediente;
    private String Ubicacion;
    private String Fecha;
    private String IDJuzgado;

    public String getIDJuzgado() {
        return IDJuzgado;
    }

    public void setIDJuzgado(String IDJuzgado) {
        this.IDJuzgado = IDJuzgado;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getJuzgado() {
        return Juzgado;
    }

    public void setJuzgado(String juzgado) {
        Juzgado = juzgado;
    }

    public String getExpediente() {
        return Expediente;
    }

    public void setExpediente(String expediente) {
        Expediente = expediente;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
