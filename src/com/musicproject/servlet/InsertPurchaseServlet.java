package com.musicproject.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.musicproject.bean.PurchaseInfo;
import com.musicproject.dao.PurchaseDAO;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class InsertPurchaseServlet
 */
@WebServlet("/InsertPurchaseServlet")
public class InsertPurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertPurchaseServlet() {
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
		String musicid=request.getParameter("musicid");
		Date dNow = new Date( );
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String redata=df.format(dNow);
		JSONObject jsonObject=new JSONObject();
		PrintWriter out = response.getWriter();
		System.out.println(redata);

		PurchaseInfo pInfo=new PurchaseInfo();
		pInfo.setMusicid(musicid);
		pInfo.setUserid(userid);
		pInfo.setPurchasetime(redata);

		PurchaseDAO pDao=new PurchaseDAO();
		PurchaseInfo ph=pDao.regist(userid, musicid);
		if(ph!=null){
			try {
				jsonObject.put("successMsg","");
				jsonObject.put("errorCode", -1);
				jsonObject.put("errorMsg", "已经购买，请勿重复购买！");
				out.write(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//
		}
		else {
			int b=pDao.insert(pInfo);
			if(b>0) {
				try {
					jsonObject.put("successMsg", "购买成功");
					jsonObject.put("errorCode", 0);
					jsonObject.put("errorMsg", "");
					out.write(jsonObject.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					jsonObject.put("successMsg", "");
					jsonObject.put("errorCode", -1);
					jsonObject.put("errorMsg","未知错误" );
					out.write(jsonObject.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
