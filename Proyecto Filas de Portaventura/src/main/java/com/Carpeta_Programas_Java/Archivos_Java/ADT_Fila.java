package com.Carpeta_Programas_Java.Archivos_Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ADT_Fila {


    //---------------DECLARACIÓN VARIABLES-------------
    private int maximo;
    private ArrayList<String> filaGeneral;
    private ArrayList<String> filaVIP;

    // Lista fija de nombres VIP
    private final List<String> listaVIP = Arrays.asList(
            "Carlos", "Paco", "Juan", "Manolo", "José",
            "Mario", "Jorge", "Manuel", "Marcos", "Luis"
    );

    //---------------CREACIÓN VARIABLES ADT----------------
    public ADT_Fila(int maximo_atraccion) {
        this.maximo = maximo_atraccion;
        this.filaGeneral = new ArrayList<>();
        this.filaVIP = new ArrayList<>();
    }

    //myADT_Fila -> FUNCIONES ¡FIFO! TODAS SON ARRAYS LIST
    //------------------Funciones---------------------------
    //FILA ENCOLAR
    public void encolarVisitante(String nombre) {
        if (listaVIP.contains(nombre)) {
            filaVIP.add(nombre);
        } else {
            filaGeneral.add(nombre);
        }
    }

    //FILA ENCOLAR NOMBRE
    public void esVIP(String nombre) {
        if(listaVIP.contains(nombre)){
            System.out.println(nombre + " ------> es VIP");
        }else {
            System.out.println(nombre + " ------> no es VIP");
        }
    }

    //TAMAÑO FILA TOTAL
    public int tamañoTotal() {
        int total = filaVIP.size() + filaGeneral.size();
        return total;
    }

    //FILA LLENAR
    public List<String> llenarAtraccion() {
        ArrayList<String> genteASubir = new ArrayList<>();
        while (genteASubir.size() < maximo && (!filaVIP.isEmpty() || !filaGeneral.isEmpty())) {
            if (!filaVIP.isEmpty()) {
                genteASubir.add(filaVIP.remove(0)); // VIP tiene prioridad
            } else if (!filaGeneral.isEmpty()) {
                genteASubir.add(filaGeneral.remove(0));
            }
        }
        return genteASubir;
    }

    //MOSTRAR FILA VIP Y FILA GENERAL
    public void mostrarFilas() {
        System.out.println("Fila VIP: " + filaVIP);
        System.out.println("Fila General: " + filaGeneral);
    }


    //------------------FIN  Funciones---------------------------
}
