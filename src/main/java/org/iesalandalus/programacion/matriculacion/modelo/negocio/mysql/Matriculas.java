package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Matriculas implements IMatriculas {
    private final ArrayList<Matricula> coleccionMatriculas;

    public Matriculas() {
        this.coleccionMatriculas = new ArrayList<>();
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    public ArrayList<Matricula> get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }

    private ArrayList<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
            ArrayList<Matricula> copia = new ArrayList<>(this.coleccionMatriculas.size());
        for (Matricula matricula:this.coleccionMatriculas) {
            copia.add(new Matricula(matricula));
        }
        return copia;
    }


    public int getTamano() {
        return this.coleccionMatriculas.size();
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
        this.coleccionMatriculas.add(new Matricula(matricula));
    }


    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: La matricula no puede ser nulo.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            return null;
        }
        return this.coleccionMatriculas.get(indice);

    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        this.coleccionMatriculas.remove(indice);
    }


    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException {
        ArrayList<Matricula> tempAlumno = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null && matricula.getAlumno().equals(alumno)) {
                tempAlumno.add(new Matricula(matricula));
            }
        }
        return tempAlumno;
    }

    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException {
        ArrayList<Matricula> tempCurso = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null && matricula.getCursoAcademico().equals(cursoAcademico)) {
                tempCurso.add(new Matricula(matricula));
            }
        }

        return tempCurso;
    }


    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        ArrayList<Matricula> tempCiclo = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null) {
                for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                    if (asignatura != null && asignatura.getCicloFormativo().equals(cicloFormativo)) {
                        tempCiclo.add(matricula);
                        break;
                    }
                }
            }
        }

        return tempCiclo;
    }


}
