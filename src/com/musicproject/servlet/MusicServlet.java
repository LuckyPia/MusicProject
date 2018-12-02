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

import com.musicproject.bean.Music;
import com.musicproject.dao.MusicDAO;
import org.json.JSONObject;


/**
 * Servlet implementation class MusicServlet
 */
@WebServlet("/MusicServlet")
public class MusicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MusicServlet() {
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

		List<Music> list=MusicDAO.query("select * from music");

		JSONObject jsonObject=new JSONObject();

		List<Map<String,Object>> roomList=new ArrayList<Map<String,Object>>();


		if(list.isEmpty()){
			//	System.out.println("查询为空");
		}


		try( PrintWriter out = response.getWriter()) {

			for(Music r:list){
				Map<String, Object> params = new HashMap<>();
				params.put("MusicId", r.getMusicid());
				params.put("MusicTitle", r.getMusictitle());
				params.put("MusicArtist", r.getMusicartist());
				params.put("MusicPrice", r.getMusicprice());
				params.put("MusicPath", r.getMusicpath());
				roomList.add(params);


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



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);


	}
}


