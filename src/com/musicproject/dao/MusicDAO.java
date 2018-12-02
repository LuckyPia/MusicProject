package com.musicproject.dao;

import com.musicproject.bean.Contact;
import com.musicproject.bean.Music;
import com.musicproject.bean.PurchaseInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MusicDAO {
	Contact contact = null;
	Contact contact1 = null;
	static Connection con=null;
	private String SQL ="";
	public static Connection getCon(){
		try{
			String url="jdbc:mysql://localhost:3306/music?useUnicode=true&characterEncoding=gbk";
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
	//添加音乐
	public int insert(Music b){
		int d=0;
		try{
			con=getCon();
			String sql="insert into music (MusicTitle,MusicArtist,MusicPrice,MusicPath) values (?,?,?,?)";
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setString(1, b.getMusictitle());
			pst.setString(2, b.getMusicartist());
			pst.setString(3, b.getMusicprice());
			pst.setString(4, b.getMusicpath());


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
			String sql="delete from music where MusicTitle=?";
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setString(1, musictitle);
			d=pst.executeUpdate();
			pst.close();
			con.close();
		}catch(Exception e){}
		return d;

	}

	//查询音乐
	public static List<Music> query(String sql){
		List<Music> list=new ArrayList<Music>();
		System.out.println("query");

		try{
			con=getCon();
			//3.创建声明对象或者预声明对象
			PreparedStatement pst=con.prepareStatement(sql);
			//传参

			//4.执行
			ResultSet rs=pst.executeQuery();
			//5.处理结果
			while(rs.next()){
				//包裝成Book對象
				Music b=new Music();
				PurchaseInfo p=new PurchaseInfo();
				b.setMusicid(rs.getInt("MusicID"));
				b.setMusictitle(rs.getString("MusicTitle"));
				b.setMusicartist(rs.getString("MusicArtist"));
				b.setMusicprice(rs.getString("MusicPrice"));
				b.setMusicpath(rs.getString("MusicPath"));
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

	public static List<Contact> queryall(String sql){
		List<Contact> list=new ArrayList<Contact>();
		System.out.println("query");

		try{
			con=getCon();
			//3.创建声明对象或者预声明对象
			PreparedStatement pst=con.prepareStatement(sql);
			//传参

			//4.执行
			ResultSet rs=pst.executeQuery();
			//5.处理结果
			while(rs.next()){
				//包裝成Book對象
				Contact c=new Contact();
				c.setMusictitle(rs.getString("MusicTitle"));
				c.setMusicartist(rs.getString("MusicArtist"));
				c.setMusicprice(rs.getString("MusicPrice"));
				c.setMusicpath(rs.getString("MusicPath"));
				c.setUserid(rs.getString("UserID"));
				c.setPurchasetime(rs.getString("PurchaseTime"));
				//加在列表中
				list.add(c);
				System.out.println(list);

			}


			//6.关闭
			rs.close();
			pst.close();
			con.close();

		}catch(Exception e){}
		return list;
	}

	//用户购买判断
	public  List<Contact> regist(String userid){
		SQL = " select * from music,purchaseinfo where music.MusicID=purchaseinfo.MusicID and purchaseinfo.userid= ?";
		List<Contact> list=new ArrayList<Contact>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = MusicDAO.getCon();
			pstmt = (PreparedStatement) connection.prepareStatement(SQL);
			//这里的意思将用户名和密码填到SQL语句的问号处
			pstmt.setString(1, userid);
			ResultSet rs = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
			//判断结果集是否有效
			while(rs.next()){
				contact1 = new Contact();
				contact1.setMusictitle(rs.getString("MusicTitle"));
				contact1.setMusicartist(rs.getString("MusicArtist"));
				contact1.setMusicprice(rs.getString("MusicPrice"));
				contact1.setMusicpath(rs.getString("MusicPath"));
				contact1.setMusicid(rs.getInt("MusicID"));
				contact1.setUserid(rs.getString("UserID"));
				contact1.setPurchasetime(rs.getString("PurchaseTime"));
				list.add(contact1);
				System.out.println(list);
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