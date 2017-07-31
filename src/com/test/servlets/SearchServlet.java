package com.test.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.test.beans.Product;
import com.test.dao.SearchDao;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in search servlet");
		SearchDao searchDao = new SearchDao();
		String searchString = request.getParameter("search");
		List<Product> products = new ArrayList<>();
		try {
			products = searchDao.getSearchResults(searchString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.getClass().getResourceAsStream("/html/searchResults.html").to;

		System.out.println(request.getServletContext().getRealPath("/html/searchResults.html"));
		String page = getHTMLString(request.getServletContext().getRealPath("/html/searchResults.html"), products);

		response.getWriter().write(page);

	}

	private String getHTMLString(String filePath, List<Product> products) throws IOException {
		BufferedReader template = new BufferedReader(new FileReader(filePath));
		String line="";
		StringBuilder builder = new StringBuilder();
		while((line=template.readLine())!=null){
			builder.append(line);
		}
		String page = builder.toString();
		page = MessageFormat.format(page, 
				products.get(0).getProductImgPath(),
				products.get(1).getProductImgPath(),
				products.get(2).getProductImgPath(),
				products.get(0).getProductName(),
				products.get(1).getProductName(),
				products.get(2).getProductName());
		return page;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
