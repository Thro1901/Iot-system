package com.zetcode;

import models.Sensor;
import repository.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = {"/sensor"})
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database db = new Database();
        response.setContentType("text/html;charset=UTF-8");

        var out = response.getOutputStream();

        out.println("<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" );
        out.println("<form action=\"http://localhost:8080/valuenow\">\n" +

                "<input type=\"submit\" value=\"Go to last value\" />\n" +
                "</form>");


        out.println("<h4>  Data from SQL  </h4> ");
        out.println("<table style=\"width:30%\">\n" +
                "<tr>\n" +
                //"<th>Id</th>\n" +
                "<th>Temperature</th>\n" +
                "<th>Humidity</th>\n" +
                "<th>Date</th>\n" +
                "<th colspan=\"4\">Time</th>\n" +
                "</tr>\n");

        for (Sensor s: db.getLatest(50)){

            out.println("<tr>" +
            //"<td>" +  s.getId() +" </td>\n" +
            "<td>" +  s.getTemperature() + " °C</td>\n" +
            "<td>" +  s.getHumidity() + " %</td>\n" +
            "<td>" +  s.getDate() + " </td>\n" +
            "<td>" + s.getTime() + " </td>\n" );

            out.println("</tr>\n");

        }

        out.println("</table>\n" +
                "</body>\n" +
                "</html>");
    }
}

