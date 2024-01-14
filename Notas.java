//ENTITY - Entity.java
____________________________________________________________________

package com.servlet.modelo.entity;

import java.time.LocalDate;

public class Manual {
	private int id;
    private String titulo;
    private String categoria;
    private LocalDate fecha;
    private String ubicacion;
    private String imagen;
    private String resumen;
    private String articulo;

    // Constructor
    public Manual(int id, String titulo, String categoria, LocalDate fecha, String ubicacion,
                  String imagen, String resumen, String articulo) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.resumen = resumen;
        this.articulo = articulo;
    }

    // Getters y Setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}



































//DAO - ManualInterface.java
____________________________________________________________________

package com.servlet.modelo;

import java.util.List;
import com.servlet.modelo.entity.Manual;

public interface ManualInterface {

	List<Manual> obtenerTodosLosManuales();
    Manual obtenerManualPorId(int id);
    boolean insertarManual(Manual manual);
    boolean actualizarManual(Manual manual);
    boolean eliminarManual(int id);
    
}






























//DAO - ManualDAO.java
____________________________________________________________________

package com.servlet.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.servlet.modelo.entity.Manual;

public class ManualDAO implements ManualInterface{
	
	private final String SELECT_ALL_MANUALES = "SELECT * FROM Manuales";
	private final String SELECT_MANUAL_BY_ID = "SELECT * FROM Manuales WHERE id=?";
	private final String INSERT_MANUAL = "INSERT INTO Manuales (titulo, categoria, fecha, ubicacion, imagen, resumen, articulo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_MANUAL = "UPDATE Manuales SET titulo=?, categoria=?, fecha=?, ubicacion=?, imagen=?, resumen=?, articulo=? WHERE id=?";
    private final String DELETE_MANUAL = "DELETE FROM Manuales WHERE id=?";
    
	
	private DataSource dataSource;

