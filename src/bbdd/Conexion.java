/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Encriptado;

import modelo.Persona;

public class Conexion {

    public static Connection conn;

    public static Connection conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://145.14.151.1/u812167471_datospersona", "u812167471_datospersona", "Datospersona-2024");
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Connection cerrarconexion() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public static boolean comprobarDni(String campo) {

        try {

            String consulta = "SELECT dni from datos where dni =?";

            PreparedStatement pst = conn.prepareStatement(consulta);
            ResultSet rs;
            pst.setString(1, Encriptado.encriptar(campo));

            rs = pst.executeQuery();

            return rs.next();
        } catch (Exception ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static Persona recuperaDatosPersona(String dni) {

        String consultaRecuperaTipo = "SELECT dni, nombre, apellidos,direccion,ciudad from datos where dni='" + dni + "'";

        Persona p = null;

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaRecuperaTipo);

            if (rs.next()) {
                p = new Persona(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public static boolean registrarpersona(Persona p) {

        try {
            String consulta = "INSERT INTO datos (dni, nombre, apellidos,direccion,ciudad) "
                    + "values (?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(consulta);

            pst.setString(1, p.getDni());
            pst.setString(2, p.getNombre());
            pst.setString(3, p.getApellidos());
            pst.setString(4, p.getDireccion());
            pst.setString(5, p.getCiudad());

            pst.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
