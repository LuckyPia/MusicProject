package com.musicproject.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.musicproject.bean.Contact;
import com.musicproject.dao.MusicDAO;
import org.json.JSONObject;


/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<Contact> list=MusicDAO.queryall(" select * from music,purchaseinfo where music.MusicID=purchaseinfo.MusicID ");

        JSONObject jsonObject=new JSONObject();

        List<Map<String,Object>> roomList=new ArrayList<Map<String,Object>>();


        if(list.isEmpty()){
            //	System.out.println("查询为空");
        }

        // System.out.println("查询为满");

        try( PrintWriter out = response.getWriter()) {

            for(Contact r:list){
                //	System.out.println("id:"+r.getRoomid());
                Map<String, Object> params = new HashMap<>();
                params.put("UserID", r.getUserid());
                params.put("MusicTitle", r.getMusictitle());
                params.put("MusicArtist", r.getMusicartist());
                params.put("MusicPrice", r.getMusicprice());
                params.put("MusicPath", r.getMusicpath());
                params.put("PurchaseTime", r.getPurchasetime());
                roomList.add(params);
                //   System .out.println(roomList);



            }
            jsonObject.put("data", roomList);
            //System .out.println(roomList);

            // System .out.println(jsonObject);
            out.write(jsonObject.toString());
        }catch (Exception e) {
            // TODO: handle exception
        }

    }
}


