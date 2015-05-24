package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Test;

import JsonParser.JsonParser;

public class JsonParserTest {

	@Test
	public void test() {
		JsonParser myJsonParser = new JsonParser();
		
		String myTestString = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
		List<String> json = new ArrayList();
		try {
			json = myJsonParser.getJson(myTestString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(myTestString, json.get(0));
		
		
	}

}
