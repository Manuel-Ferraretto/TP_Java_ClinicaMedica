package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Paciente;
import entities.Turnos;
import logic.ComunicacionDb;


@WebServlet("/ConfirmarTurno")
public class ConfirmarTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmarTurno() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ComunicacionDb ctrl = new ComunicacionDb();
		Paciente paciente = new Paciente();
		Turnos turno = new Turnos();
		HttpSession session = request.getSession();

		
		paciente = (Paciente) session.getAttribute("usuario");
		int confirmacion = Integer.parseInt(request.getParameter("confirmar"));
		int id_paciente = Integer.parseInt(request.getParameter("id_paciente"));
		String matricula_profesional = request.getParameter("matricula_profesional");
		String fecha_string = request.getParameter("date");
		String hora_string = request.getParameter("time");
		LocalDate fecha_turno = LocalDate.parse(fecha_string);
		LocalTime hora_turno = LocalTime.parse(hora_string);
		
		if (confirmacion == 1){
			turno.setId_paciente(id_paciente);
			turno.setMatricula_prof(matricula_profesional);
			turno.setFecha_turno(fecha_turno);
			turno.setHora_turno(hora_turno);
			
			try {
				ctrl.asignarTurno(turno);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("confirmacion_turno.html").forward(request, response);
		}
		else request.getRequestDispatcher("menu.html").forward(request, response);

	}

}
