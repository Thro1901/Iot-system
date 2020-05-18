package com.zetcode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(name = "currentvalue", urlPatterns = {"/Currentvalue"})
public class CurrentValueFromFeather extends HttpServlet {
    String inputLine;
    String url = "http://5.150.211.190";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html;charset=UTF-8");
    var out = response.getOutputStream();

        out.println("<form action=\"http://localhost:8080/sensor\">\n" +

                "<input type=\"submit\" value=\"Go back\" />\n" +
                "</form>");

        URL url = new URL("http://5.150.211.190/");

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        while ((line = br.readLine()) != null) {
            out.println(line);
        }

    }
}
