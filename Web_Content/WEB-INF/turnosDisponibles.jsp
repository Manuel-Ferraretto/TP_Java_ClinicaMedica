<%@page import="java.util.LinkedList"%>
<%@page import="entities.Turnos"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Paciente"%>
<%@page import="java.sql.Date" %>
<%@page import="java.sql.Time" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado profesionales</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/estilos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>

<body>
	<%
		LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("turnosDisponibles");
		Profesional prof = (Profesional)request.getAttribute("profesional");
		Paciente pac = (Paciente)session.getAttribute("usuario");
		
		Integer id_pac = (Integer)session.getAttribute("id_paciente");
	%>
<form action="AsignarTurno" method="post">

		<h2>Profesional: <%=prof.getNombre() %> <%=prof.getApellido() %></h2>
		<h2>Usuario: <%=pac.getNombre() %> <%=pac.getApellido() %></h2>
		<h2>Id_paciente: <%= id_pac %></h2>
		<input type="hidden" name="matricula_profesional" value="<%=prof.getMatricula() %>">
		<div class="container">
 			<table class="table table-striped" id="myTable">
    			<thead>
      				<tr>
        				<th>Fecha turno</th>
        				<th>Hora turno</th>
        				<th>Seleccione su turno</th>
     				 </tr>
    			</thead>
    
    			<tbody>
    
   					 <% for (Turnos t:turnos) { %> 
   					 
   					 <% 
						Date sqlDate = null; 
						Time sqlTime = null;
						sqlDate = Date.valueOf(t.getFecha_turno());
						sqlTime = Time.valueOf(t.getHora_turno());
					%>

      				<tr>
        				<td><input type="hidden" name="date" id="date"><%=sqlDate %></td>
        				<td><input type="hidden" name="time" id="time"><%=sqlTime %></td>
        				<td><button class="btn btn-success" name="nro_turno" type="submit" value="1">Reservar</button></td> 
      				</tr>
    				 <% } %>
   				</tbody> 
  			</table>
  			<button class="btn btn-success" name="nro_turno" type="submit" value="0">Volver atras</button>
		</div>
</form>

	  <script>
                $(document).ready(function(){

                // code to read selected table row cell data (values).
                $("#myTable").on('click','.btn btn-success',function(){
                // get the current row
                var currentRow=$(this).closest("tr"); 
     
                var fecha_turno=currentRow.find("td:eq(0)").text(); // get current row 1st TD value
                var hora_turno=currentRow.find("td:eq(1)").text(); // get current row 2nd TD
                
                $("date").val(fecha_turno);
                $("time").val(hora_turno);
                    });
                });

            </script>
	
</body>

</html>