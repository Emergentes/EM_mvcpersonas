package com.emergentes.controlador;

import com.emergentes.modelo.Persona;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaest");

        Persona obj1 = new Persona();
        int id, pos;
        int opcion = Integer.parseInt(request.getParameter("op"));

        switch (opcion) {
            case 1: // Insertar nuevo registro
                obj1.setId(ultimoId(request));
                request.setAttribute("tipo", "nuevo");
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case 2: // Modificar registro
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarIndice(request, id);
                obj1 = lista.get(pos);
                request.setAttribute("tipo", "modificar");
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case 3: //Eliminar registro
                pos = buscarIndice(request, Integer.parseInt(request.getParameter("id")));
                lista.remove(pos);
                ses.setAttribute("listaest", lista);
                response.sendRedirect("index.jsp");
                break;
            case 4:
                // Grabar
                String nuevo = request.getParameter("nuevo");

                id = Integer.parseInt(request.getParameter("id"));
                int edad = Integer.parseInt(request.getParameter("edad"));

                obj1.setId(id);
                obj1.setNombres(request.getParameter("nombres"));
                obj1.setApellidos(request.getParameter("apellidos"));
                obj1.setEdad(edad);

                if (nuevo.equals("true")) {
                    lista.add(obj1);
                } else {
                    pos = buscarIndice(request, id);
                    lista.set(pos, obj1);
                }
                ses.setAttribute("listaest", lista);
                response.sendRedirect("index.jsp");
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }

    private int buscarIndice(HttpServletRequest request, int id) {
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaest");
        
        int i = 0;
        if (lista.size() > 0) {

            while (i < lista.size()) {
                if (lista.get(i).getId() == id) {
                    break;
                }
                else i++;
            }
        }
        return i;
    }

    private int ultimoId(HttpServletRequest request) {
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaest");

        int idaux = 0;

        for (Persona item : lista) {
            idaux = item.getId();
        }
        return idaux + 1;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
