package org.example.logica;

import org.example.modelo.NodoBinario;
import java.util.LinkedList;
import java.util.Queue;

// Contiene toda la lógica del árbol binario
public class ArbolBinario<T> {

    private NodoBinario<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public NodoBinario<T> obtenerRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // Inserta un nuevo valor nivel por nivel (de izquierda a derecha)
    public void insertar(T valor) {
        NodoBinario<T> nuevoNodo = new NodoBinario<>(valor);
        if (raiz == null) {
            raiz = nuevoNodo;
            return;
        }
        Queue<NodoBinario<T>> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            NodoBinario<T> actual = cola.poll();
            if (actual.hijoIzquierdo == null) {
                actual.hijoIzquierdo = nuevoNodo;
                return;
            } else {
                cola.add(actual.hijoIzquierdo);
            }
            if (actual.hijoDerecho == null) {
                actual.hijoDerecho = nuevoNodo;
                return;
            } else {
                cola.add(actual.hijoDerecho);
            }
        }
    }

    // Recorrido in-orden: izquierdo → raíz → derecho
    public String recorridoInOrden() {
        StringBuilder resultado = new StringBuilder();
        inOrdenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void inOrdenRecursivo(NodoBinario<T> nodo, StringBuilder resultado) {
        if (nodo == null) return;
        inOrdenRecursivo(nodo.hijoIzquierdo, resultado);
        resultado.append(nodo.valor).append("  ");
        inOrdenRecursivo(nodo.hijoDerecho, resultado);
    }

    // Recorrido pre-orden: raíz → izquierdo → derecho
    public String recorridoPreOrden() {
        StringBuilder resultado = new StringBuilder();
        preOrdenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void preOrdenRecursivo(NodoBinario<T> nodo, StringBuilder resultado) {
        if (nodo == null) return;
        resultado.append(nodo.valor).append("  ");
        preOrdenRecursivo(nodo.hijoIzquierdo, resultado);
        preOrdenRecursivo(nodo.hijoDerecho, resultado);
    }

    // Recorrido post-orden: izquierdo → derecho → raíz
    public String recorridoPostOrden() {
        StringBuilder resultado = new StringBuilder();
        postOrdenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void postOrdenRecursivo(NodoBinario<T> nodo, StringBuilder resultado) {
        if (nodo == null) return;
        postOrdenRecursivo(nodo.hijoIzquierdo, resultado);
        postOrdenRecursivo(nodo.hijoDerecho, resultado);
        resultado.append(nodo.valor).append("  ");
    }

    // El grado es el número máximo de hijos que tiene cualquier nodo
    public int calcularGrado() {
        return gradoRecursivo(raiz);
    }

    private int gradoRecursivo(NodoBinario<T> nodo) {
        if (nodo == null) return 0;
        int hijosDeEsteNodo = 0;
        if (nodo.hijoIzquierdo != null) hijosDeEsteNodo++;
        if (nodo.hijoDerecho != null) hijosDeEsteNodo++;
        int gradoRamaIzquierda = gradoRecursivo(nodo.hijoIzquierdo);
        int gradoRamaDerecha = gradoRecursivo(nodo.hijoDerecho);
        return Math.max(hijosDeEsteNodo, Math.max(gradoRamaIzquierda, gradoRamaDerecha));
    }

    // Devuelve el nivel del nodo con el valor dado (la raíz está en nivel 0)
    public int calcularNivel(T valorBuscado) {
        return nivelRecursivo(raiz, valorBuscado, 0);
    }

    private int nivelRecursivo(NodoBinario<T> nodo, T valorBuscado, int nivelActual) {
        if (nodo == null) return -1;
        if (nodo.valor.equals(valorBuscado)) return nivelActual;
        int nivelEnRamaIzquierda = nivelRecursivo(nodo.hijoIzquierdo, valorBuscado, nivelActual + 1);
        if (nivelEnRamaIzquierda != -1) return nivelEnRamaIzquierda;
        return nivelRecursivo(nodo.hijoDerecho, valorBuscado, nivelActual + 1);
    }

    // La altura es el nivel del nodo más profundo del árbol
    public int calcularAltura() {
        return alturaRecursiva(raiz);
    }

    private int alturaRecursiva(NodoBinario<T> nodo) {
        if (nodo == null) return -1;
        int alturaRamaIzquierda = alturaRecursiva(nodo.hijoIzquierdo);
        int alturaRamaDerecha = alturaRecursiva(nodo.hijoDerecho);
        return 1 + Math.max(alturaRamaIzquierda, alturaRamaDerecha);
    }

    // BFS: recorre el árbol nivel por nivel, de izquierda a derecha
    public String busquedaAmplitud() {
        if (raiz == null) return "";
        StringBuilder resultado = new StringBuilder();
        Queue<NodoBinario<T>> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            NodoBinario<T> actual = cola.poll();
            resultado.append(actual.valor).append("  ");
            if (actual.hijoIzquierdo != null) cola.add(actual.hijoIzquierdo);
            if (actual.hijoDerecho != null) cola.add(actual.hijoDerecho);
        }
        return resultado.toString().trim();
    }
}
