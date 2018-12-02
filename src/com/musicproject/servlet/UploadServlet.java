package com.musicproject.servlet;

import com.musicproject.bean.Music;
import com.musicproject.dao.MusicDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {
    String title;
    String artist;
    String price;
    String contentType;
    FileItem music;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        JSONObject jsonObject =new JSONObject();
        PrintWriter out=response.getWriter();
        if(!isMultipart){
            throw new RuntimeException("错误");
        }
        String tempPath = "d:/UploadMusic/";
        File f1 = new File(tempPath);
        if(!f1.isDirectory()){
            System.out.println("临时文件目录不存在! 创建临时文件目录");
            f1.mkdir();
        }
        DiskFileItemFactory dfif = new DiskFileItemFactory();
        dfif.setRepository(f1);
        ServletFileUpload parser = new ServletFileUpload(dfif);

        parser.setFileSizeMax(15*1024*1024);//设置单个文件上传的大小
        parser.setSizeMax(50*1024*1024);



        Map<String,String> map = new HashMap<String, String>();

        try {
            List<?> items = parser.parseRequest(request);
            Iterator iter = items.iterator();
            if (items != null && items.size() > 0) {

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if(item.isFormField()){
                        String name=item.getFieldName();
                        String value=item.getString("UTF-8");
                        System.out.println("form field:"+name+"--"+value);
                        if(name.equals("title")){
                            title=value;
                        }else if(name.equals("artist")){
                            artist=value;
                        }else if(name.equals("price")){
                            price=value;
                        }else{

                        }

                    }else{
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();
                        System.out.println("file upload"+fieldName+"--"+fileName+"--"+contentType+"--"+isInMemory+"--"+sizeInBytes);
                        if(fileName.endsWith(".mp3")){
                            music=item;
                        }else{
                            //request.setAttribute("message","请上传格式为mp3的音乐");
                            jsonObject.put("data","" );
                            jsonObject.put("errorCode", -1);
                            jsonObject.put("errorMsg", "请上传格式为mp3的音乐");
                            out.write(jsonObject.toString());

                        }

                    }
                }

                if(music!=null&&!title.isEmpty()&&!artist.isEmpty()){
                    String refileName=title+"-"+artist+".mp3";
                    try{

                        //1.获取真实地址,这里保存在Tomcat运行的项目upload目录中,这要在src/main/webapp下新建upload文件夹
                        ServletContext sctx = getServletContext();
                        String path=sctx.getRealPath("/upload");
                        File uploadedPath1=new File(path);
                        if(!uploadedPath1.exists()){
                            uploadedPath1.mkdirs();
                        }
                        File uploadedFile1=new File(path+"\\"+refileName);
                        music.write(uploadedFile1);

                        //2方式
                        /*File uploadedPath=new File(uploadPath);
                        if(!uploadedPath.exists()){
                            uploadedPath.mkdirs();
                        }
                        File uploadedFile=new File(uploadPath+refileName);
                        music.write(uploadedFile);*/

                        music.delete();

                        MusicDAO musicDAO=new MusicDAO();
                        Music music=new Music();
                        music.setMusictitle(title);
                        music.setMusicartist(artist);
                        music.setMusicprice(price);
                        music.setMusicpath(refileName);
                        int resualt=musicDAO.insert(music);
                        log("resualt:"+resualt);

                        jsonObject.put("data", "上传成功");
                        jsonObject.put("errorCode", 0);
                        jsonObject.put("errorMsg", "");
                        out.write(jsonObject.toString());

                        //System.out.println(uploadedFile);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    //返回json
                    jsonObject.put("data", "");
                    jsonObject.put("errorCode", -1);
                    jsonObject.put("errorMsg", "上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器繁忙，文件上传失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
