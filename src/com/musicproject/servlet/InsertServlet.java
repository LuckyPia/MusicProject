package com.musicproject.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.musicproject.bean.User;
import com.musicproject.dao.UserDAO;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		//获得请求
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String repassword=request.getParameter("repassword");
		System.out.println(username);
		PrintWriter out = response.getWriter();
		UserDAO bd=new UserDAO();
		User user2 =bd.regist(username);

		//封装
		User b=new User();
		b.setUsername(username);
		b.setPassword(password);
		JSONObject jsonObject=new JSONObject();
		if(username.isEmpty()||password.isEmpty()||repassword.isEmpty()){
			try {
				jsonObject.put("data", "");
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg", "账号|密码|确认密码不能为空！");
				out.write(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(!repassword.equals(password)) {
			try {
				jsonObject.put("data", "");
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg", "两次密码不一致！");
				out.write(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(user2!=null){
			try {
				jsonObject.put("data","");
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg", "账号重复");
				out.write(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{
			int d=bd.insert(b);
			if(d>0){
				try {
					jsonObject.put("data", username);
					jsonObject.put("errorCode", 0);
					jsonObject.put("errorMsg", "success");
					out.write(jsonObject.toString());
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

	}
}
