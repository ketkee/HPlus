package com.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test.beans.Product;

public class SearchDao {

	
	public List<Product> getSearchResults(String searchString) throws SQLException{
		List<Product> products = new ArrayList<Product>();
		Connection connection = DBConnection.getConnectionToDatabase();
		System.out.println("search string in DAO: "+searchString );
		
		PreparedStatement statement = connection.prepareStatement("select * from products where product_name like '%"+searchString+"%'");
		ResultSet set = statement.executeQuery();
		Product product=null;
		while(set.next()){
			product= new Product();
			product.setProductId(set.getInt("product_id"));
			product.setProductName(set.getString("product_name"));
			product.setProductImgPath(set.getString("image_path"));
			products.add(product);
		}
		return products;
	}
}
