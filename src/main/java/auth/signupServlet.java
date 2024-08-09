package auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class signup
 */
@WebServlet("/signup")
public class signupServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  response.getWriter().append("Served at: ").append(request.getContextPath());
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String code = request.getParameter("code");
        
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String signUpQuery = "INSERT INTO Users (name, email, password, code, role) VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mergui", "root", "root");
            if(role == "user") {
             // Add user data to DB
                PreparedStatement pstmt = conn.prepareStatement(signUpQuery);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.setString(4, code);
                pstmt.setString(5, role);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    session.setAttribute("name", name);
                    session.setAttribute("email", email);
                    session.setAttribute("role", role);
                    out.println("Sign up successful!");
                } else {
                    out.println("Failed to sign up.");
                }
                pstmt.close();
            }else if(role=="coadmin") {
             // check code if code was right Add coadmin data to DB
            }else if(role == "admin") {
             //Add admin data to DB
            }
            else {
                out.println("<li>Error </li>");
            }

        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
        
        out.println("<li>Name: " + name + "</li>");

 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  doGet(request, response);
 }

}