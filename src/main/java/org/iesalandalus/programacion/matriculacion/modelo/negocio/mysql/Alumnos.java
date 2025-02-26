package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;

public class Alumnos implements IAlumnos {

    private Connection conexion;
    private static Alumnos instancia;


    public Alumnos() {
        comenzar();
    }

    public static Alumnos getInstancia() {
        if (instancia == null) {
            instancia = new Alumnos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();
    }

    public ArrayList<Alumno> get() throws SQLException {
        ArrayList<Alumno> copiaProfunda = new ArrayList<>();
        String query = """ 
                SELECT nombre
                      , dni
                      , telefono 
                      , email
                      , fechaNacimiento 
                      FROM alumnos""";
        Statement sentencia = conexion.createStatement();
        ResultSet resultado = sentencia.executeQuery(query);
        while (resultado.next()) {
            Alumno alumno = new Alumno(
                    resultado.getString("nombre")
                    , resultado.getString("dni")
                    , resultado.getString("telefono")
                    , resultado.getString("email")
                    , resultado.getDate("fechaNacimiento").toLocalDate());
            copiaProfunda.add(alumno);
        }
        return copiaProfunda;
    }


    public int getTamano() throws SQLException {
        String query = """
                SELECT COUNT(*)
                FROM alumnos
                """;
        Statement sentencia = conexion.createStatement();
        ResultSet rs = sentencia.executeQuery(query);
        return rs.getInt("cont");
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException {

        if (buscar(alumno) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

        String query = """
                INSERT INTO alumnos (nombre, dni, telefono, email, fechaNacimiento)
                VALUES (?, ?, ?, ?, ?)""";
        PreparedStatement preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, alumno.getNombre());
        preparedStatement.setString(2, alumno.getDni());
        preparedStatement.setString(3, alumno.getTelefono());
        preparedStatement.setString(4, alumno.getCorreo());
        preparedStatement.setDate(5, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
        preparedStatement.executeUpdate();
    }

    public Alumno buscar(Alumno alumno) throws SQLException {
        String query = """
                SELECT nombre
                    , telefono
                    , correo
                    , dni
                    , fechaNacimiento
                FROM alumno
                Where dni = ?
                """;
        PreparedStatement preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, alumno.getDni());
        ResultSet rs = preparedStatement.executeQuery();
        if (!rs.next())
            return null;
        return new Alumno(rs.getString("nombre"),
                rs.getString("dni"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getDate("fechaNacimiento").toLocalDate()
        );
    }


    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException {

        if (buscar(alumno) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ese alumno con ese DNI");
        }
        String query = """
                DELETE FROM alumnos
                WHERE dni = ?
                """;
        PreparedStatement preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, alumno.getDni());
        preparedStatement.executeUpdate();
    }


}

