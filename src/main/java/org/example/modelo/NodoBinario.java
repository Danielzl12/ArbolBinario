package org.example.modelo;

// Representa cada elemento del árbol binario
public class NodoBinario<T> {

    public T valor;
    public NodoBinario<T> hijoIzquierdo;
    public NodoBinario<T> hijoDerecho;

    public NodoBinario(T valor) {
        this.valor = valor;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }
}
