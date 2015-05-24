package ShipwireAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;

import JsonParser.JsonParser;
import Logger.Logger;

public class ShipwireAPIClient {
	
	private Logger logger;
	
	//private String urlBaseSandbox = "https://api.beta.shipwire.com";
	
	private String urlBaseProduction = "https://api.shipwire.com";
	
	private String userName;
	
	private String password;
	
	public ShipwireAPIClient(Logger myLogger, String userName, String password){
		this.logger = myLogger;
		this.userName = userName;
		this.password = password;
	}
	
	public List<String> GetInfo() throws ClientProtocolException, IOException
	{
		String url = GetURL();
		String authString = GetAuthString();
		
		// Time out set to 30 seconds
		int timeoutInSeconds = 30;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeoutInSeconds * 1000).setConnectionRequestTimeout(timeoutInSeconds * 1000).build();
				
		HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		HttpGet request = new HttpGet(url);
		
		request.addHeader("Authorization", "Basic " + authString);
		HttpResponse response = client.execute(request);
			
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200)
		{
			this.logger.Log("Response Code : " 
		               + response.getStatusLine().getStatusCode() + " (Good)");
		}
		else
		{
			this.logger.Log("Response Code : " 
		               + response.getStatusLine().getStatusCode() + " (Not good)");
		}		
				
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));
		 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}				
				
		String resultAsString = result.toString();
		
		this.logger.Log(resultAsString);
				
		List<String> parsedResult = parseResult(resultAsString);
				
		return parsedResult;
	}
	
	private String GetURL()
	{
		return urlBaseProduction + "/api/v3/stock";
	}
	
	private String GetAuthString()
	{
		String authString = this.userName + ":" + this.password;		
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		return authStringEnc;
	}
	
	private List<String> parseResult(String resultAsString) 
	{
		List<String> parsedResult = new ArrayList<String>();
		JsonParser parser = new JsonParser();
		try {
			//parsedResult = parser.getProducts(resultAsString);
			parsedResult = parser.getJson(resultAsString);
		} catch (JSONException e) {
			logger.Log("Error parsing from JSON: " + e.getMessage());			
		}
		return parsedResult;
	}
}