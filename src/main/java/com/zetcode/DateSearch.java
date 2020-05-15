package com.zetcode;

import models.Sensor;
import repository.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DateSearch", urlPatterns = {"/SearchDates"})
public class DateSearch extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database db = new Database();

        String d1 = request.getParameter("firstDate");
        String d2 = request.getParameter("secondDate");



        response.setContentType("text/html;charset=UTF-8");

        var out = response.getOutputStream();
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "border-collapse: collapse;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" );
        out.println("<form action=\"http://localhost:8080/sensor\">\n" +

                "<input type=\"submit\" value=\"Go back\" />\n" +
                "</form>");



        out.println("<h4>  Last Data From SQL  </h4> ");
        out.println("<table style=\"width:30%\">\n" +
                "<tr>\n" +
                //"<th>Id</th>\n" +
                "<th>Temperature</th>\n" +
                "<th>Humidity</th>\n" +
                "<th>Date</th>\n" +
                "<th colspan=\"4\">Time</th>\n" +
                "</tr>\n");

        for (Sensor s: db.getBetweenDates(d1,d2)){

            out.println("<tr>" +
                    //"<td>" + s.getId() + "</td>\n" +
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

