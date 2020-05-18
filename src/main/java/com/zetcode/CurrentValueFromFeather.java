package com.zetcode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

     URL oracle = new URL(url);

    BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        while ((inputLine = in.readLine()) != null){
            out.println(inputLine);

        }

        // Ett annat sätt att göra det på. Samma resultat

       // String url = "http://5.150.211.190";
        //Document document = Jsoup.connect(url).get();
       // out.println(document.text());
       // out.println(inputLine);
        in.close();

    }
}
