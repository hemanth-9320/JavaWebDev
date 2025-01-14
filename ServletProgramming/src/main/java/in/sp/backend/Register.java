package in.sp.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Serve the registration page
        request.getRequestDispatcher("/register.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user inputs
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            response.getWriter().println("All fields are required.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO usersmain (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                // Registration successful
                response.getWriter().println("Registration Successful! Welcome, " + username);
            } else {
                // Registration failed
                response.getWriter().println("Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // Duplicate username or email error code
                response.getWriter().println("Username or email already exists.");
            } else {
                e.printStackTrace();
                response.getWriter().println("An error occurred while processing your registration: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An unexpected error occurred.");
        }
    }
}
