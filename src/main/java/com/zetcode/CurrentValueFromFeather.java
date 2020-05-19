package com.zetcode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "currentvalue", urlPatterns = {"/Currentvalue"})
public class CurrentValueFromFeather extends HttpServlet {

    String timeNow;
    String temp;
    String hum;
    String url = "http://5.150.211.190/getValues";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html;charset=UTF-8");
    var out = response.getOutputStream();

        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" );
        out.println("<form action=\"http://localhost:8080/sensor\">\n" +
                "<input type=\"submit\" value=\"Go back\" />\n" +
                "</form>");

    URL oracle = new URL(url);

    BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        out.println("<h4>  Current value from Feather  </h4> ");

        out.println("<table border=\"1\" style=\"width:30%\">\n" +
                "<tr>\n" +
                "<th>Temperature</th>\n" +
                "<th>Humidity</th>\n" +
                "<th>Date</th>\n" +
                "</tr>\n");

        temp = in.readLine();
        hum = in.readLine();
        timeNow = in.readLine();

        long unix_seconds = Long.parseLong(timeNow);
        Date date = new Date(unix_seconds * 1000);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeNow = jdf.format(date);

            out.println("<tr>" +
                    "<td>" + temp + " °C</td>\n" +
                    "<td>" + hum + " %</td>\n" +
                    "<td>" + timeNow + " </td>\n");

        out.println("</tr>\n");
        out.println("</table>");
        out.println("</body>\n" +
                "</html>");

        in.close();

    }
}
