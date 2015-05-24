package Logger;

import java.util.HashMap;
import java.util.List;

import javax.swing.JTextArea;

import ShopifyAPI.Order;

public class Logger {

	private JTextArea textArea;
	
	public Logger(JTextArea textArea)
	{
		this.textArea = textArea;		
		this.textArea.append("--- Welcome ---");
	}
	
	public void Log(String text)
	{
		this.textArea.append("\n" + text);
	}
	
	public void Log(HashMap<Integer, Order> orders)
	{
		for (Order order: orders.values())
		{
			this.textArea.append("\n" + "Order with id: " + order.getId() + " json: " + order.getInfo());
		}		
	}
	
	public void Log(List<String> texts)
	{
		for(String text : texts)
		{
			this.textArea.append("\n" + text);
		} 		
	}
}