	public ManualDAO() throws Exception {
        Context initialContext = new InitialContext();
        dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/PostgresDS");
    }
	
	
    // LISTAR
	public List<Manual> obtenerTodosLosManuales() {
	    List<Manual> manuales = new ArrayList<>();
	    try (Connection conn = dataSource.getConnection(); // Usar dataSource para obtener la conexión
	         PreparedStatement statement = conn.prepareStatement(SELECT_ALL_MANUALES);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String titulo = resultSet.getString("titulo");
	            String categoria = resultSet.getString("categoria");
	            LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
	            String ubicacion = resultSet.getString("ubicacion");
	            String imagen = resultSet.getString("imagen");
	            String resumen = resultSet.getString("resumen");
	            String articulo = resultSet.getString("articulo");

	            Manual manual = new Manual(id, titulo, categoria, fecha, ubicacion, imagen, resumen, articulo);
	            manuales.add(manual);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return manuales;
	}
    
    
	
    // BUSCAR
	public Manual obtenerManualPorId(int id) {
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement statement = conn.prepareStatement(SELECT_MANUAL_BY_ID)) {

	        statement.setInt(1, id);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                String titulo = resultSet.getString("titulo");
	                String categoria = resultSet.getString("categoria");
	                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
	                String ubicacion = resultSet.getString("ubicacion");
	                String imagen = resultSet.getString("imagen");
	                String resumen = resultSet.getString("resumen");
	                String articulo = resultSet.getString("articulo");

	                return new Manual(id, titulo, categoria, fecha, ubicacion, imagen, resumen, articulo);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
    
	
    
	// INSERTAR
	public boolean insertarManual(Manual manual) {
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement statement = conn.prepareStatement(INSERT_MANUAL)) {

	        statement.setString(1, manual.getTitulo());
	        statement.setString(2, manual.getCategoria());
	        statement.setDate(3, java.sql.Date.valueOf(manual.getFecha()));
	        statement.setString(4, manual.getUbicacion());
	        statement.setString(5, manual.getImagen());
	        statement.setString(6, manual.getResumen());
	        statement.setString(7, manual.getArticulo());

	        int filasInsertadas = statement.executeUpdate();
	        return filasInsertadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	
	 //ACTUALIZAR
	public boolean actualizarManual(Manual manual) {
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement statement = conn.prepareStatement(UPDATE_MANUAL)) {
	    	statement.setInt(8, manual.getId());
	        statement.setString(1, manual.getTitulo());
	        statement.setString(2, manual.getCategoria());
	        statement.setDate(3, java.sql.Date.valueOf(manual.getFecha()));
	        statement.setString(4, manual.getUbicacion());
	        statement.setString(5, manual.getImagen());
	        statement.setString(6, manual.getResumen());
	        statement.setString(7, manual.getArticulo());
	        

	        int filasActualizadas = statement.executeUpdate();
	        return filasActualizadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	
	// ELIMINAR
		public boolean eliminarManual(int id) {
		    try (Connection conn = dataSource.getConnection();
		         PreparedStatement statement = conn.prepareStatement(DELETE_MANUAL)) {

		        statement.setInt(1, id);

		        int filasEliminadas = statement.executeUpdate();
		        return filasEliminadas > 0;
		    } catch (SQLException e) {
		    	System.err.println("Error al eliminar manual con ID: " + id);
		        e.printStackTrace();
		        return false;
		    }
		}
    
	
	
    //Metodo close
    public void close(Connection conn, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}




























//Controller - ManualControlador.java
____________________________________________________________________

package com.servlet.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.servlet.modelo.ManualDAO;
import com.servlet.modelo.entity.Manual;




@WebServlet("/ManualControlador")
public class ManualControlador extends HttpServlet {
	    private static final long serialVersionUID = 1L;

	    //LISTAR Y BUSCAR
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String idParameter = request.getParameter("id");
	        String action = request.getParameter("action");      
	        ManualDAO manualDAO = null;
	        
	           
	        
	        //LISTAR 
	        if (idParameter == null && "listar".equals(action)) {//Si no se pone un parametro, es decir si solo se oprime el boton se cumple la condicion
	        	
	            try {
	                manualDAO = new ManualDAO();
	                List<Manual> manuales = manualDAO.obtenerTodosLosManuales();
	                request.setAttribute("manuales", manuales);
	                request.getRequestDispatcher("mostrar_manuales.jsp").forward(request, response);
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.sendRedirect("error_listado.jsp");
	            }
	        } 

	        
	        
        	// BUSCAR 
	        else if(idParameter != null && "buscar".equals(action)) { //Si se pone un valor entra aqui en el else
	        	
	        	 if (idParameter.isEmpty()) { //Si esta vacio aqui se detiene
	                    response.sendRedirect("error_no_proporcionado_id.jsp"); 
	                    return;
	             }
	        	 
	            try {
	                int id = Integer.parseInt(idParameter);
	                manualDAO = null;
	                try {
	                	
	                    manualDAO = new ManualDAO();
	                    Manual manual = manualDAO.obtenerManualPorId(id);
	                    //------
	                    if (manual != null) {
	                    	
	                        request.setAttribute("manual", manual);
	                        request.getRequestDispatcher("mostrar_manual.jsp").forward(request, response);
	                        
	                    }else {
	                        response.sendRedirect("error_manual_no_encontrado.jsp");
	                        
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    response.sendRedirect("error_obtener_manual.jsp");
	                }
	            } catch (NumberFormatException ex) {
	                ex.printStackTrace();
	                response.sendRedirect("error_conversion_parametro.jsp");
	            }	            
	        }

	        
	        
	        //BUSCAR PARA ACTUALIZAR
	        else if(idParameter != null && "b_para_actualizar".equals(action)) { //Si se pone un valor entra aqui en el else
	        	
	        	 if (idParameter.isEmpty()) { //Si esta vacio aqui se detiene
	                    response.sendRedirect("error_no_proporcionado_id.jsp"); 
	                    return;
	             }
	        	 
	            try {
	                int id = Integer.parseInt(idParameter);
	                manualDAO = null;
	                try {
	                	
	                    manualDAO = new ManualDAO();
	                    Manual manual = manualDAO.obtenerManualPorId(id);
	                    //------
	                    if (manual != null) {
	                    	
	                        request.setAttribute("manual", manual);
	                        request.getRequestDispatcher("mostrar_manual_actualizar.jsp").forward(request, response);
	                        
	                    }else {
	                        response.sendRedirect("error_manual_no_encontrado.jsp");
	                        
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    response.sendRedirect("error_obtener_manual.jsp");
	                }
	            } catch (NumberFormatException ex) {
	                ex.printStackTrace();
	                response.sendRedirect("error_conversion_parametro.jsp");
	            }	            
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    //INSERTAR ACTUALIZAR Y ELIMINAR
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	
	        String action = request.getParameter("action");

	        
	        //INSERTAR
	        if ("insertar".equals(action)) {

	            String titulo = request.getParameter("titulo");
	            String categoria = request.getParameter("categoria");
	            String ubicacion = request.getParameter("ubicacion");
	            String imagen = request.getParameter("imagen");
	            String resumen = request.getParameter("resumen");
	            String articulo = request.getParameter("articulo");

	            Manual nuevoManual = new Manual(0, titulo, categoria, LocalDate.now(), ubicacion, imagen, resumen, articulo);

	            ManualDAO manualDAO = null;
		        try {
		            manualDAO = new ManualDAO();
		            boolean exito = manualDAO.insertarManual(nuevoManual);
		            if (exito) {
		                response.sendRedirect("exito.jsp");
		            } else {
		            	response.sendRedirect("error_insertar.jsp");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            response.sendRedirect("error_general.jsp");
		        }		        
	        } 
	        
	        
	        
	        //ACTUALIZAR
	        else if ("actualizar".equals(action)) {
	            
	        	String idParameter = request.getParameter("id");
		    	int id = 0;
		    	
		    	if(idParameter != null && !idParameter.isEmpty()) {
		            try {
		                id = Integer.parseInt(idParameter);
		            } catch (NumberFormatException ex) {
		                ex.printStackTrace();
		                response.sendRedirect("error_id_no_valido.jsp");
		                return;
		            }
		        } else {
		            response.sendRedirect("error_no_proporcionado_id.jsp"); 
		            return;
		        }
		    	
		        String titulo = request.getParameter("titulo");
		        String categoria = request.getParameter("categoria");
		        String ubicacion = request.getParameter("ubicacion");
		        String imagen = request.getParameter("imagen");
		        String resumen = request.getParameter("resumen");
		        String articulo = request.getParameter("articulo");

		        Manual manualActualizado = new Manual(id, titulo, categoria, LocalDate.now(), ubicacion, imagen, resumen, articulo); // Crear el manual actualizado

		        ManualDAO manualDAO = null;
		        try {
		            manualDAO = new ManualDAO();
		            boolean exito = manualDAO.actualizarManual(manualActualizado);
		            if (exito) {
		                response.sendRedirect("exito.jsp");
		            } else {
		            	response.sendRedirect("error_actualizar.jsp");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            response.sendRedirect("error_general.jsp");
		        }        	
	        }
	        
	        
	        
	        //ELIMINAR
	        else if ("eliminar".equals(action)) {
	            
	        	 String idParameter = request.getParameter("id");
	 	        int id = 0;

	 	        if (idParameter != null && !idParameter.isEmpty()) {
	 	            try {
	 	                id = Integer.parseInt(idParameter);
	 	            } catch (NumberFormatException e) {
	 	            	response.sendRedirect("error_id_no_valido.jsp");
	 	                return;
	 	            }
	 	        } else {
	 	        	response.sendRedirect("error_no_proporcionado_id.jsp"); 
	 	            return;
	 	        }

	 	        ManualDAO manualDAO = null;
	 	        try {
	 	            manualDAO = new ManualDAO();
	 	            boolean exito = manualDAO.eliminarManual(id);
	 	            if (exito) {
	 	                response.sendRedirect("exito.jsp"); 
	 	            } else {
	 	                response.sendRedirect("error_eliminar.jsp"); 
	 	            }
	 	        } catch (Exception e) {
	 	            e.printStackTrace(); 
	 	            response.sendRedirect("error_general.jsp"); 
	 	        }	        	
	        }
	        // ... Otros casos
	         
	    }
	        
}
