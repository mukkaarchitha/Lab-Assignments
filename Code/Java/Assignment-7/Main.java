

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import java.net.*;
import java.nio.charset.Charset;
import java.io.*;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
public static	double ValueMon;
public static String countryName;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try{
			System.out.println("in get method");
		int responseCode = 0;
		String countryname=request.getParameter("countryname");
		
		MongoClientURI uri = new MongoClientURI("mongodb://architha:architha@ds033297.mlab.com:33297/assignlab7");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection country = db.getCollection("lab7");

		
		BasicDBObject query = new BasicDBObject();
		query.put("name",countryname);
		DBCursor docs = country.find(query);
		//System.out.println(docs);
		System.out.println(docs.toArray().toString());
		
		JSONArray ja=new JSONArray(docs.toArray());
		System.out.println(ja.toString());
		
		String a=(String) ja.toString();
		System.out.println(a);
		PrintWriter write=response.getWriter();
		write.println("<p> Data Returned"+a+"</p>");
		JSONObject jO=new JSONObject(a);
		
		System.out.println("Details"+jO.toString());
	
		
		
		
		
		
		}
		catch(Exception e)
		{
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//input(request.getParameter("Country"));
		PrintWriter p= response.getWriter();
		//convert(request.getParameter("Country"));
		convert("INR");
		p.println("Country Name:"+convert(request.getParameter("Country")));
		p.println("Value: ");
		try
		{
		MongoClientURI uri = new MongoClientURI("mongodb://architha:architha@ds033297.mlab.com:33297/assignlab7");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("lab7");
		System.out.println(users.getName());
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("Country Name","");
		jsonObject.put("Value","");
		DBObject dbObject= (DBObject)JSON.parse(jsonObject.toString());
		//DBObject dbObject1= (DBObject)JSON.parse(jsonObject.toString());
		users.insert(dbObject);
		//users.insert(dbObject1);
		//doGet(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
			public static String callURL(String myURL) {
				System.out.println("Requeted URL:" + myURL);
				StringBuilder sb = new StringBuilder();
				URLConnection urlConn = null;
				InputStreamReader in = null;
				try {
					URL url = new URL(myURL);
					urlConn = url.openConnection();
					if (urlConn != null)
						urlConn.setReadTimeout(60 * 1000);
					if (urlConn != null && urlConn.getInputStream() != null) {
						in = new InputStreamReader(urlConn.getInputStream(),
								Charset.defaultCharset());
						BufferedReader br = new BufferedReader(in);
						if(br!=null)
						{
							int p;
							while ((p = br.read()) != -1) {
								sb.append((char) p);
							}
							br.close();
						}
					}
								
				in.close();
				} catch (Exception e) {
					throw new RuntimeException("Exception while calling URL:"+ myURL, e);
				} 
				System.out.println(sb.toString());
				

		return sb.toString();
			}
			
			public static void convert(String s)
			{
			  String s1="http://api.fixer.io/latest";
				 try{
					 
					 JSONObject jsonObject=new JSONObject(callURL(s1));
					 			JSONObject sample=jsonObject.getJSONObject("rates");
					 			Double Value=sample.getDouble(s);
					 		System.out.println(sample.toString());
					 		System.out.println(Value.toString());
					 		
							 					  }
					  catch (Exception e)
					  {
					 	 System.out.println("Error parsing");
					  }

			}

			public  static void input(String s)
			{
				 try {
					JSONObject jsonObject=new JSONObject(callURL("http://api.stableweb.co.uk/index.php/countrydata/get_data_by_country_name/"+s));
					String value=jsonObject.getString("currency_alphabetic_code");
					input(value);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

}
