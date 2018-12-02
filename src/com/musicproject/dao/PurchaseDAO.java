package com.musicproject.dao;

import com.musicproject.bean.PurchaseInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PurchaseDAO {
	PurchaseInfo user = null;
	PurchaseInfo user1 = null;
	static Connection con=null;
	private String SQL ="";
	public static Connection getCon(){
		try{
			String url="jdbc:mysql://localhost:3306/music";
			String user="root";
			String password=null;
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver ok");
			con=DriverManager.getConnection(url, user, password);
			if(con!=null)System.out.println("con ok");
		}catch(Exception e){}
		return con;
	}

	//关闭连接
	public  static void closeConnection(Connection connection){

		if(connection != null){
			try {
				connection.close(); // 关闭数据库连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	//添加
	public int insert(PurchaseInfo b){
		int d=0;
		try{
			con=getCon();
			String sql="insert into PurchaseInfo values (?,?,?)";
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setString(1, b.getUserid());
			pst.setString(2, b.getMusicid());
			pst.setString(3, b.getPurchasetime());

			d=pst.executeUpdate();
			pst.close();
			con.close();

		}catch(Exception e){}
		return d;
	}

	//删除音乐
	public int delete(String musictitle){
		int d=0;
		try{
			con=getCon();
			String sql="delete from PurchaseInfo where UserID=?";
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setString(1, musictitle);
			d=pst.executeUpdate();
			pst.close();
			con.close();
		}catch(Exception e){}
		return d;

	}

	//查询音乐
	public List<PurchaseInfo> query2(String userid){
		List<PurchaseInfo> list=new ArrayList<PurchaseInfo>();

		try{
			con=getCon();
			//3.创建声明对象或者预声明对象
			String sql="select * from PurchaseInfo where UserID=?";
			PreparedStatement pst=con.prepareStatement(sql);
			//传参
			pst.setString(1, userid);
			//4.执行
			ResultSet rs=pst.executeQuery();
			//5.处理结果
			while(rs.next()){
				//包裝成Book對象
				PurchaseInfo b=new PurchaseInfo();
				String a=rs.getString("UserID");
				String f=rs.getString("MusicID");
				String d=rs.getString("PurchaseTime");
				System.out.println(""+a+f+d);

				list.add(b);

			}


			//6.关闭
			rs.close();
			pst.close();
			con.close();

		}catch(Exception e){}
		return list;
	}

	//添加判断
	public PurchaseInfo regist(String userid,String musicid){
		SQL = "select * from purchaseinfo where UserID = ? and MusicID = ?";
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = UserDAO.getCon();
			pstmt = (PreparedStatement) ((java.sql.Connection) connection).prepareStatement(SQL);
			//这里的意思将用户名和密码填到SQL语句的问号处
			pstmt.setString(1, userid);
			pstmt.setString(2, musicid);
			ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
			//判断结果集是否有效
			if(rSet.next()){
				user1 = new PurchaseInfo();
				user1.setUserid(rSet.getString("userid"));
				user1.setUserid(rSet.getString("musicid"));
			}
			connection.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			UserDAO.closeConnection(connection);
		}
		return user1;
	}

}
