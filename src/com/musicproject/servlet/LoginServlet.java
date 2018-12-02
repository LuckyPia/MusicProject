package com.musicproject.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.musicproject.bean.User;
import com.musicproject.dao.UserDAO;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println(password);
		JSONObject jsonObject=new JSONObject();
		PrintWriter out = response.getWriter();


		List<User> list=new ArrayList<User>();

		list = UserDAO.login(username,password);
		System.out.println(list);
		List<Map<String,Object>> roomList=new ArrayList<Map<String,Object>>();

		if(list.isEmpty()){
			try {
				jsonObject.put("data", (Map) null);
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg","无效的用户名或密码" );
				out.write(jsonObject.toString());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				for(User r:list){
					//	System.out.println("id:"+r.getRoomid());
					Map<String, Object> params = new HashMap<>();

					params.put("userid", r.getUserid());
					params.put("username", r.getUsername());
					params.put("power", r.getPower());
					params.put("password", r.getPassword());
					roomList.add(params);
					//   System .out.println(roomList);

					jsonObject.put("data", params);
					jsonObject.put("errorCode", 0);
					jsonObject.put("errorMsg", "");
					out.write(jsonObject.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Cookie c1=new Cookie("username", username);
			Cookie c2=new Cookie("password", password);
			response.addCookie(c2);
			response.addCookie(c1);


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
