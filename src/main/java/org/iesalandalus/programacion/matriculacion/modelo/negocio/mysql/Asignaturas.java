package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;


public class Asignaturas implements IAsignaturas {
    private final ArrayList<Asignatura> coleccionAsignaturas;

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    public ArrayList<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }

    private ArrayList<Asignatura> copiaProfundaAsignaturas() {
        ArrayList<Asignatura> copia = new ArrayList<>(this.coleccionAsignaturas.size());
        for (Asignatura coleccionAsignatura : this.coleccionAsignaturas) {
            copia.add(new Asignatura(coleccionAsignatura));
        }
        return copia;
    }

    public int getTamano() {
        return this.coleccionAsignaturas.size();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        this.coleccionAsignaturas.add(new Asignatura(asignatura));
    }

    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: La asignatura no puede ser nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            return null;
        }
        return new Asignatura(this.coleccionAsignaturas.get(indice));
    }


    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        this.coleccionAsignaturas.remove(indice);
    }


}