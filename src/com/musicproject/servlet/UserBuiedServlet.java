package com.musicproject.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.musicproject.bean.Contact;
import com.musicproject.dao.MusicDAO;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class UserBuied
 */
@WebServlet("/UserBuied")
public class UserBuiedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBuiedServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userid=request.getParameter("userid");
		MusicDAO musicDAO=new MusicDAO();
		List<Contact> list=musicDAO.regist(userid);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject=new JSONObject();

		List<Map<String,Object>> roomList=new ArrayList<Map<String,Object>>();
		if(list.isEmpty()) {

			System.out.println("empty");

			try {
				jsonObject.put("data", (Collection) null);
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg", "尚未购买音乐");
				out.write(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println(list);

			// System.out.println("查询为满");

			try {

				for(Contact r:list){
					//	System.out.println("id:"+r.getRoomid());
					Map<String, Object> params = new HashMap<>();
					//params.put("UserID", r.getUserid());
					params.put("MusicTitle", r.getMusictitle());
					params.put("MusicArtist", r.getMusicartist());
					params.put("MusicPrice", r.getMusicprice());
					params.put("MusicPath", r.getMusicpath());
					params.put("PurchaseTime", r.getPurchasetime());
					params.put("MusicId", r.getMusicid());
					roomList.add(params);
					//   System .out.println(roomList);



				}
				jsonObject.put("data", roomList);
				jsonObject.put("errorCode", 0);
				jsonObject.put("errorMsg", "");
				System .out.println(roomList);

				// System .out.println(jsonObject);
				out.write(jsonObject.toString());
			}catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
