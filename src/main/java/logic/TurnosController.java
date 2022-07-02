package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataTurnos;
import entities.Paciente;
import entities.Turnos;

public class TurnosController {
	private DataTurnos dt;
	
	public TurnosController() {
		dt= new DataTurnos();
	}

	public void deletePorMatricula(Turnos t) {
		// TODO Auto-generated method stub
		dt.deletePorMatricula(t);
	}

	public LinkedList<Turnos> getAll() {
		// TODO Auto-generated method stub
		return dt.getAll();
	}
	
	public LinkedList<Turnos> getAllTurnosPacienteActual(Paciente p){
		return  dt.getAllTurnosPacienteActual(p);
	}
	
	public Boolean validateAvailability(Turnos t) throws SQLException {
		return dt.validateAvailability(t);
	}

}
