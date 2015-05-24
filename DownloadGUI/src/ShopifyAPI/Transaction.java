package ShopifyAPI;

public class Transaction {
	private int id;
	
	private String info;
	
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
