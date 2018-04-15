package com.lhc.bookstore.book.web.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.book.service.BookService;
import com.lhc.bookstore.category.domain.Category;
import com.lhc.bookstore.category.service.CategoryService;

import cn.itcast.commons.CommonUtils;

/**
 * 添加图书因为要上传图片，故不能继承BaseServlet
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();
	private CategoryService cs = new CategoryService();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//创建工厂对象，设置缓存大小和临时目录
		//DiskFileItemFactory factory = new DiskFileItemFactory(15 * 1024, new File("F:/temp"));
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//创建文件上传解析器
		ServletFileUpload sfu = new ServletFileUpload(factory);
		//设置单个文件上传大小
		sfu.setFileSizeMax(20 * 1024);
		try {
			//解析request，得到表单项列表
			List<FileItem> itemList = sfu.parseRequest(request);
			
			/**
			 * 把itemList封装到Book对象中
			 */
			Map<String, Object> map = new HashMap<String, Object>();
			for(FileItem item : itemList) {
				if(item.isFormField()) {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			Book book = CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			
			//保存目录
			String saveFile = this.getServletContext().getRealPath("/book_img");
			//保存文件名，解决文件名重复问题
			String fileName = CommonUtils.uuid() + "_" + itemList.get(1).getName();
			
			//校验上传文件扩展名
			if(!fileName.toLowerCase().endsWith(".jpg")) {
				request.setAttribute("msg", "图片只支持jpg格式！");
				request.setAttribute("categoryList", cs.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
			
			//创建上传目标位置
			File destFile = new File(saveFile, fileName);
			//保存上传文件到目标位置
			itemList.get(1).write(destFile);
			
			//校验上传文件尺寸
			Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
			if(image.getWidth(null) > 200 || image.getHeight(null) > 200) {
				//尺寸不合适，删除以保存的图片
				destFile.delete();
				request.setAttribute("msg", "图片尺寸不能大于200 x 200 ！");
				request.setAttribute("categoryList", cs.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
			
			//将上传文件的相对路径(项目目录+文件名)设置为Book对象的image属性
			book.setImage("book_img/" + fileName);
			//将Book对象保存到数据库
			bs.add(book);
			
			//返回图书列表
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").forward(request, response);
		
		} catch (Exception e) {
			//超出上传文件大小限制，抛出 异常
			if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "您上传的文件超出了20KB");
				request.setAttribute("categoryList", cs.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
			throw new RuntimeException(e);
		}
	}

}
