package edu.msmk.clases.exchange;

// DTO (Data Transfer Object) para encapsular una dirección.
public class PeticionCliente {
    private final int provincia;
    private final int municipio;
    private final int unidadPoblacional;
    private final int via;

    // Constructor simplificado a 4 argumentos
    public PeticionCliente(int provincia, int municipio, int unidadPoblacional, int via) {
        this.provincia = provincia;
        this.municipio = municipio;
        this.unidadPoblacional = unidadPoblacional;
        this.via = via;
    }

    public int getProvincia() {
        return provincia;
    }

    public int getMunicipio() {
        return municipio;
    }

    public int getUnidadPoblacional() {
        return unidadPoblacional;
    }

    public int getVia() {
        return via;
    }
}