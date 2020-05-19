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
import java.util.Calendar;

@WebServlet(name = "currentvalue", urlPatterns = {"/Currentvalue"})
public class CurrentValueFromFeather extends HttpServlet {

    String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    String temp;
    String hum;
    String url = "http://5.150.211.190/readTemperature";

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
        while ((temp = in.readLine()) != null){

            out.println("<tr>" +
                    "<td>" + temp + " °C</td>\n");

        }

        String url1 = "http://5.150.211.190/readHumidity";
        URL oracle1 = new URL(url1);

        in = new BufferedReader(new InputStreamReader(oracle1.openStream()));

        while ((hum = in.readLine()) != null){

            out.println( "<td>" + hum + " %</td>\n" +
                    "<td>" + timeNow + " </td>\n");

        }
        out.println("</tr>\n");
        out.println("</table>");
        out.println("</body>\n" +
                "</html>");



        in.close();

    }
}
