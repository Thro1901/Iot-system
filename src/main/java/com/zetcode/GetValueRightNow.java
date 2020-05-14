package com.zetcode;

import models.Sensor;
import repository.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetValueRightNow", urlPatterns = {"/valuenow"})
public class GetValueRightNow extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database db = new Database();
        response.setContentType("text/plain;charset=UTF-8");

        var out = response.getOutputStream();

        for (Sensor s: db.getLatest(1)){
            out.println(s.getId()+" "+s.getTemperature()+" "+s.getHumidity()+" "+s.getDate()+" "+s.getTime());
        }
    }
}