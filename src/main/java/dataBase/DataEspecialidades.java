package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import dataBase.DbConnector;
import entities.Administradores;
import entities.Especialidad;
import entities.Paciente;


public class DataEspecialidades {

		public LinkedList<Especialidad> getAll() throws SQLException {
			
			Statement stmt=null;
			ResultSet rs=null;
			LinkedList<Especialidad> especialidades = new LinkedList<>();
			try {
						
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from especialidades where estado = 1");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Especialidad e = new Especialidad();
					e.setCodigo_esp(rs.getInt("codigo_esp"));
					e.setNombre(rs.getString("nombre"));
					especialidades.add(e);
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
			
			return especialidades;
		} // fin getAll

		public Especialidad getByNombre(Especialidad e)throws SQLException {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Especialidad esp = null;
			String consulta = "select * from especialidades where nombre=?";
			try{
				// Crear la conexi�n
				stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
				
				// Ejecutar la query
				stmt.setString(1, e.getNombre());
				rs = stmt.executeQuery();
				
				// Mapeo de ResultSet a objeto
				if(rs!= null && rs.next()) {
					esp = new Especialidad(); 
					esp.setCodigo_esp(rs.getInt("codigo_esp"));
					esp.setNombre(rs.getString("nombre"));
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
			return esp;
		}

		public Especialidad add(Especialidad e2) {
			PreparedStatement stmt= null;
			ResultSet keyResultSet=null;
			try {
				stmt=DbConnector.getInstancia().getConn().
						prepareStatement(
								"insert into especialidades(nombre) values(?)",
								PreparedStatement.RETURN_GENERATED_KEYS
								);
				stmt.setString(1, e2.getNombre());
				stmt.executeUpdate();
				
				keyResultSet=stmt.getGeneratedKeys();
	            if(keyResultSet!=null && keyResultSet.next()){
	                e2.setCodigo_esp(keyResultSet.getInt(1));
	            }

				
			}  catch (SQLException e) {
	            e.printStackTrace();
			} finally {
	            try {
	                if(keyResultSet!=null)keyResultSet.close();
	                if(stmt!=null)stmt.close();
	                DbConnector.getInstancia().releaseConn();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	            }
			}
			return e2;
		}

		public Especialidad getByCodigo(Especialidad e) throws SQLException {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Especialidad esp = null;
			String consulta = "select * from especialidades where codigo_esp=?";
			try{
				// Crear la conexi�n
				stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
				
				// Ejecutar la query
				stmt.setInt(1, e.getCodigo_esp());
				rs = stmt.executeQuery();
				
				// Mapeo de ResultSet a objeto
				if(rs!= null && rs.next()) {
					esp = new Especialidad(); 
					esp.setCodigo_esp(rs.getInt("codigo_esp"));
					esp.setNombre(rs.getString("nombre"));
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
			return esp;
		}

		public void update(Especialidad esp) {
			PreparedStatement stmt= null;
			ResultSet rs=null;
			try {
				stmt=DbConnector.getInstancia().getConn().
						prepareStatement(
								"update especialidades set nombre=? where codigo_esp=? "
								);
				stmt.setString(1, esp.getNombre());
				stmt.setInt(2, esp.getCodigo_esp());
			
				stmt.executeUpdate();
				
			}  catch (SQLException e) {
	            e.printStackTrace();
			} finally {
	            try {
	                if(rs!=null)rs.close();
	                if(stmt!=null)stmt.close();
	                DbConnector.getInstancia().releaseConn();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	            }
			}
			
		}

		public void delete(Especialidad esp) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from especialidades where codigo_esp=? ");
				stmt.setInt(1, esp.getCodigo_esp());
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
		
		public LinkedList<Especialidad> getNombres(Paciente p) throws SQLException{
			LinkedList<Especialidad> especialidades = new LinkedList<>();
			PreparedStatement stmt=null;
			ResultSet rs=null;
			String consulta = "select e.nombre\r\n"
					+ "from profesionales p \r\n"
					+ "inner join especialidades e \r\n"
					+ "	on p.cod_especialidad = e.codigo_esp \r\n"
					+ "inner join turnos t \r\n"
					+ "	on t.matricula_prof = p.matricula \r\n"
					+ "where t.id_paciente = ?";
			
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setInt(1, p.getId());
			rs = stmt.executeQuery();
			
			if (rs!=null) {
				while(rs.next()) {
					Especialidad e = new Especialidad();
					e.setNombre(rs.getString("nombre"));
					especialidades.add(e);
						} // Fin del while
			} // Fin del if
			
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			DbConnector.getInstancia().releaseConn();
			
			return especialidades;
		}

}
