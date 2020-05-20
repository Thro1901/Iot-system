package com.zetcode;

import models.Sensor;
import repository.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "DateSearch", urlPatterns = {"/SearchDates"})
public class DateSearch extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database db = new Database();

        response.setContentType("text/html;charset=UTF-8");

        var out = response.getOutputStream();

        String d1 = request.getParameter("firstDate");
        String d2 = request.getParameter("secondDate");

        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" );

        out.println("<form action=\"http://localhost:8080/sensor\">\n" +

                "<input type=\"submit\" value=\"Go back\" />\n" +
                "</form>");

        out.println("<h4>  Search between dates  </h4> ");

        if (d1 == null || d2== null){
            out.println("<form>\n" +
                    "<label for=\"firstDate\">First date:</label><br>\n" +
                    "<input type=\"text\" id=\"firstDate\" name=\"firstDate\" value="+ LocalDate.now().minusDays(7) +"><br>\n" +
                    "<label for=\"secondDate\">Second Date:</label><br>\n" +
                    "<input type=\"text\" id=\"secondDate\" name=\"secondDate\" value="+ LocalDate.now() + ">\n"+
                    "<input type=\"submit\" value=\"Submit\"\n>" +
                    "</form>");
        }
        else{
            out.println("<form>\n" +
                    "<label for=\"firstDate\">First date:</label><br>\n" +
                    "<input type=\"text\" id=\"firstDate\" name=\"firstDate\" value="+ LocalDate.now().minusDays(7) +"><br>\n" +
                    "<label for=\"secondDate\">Second Date:</label><br>\n" +
                    "<input type=\"text\" id=\"secondDate\" name=\"secondDate\" value="+ LocalDate.now() + ">\n"+
                    "<input type=\"submit\" value=\"Submit\"\n>" +
                    "</form>");
            out.println("<h4>  Last Data From SQL  </h4> ");
            out.println("<table border=\"1\" style=\"width:30%\">\n" +
                    "<tr>\n" +
                    "<th>Temperature</th>\n" +
                    "<th>Humidity</th>\n" +
                    "<th>Date</th>\n" +
                    "<th colspan=\"4\">Time</th>\n" +
                    "</tr>\n");

            for (Sensor s: db.getBetweenDates(d1,d2)){
                    out.println("<tr>" +
                            "<td>" + s.getTemperature() + " °C</td>\n" +
                            "<td>" + s.getHumidity() + " %</td>\n" +
                            "<td>" + s.getDate() + " </td>\n" +
                            "<td>" + s.getTime() + " </td>\n");
                    out.println("</tr>\n");
            }

            out.println("</table>");
            out.println("</body>\n" +
                    "</html>");

        }
    }
}

