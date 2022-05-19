package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Especialidad_ObralSocial;
import entities.ObraSocial;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;
import entities.Valor_especialidad;
import logic.ComunicacionDb;
import logic.ProfesionalController;

/**
 * Servlet implementation class AsignarTurno
 */
@WebServlet("/AsignarTurno")
public class AsignarTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AsignarTurno() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComunicacionDb ctrl = new ComunicacionDb();
		Paciente paciente = new Paciente();
		Turnos turno = new Turnos();
		Profesional prof = new Profesional();
		ObraSocial os = new ObraSocial();
		Valor_especialidad valor_esp = new Valor_especialidad();
		Especialidad_ObralSocial esp_os = new Especialidad_ObralSocial();
		Double precio_final;
		HttpSession session = request.getSession();
		LinkedList<Profesional> profesionales = new LinkedList<>();
		ProfesionalController profCtrl = new ProfesionalController();
		//DateTimeFormatter formatter = new DateTimeFormatter("dd/MM/yyyy");
		String fecha_string;
		String hora_string;
		String matricula_profesional;
		LocalDate fecha_turno;
		LocalTime hora_turno;

		int num_turno = Integer.parseInt(request.getParameter("nro_turno"));
		//turno.setNumero(num_turno);
		
		if (num_turno == 1) {
			fecha_string = request.getParameter("date");
			hora_string = request.getParameter("time");
			matricula_profesional = request.getParameter("matricula_profesional");
			
			fecha_turno = LocalDate.parse(fecha_string);
			hora_turno = LocalTime.parse(hora_string);
			
			turno.setFecha_turno(fecha_turno);
			turno.setHora_turno(hora_turno);
			prof.setMatricula(matricula_profesional);

			try {
				prof = ctrl.getProfesional(prof);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
			try {
				valor_esp = ctrl.getValorEspecialidad(prof);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			paciente = (Paciente) session.getAttribute("usuario");
			try {
				os = ctrl.getObraSocial(paciente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			try {
				esp_os = ctrl.getPorcentajeCobertura(prof.getCod_especialidad(), os.getId_obra_social());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			precio_final = (double) (valor_esp.getValor() - (valor_esp.getValor() * esp_os.getProcentaje_cobertura())); 
		
			request.setAttribute("usuario", paciente);
			request.setAttribute("turno", turno);
			request.setAttribute("profesional", prof);
			request.setAttribute("obra_social", os);
			request.setAttribute("valor_especialidad", valor_esp);
			request.setAttribute("precio_final", precio_final);
			request.setAttribute("date", fecha_string);
			request.setAttribute("time", hora_string);
			request.getRequestDispatcher("WEB-INF/pruebas.jsp").forward(request, response); 
		
			}
		
			else {
			//Necesito el código de especialidad para la lista de profesionales
				request.setAttribute("profesionales", profesionales);
				request.getRequestDispatcher("WEB-INF/elegirProfesional.jsp").forward(request, response); 
			}
		}

}
