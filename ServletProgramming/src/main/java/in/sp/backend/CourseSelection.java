package in.sp.backend;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CourseSelection extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studyYear = request.getParameter("studyYear");
        String[] selectedCourses = request.getParameterValues("courses");

        // Process the selected courses (e.g., save to database, display message, etc.)
        StringBuilder responseMessage = new StringBuilder("You have selected:");
        responseMessage.append("<br>Study Year: ").append(studyYear);
        responseMessage.append("<br>Courses: ");
        if (selectedCourses != null) {
            for (String course : selectedCourses) {
                responseMessage.append(course).append(" ");
            }
        } else {
            responseMessage.append("No courses selected.");
        }

        response.setContentType("text/html");
        response.getWriter().println(responseMessage.toString());
    }
}
