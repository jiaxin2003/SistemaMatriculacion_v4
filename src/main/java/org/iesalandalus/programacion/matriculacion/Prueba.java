package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Prueba {
    public static void main(String[] args)  {
        try {
            Connection conexion = MySQL.establecerConexion();
            String dni = "12345678Z";
            String query = """
                    SELECT a.nombre
                    , a.dni
                    , a.telefono
                    , a.coreo
                    , a.fechaNacimiento
                    FROM alumno a
                    WHERE a.dni = ?
                    """;
            PreparedStatement preStatement = conexion.prepareStatement(query);
            preStatement.setString(1, dni);
            ResultSet rs = preStatement.executeQuery();
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString(1));
                System.out.println("DNI: " + rs.getString(2));
                System.out.println("Telefono: " + rs.getString(3));
                System.out.println("Correo: " + rs.getString(4));
                System.out.println("Fecha de nacimiento: " + rs.getString(5));
            }
        }catch (SQLException e) {
            System.out.println("ERROR: ");
        }
        MySQL.cerrarConexion();
    }
}
