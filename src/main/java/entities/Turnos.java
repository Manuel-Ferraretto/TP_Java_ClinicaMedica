package entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Turnos {
	private Integer numero;
	private LocalDate fecha_turno;
	private LocalTime hora_turno;
	private String matricula_prof;
	private Integer id_paciente;
	private Integer estado;
	
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public LocalDate getFecha_turno() {
		return fecha_turno;
	}
	public void setFecha_turno(LocalDate fecha_turno) {
		this.fecha_turno = fecha_turno;
	}
	public LocalTime getHora_turno() {
		return hora_turno;
	}
	public void setHora_turno(LocalTime hora_turno) {
		this.hora_turno = hora_turno;
	}
	public String getMatricula_prof() {
		return matricula_prof;
	}
	public void setMatricula_prof(String matricula_prof) {
		this.matricula_prof = matricula_prof;
	}
	public Integer getId_paciente() {
		return id_paciente;
	}
	public void setId_paciente(Integer id_paciente) {
		this.id_paciente = id_paciente;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
