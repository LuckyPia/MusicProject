package com.musicproject.dao;

import com.musicproject.bean.User;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
	static User user = null;
	User user1 = null;
	static Connection con=null;
	private static String SQL ="";
	public static Connection getCon(){
		try{
			String url="jdbc:mysql://localhost:3306/music?useUnicode=true&characterEncoding=gbk";
			String user="root";
			String password=null;
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver ok");
			con= DriverManager.getConnection(url, user, password);
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

	//查询

	public List<User> query(String sql){
		List<User> list=new ArrayList<User>();

		try{
			con=getCon();
			//3.创建声明对象或者预声明对象
			PreparedStatement pst=((java.sql.Connection) con).prepareStatement(sql);
			//传参

			//4.执行
			ResultSet rs=pst.executeQuery();
			//5.处理结果
			while(rs.next()){
				//包裝成Book對象
				User b=new User();
				b.setUsername(rs.getString("Username"));
				b.setPassword(rs.getString("Password"));
				b.setUserid(rs.getInt("UserID"));

				//加在列表中
				list.add(b);

			}


			//6.关闭
			rs.close();
			pst.close();
			con.close();

		}catch(Exception e){}
		return list;
	}

	//注册

	public int insert(User b){
		int d=0;
		try{
			con=getCon();
			String sql="insert into user (UserName,UserPassword) values (?,?)";
			PreparedStatement pst=((java.sql.Connection) con).prepareStatement(sql);
			pst.setString(1, b.getUsername());
			pst.setString(2, b.getPassword());

			d=pst.executeUpdate();
			System.out.println(d);
			pst.close();
			con.close();

		}catch(Exception e){}
		return d;
	}

	//注册判断

	//注册判断
	public User regist(String username){
		SQL = "select * from user where username = ?";
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = UserDAO.getCon();
			pstmt = (PreparedStatement) ((java.sql.Connection) connection).prepareStatement(SQL);
			//这里的意思将用户名和密码填到SQL语句的问号处
			pstmt.setString(1, username);
			ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
			//判断结果集是否有效
			if(rSet.next()){
				user1 = new User();
				user1.setUsername(rSet.getString("username"));
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

	//登陆判断
	public static List<User> login(String username, String password){
		List<User> list=new ArrayList<User>();
		SQL = "select * from user where UserName = ? and UserPassword = ? ";
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = UserDAO.getCon();
			pstmt=con.prepareStatement(SQL);
			System.out.println(password);
			//这里的意思将用户名和密码填到SQL语句的问号处
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
			//判断结果集是否有效
			while(rSet.next()){
				user = new User();

				user.setUsername(rSet.getString("username"));
				user.setPassword(rSet.getString("userpassword"));
				user.setUserid(rSet.getInt("userid"));
				user.setPower(rSet.getInt("power"));

				list.add(user);
				System.out.println("username:"+user.getUsername());
			}
			connection.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;


	}

}