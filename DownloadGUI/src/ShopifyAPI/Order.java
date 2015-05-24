package ShopifyAPI;

public class Order {	
	//should have a list of transactions?
	private int id;
	
	private String info;
	
	public Order(int id, String info)
	{
		this.id = id;
		this.info = info;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setInfo(String info)
	{
		this.info = info;
	}
	
	public String getInfo()
	{
		return this.info;
	}
}
