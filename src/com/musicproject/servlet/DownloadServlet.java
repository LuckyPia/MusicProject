package com.musicproject.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName=request.getParameter("path");	
        //System.out.println(path);
        ServletContext sctx = getServletContext();
        String path=sctx.getRealPath("/upload");
       
        
        
        path=new String(path.getBytes("iso-8859-1"));
        
        //File file = new File(path);		
        File file=new File(path+"\\"+fileName);
        
        InputStream in = new FileInputStream(file);	
        OutputStream os = response.getOutputStream();	
        
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String(file.getName().getBytes("gbk"),"iso-8859-1"));	
        response.addHeader("Content-Length", file.length() + "");
        response.setCharacterEncoding("gbk");
        response.setContentType("application/octet-stream");
        int data = 0;
        while ((data = in.read()) != -1) {				
            os.write(data);								
        }
        os.close();										
        in.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
}
