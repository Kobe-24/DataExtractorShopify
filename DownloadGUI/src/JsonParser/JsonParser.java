package JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ShopifyAPI.Order;


public class JsonParser {

	public JsonParser()
	{}
	
	public List<String> getProducts(String jsonString) throws JSONException
	{
		List<String> toReturn = new ArrayList<String>();
		final JSONObject obj = new JSONObject(jsonString);
		if (obj.has("errors"))
		{
			throw new JSONException("Error from shopify: " + obj.getString("errors"));
		}
	    final JSONArray products = obj.getJSONArray("products");
	    final int n = products.length();
	    for (int i = 0; i < n; ++i) {
	      final JSONObject info = products.getJSONObject(i);
	      toReturn.add(info.getString("product_type"));
	    }
	    return toReturn;
	}
	
	public HashMap<Integer, Order> getOrders(String jsonString) throws JSONException
	{
		HashMap<Integer, Order> toReturn = new HashMap<Integer, Order>();
		final JSONObject obj = new JSONObject(jsonString);
		if (obj.has("errors"))
		{
			throw new JSONException("Error from shopify: " + obj.getString("errors"));
		}
	    final JSONArray orders = obj.getJSONArray("orders");
	    final int n = orders.length();
	    for (int i = 0; i < n; ++i) {
	      final JSONObject info = orders.getJSONObject(i);
	      Integer id = info.getInt("id");	      
	      Order order = new Order(id, info.toString());	      	      
	      toReturn.put(id, order);
	    }
	    return toReturn;
	}
	
	public List<String> getJson(String jsonString) throws JSONException
	{
		List<String> toReturn = new ArrayList<String>();
		final JSONObject obj = new JSONObject(jsonString);
		if (obj.has("errors"))
		{
			throw new JSONException("Error from shopify: " + obj.getString("errors"));
		}
		
		toReturn.add(jsonString);
		return toReturn;
	    
	}
	
}
