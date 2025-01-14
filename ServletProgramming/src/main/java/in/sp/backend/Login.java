package in.sp.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Serve the login page
        request.getRequestDispatcher("/login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user inputs
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Debugging output to check values
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Handle potential null or empty input
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.getWriter().println("Username and password are required.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                response.getWriter().println("Failed to establish a database connection.");
                return;
            }
            
            String query = "SELECT * FROM usersmain WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                response.sendRedirect("Dashboard");
            } else {
                response.getWriter().println("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace to help debug the issue
            response.getWriter().println("An error occurred while processing your login: " + e.getMessage());
        }
    }
}
