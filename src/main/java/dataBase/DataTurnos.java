package dataBase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import entities.Especialidad;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;

public class DataTurnos {

	public void deletePorMatricula(Turnos t) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from turnos where matricula_prof=? ");
			stmt.setString(1, t.getMatricula_prof());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public LinkedList<Turnos> getAll() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Turnos> turnos = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select * from turnos");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Turnos t = new Turnos();
				t.setNumero(rs.getInt("numero"));
				t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
				t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
				t.setMatricula_prof(rs.getString("matricula_prof"));
				t.setId_paciente(rs.getInt("id_paciente"));
				turnos.add(t);
					} // Fin del while
		} // Fin del if
		
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return turnos;	
	}
	
	public LinkedList<Turnos> getAllTurnosPacienteActual(Paciente p) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Turnos> turnos = new LinkedList<>();
		String consulta = "select t.fecha_turno, t.matricula_prof \r\n"
				+ "from profesionales p \r\n"
				+ "inner join especialidades e\r\n"
				+ "	on p.cod_especialidad = e.codigo_esp\r\n"
				+ "inner join turnos t\r\n"
				+ "	on t.matricula_prof = p.matricula\r\n"
				+ "where t.id_paciente = ?";
		try {
			// Crear la conexión
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setInt(1, p.getId());
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Turnos t = new Turnos();
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setMatricula_prof(rs.getString("matricula_prof"));
					turnos.add(t);
						} // Fin del while
			} // Fin del if

			// Cerrar recursos
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			DbConnector.getInstancia().releaseConn();

		} catch (SQLException ex) {
			// Errores
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return turnos;
	}
	
	
	public Boolean validateAvailability(Turnos t)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Turnos turno = null;
		Boolean availability = true;
		String consulta = "select id_paciente from turnos where matricula_prof=? and fecha_turno=? and hora_turno=?";
		try{
			// Crear la conexión
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, t.getMatricula_prof());
			stmt.setDate(2, Date.valueOf(t.getFecha_turno()));
			stmt.setTime(3, Time.valueOf(t.getHora_turno()));
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				turno = new Turnos(); 
				turno.setId_paciente(rs.getInt("id_paciente"));
						} // Fin del if
			
			// Cerrar recursos
			if(stmt!=null) {stmt.close();}
			if(rs!=null) {rs.close();}
			DbConnector.getInstancia().releaseConn(); 
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
		if (t.getId_paciente() == null) {availability = true;}
		else {availability = false;}
		
		return availability;
	}
}
