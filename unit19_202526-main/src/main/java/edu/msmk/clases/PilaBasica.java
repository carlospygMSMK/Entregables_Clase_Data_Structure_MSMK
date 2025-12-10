package edu.msmk.clases;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class PilaBasica {
    /// // Almacenamiento: Usamos un List para implementar la ADT Pila
    private final List<Integer> elementos;

    public PilaBasica(){
        this.elementos = new ArrayList<>();
    }

    /// // Comprueba si la pila está vacía (O(1))
    public boolean isEmpty(){
        return elementos.isEmpty();
    }

    /// // Añade un elemento a la parte superior (O(1))
    public void push(int elemento){
        elementos.add(elemento);
    }

    /// // Remueve y devuelve el elemento superior (O(1))
    public int pop(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.remove(elementos.size() - 1);
    }

    /// // Devuelve el elemento superior sin removerlo (O(1))
    public int top(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.get(elementos.size() - 1);
    }
}
