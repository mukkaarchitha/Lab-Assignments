

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

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
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

/**
 * Servlet implementation class UDoperations
 */
@WebServlet("/UDoperations")
public class update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public update() {
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
		String countryname=request.getParameter("countryname3");
		
		MongoClientURI uri = new MongoClientURI("mongodb://architha:architha@ds033297.mlab.com:33297/assignlab7");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection country = db.getCollection("lab7");

		
		BasicDBObject query = new BasicDBObject();
		query.append("Country Name",countryname);
		
		countryname.remove(query);
		PrintWriter write=response.getWriter();
		write.println("<p> Deleted the Details of "+countryname+"</p>");
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
		doGet(request, response);
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
	
public Double foreign(Double b) {
		
		double x = ((b-273.0) * 9.0/5.0) + 32.0;
		Double fahrenhiet=(double) Math.round(x);
		return fahrenhiet;
	}


public static String callURL(String myURL) {
	//System.out.println("Requested URL:" + myURL);
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
			BufferedReader bufferedReader = new BufferedReader(in);
			if(bufferedReader!=null)
			{
				int cp;
				while ((cp = bufferedReader.read()) != -1) {
					sb.append((char) cp);
				}
				bufferedReader.close();
			}
		}
	in.close();
	} catch (Exception e) {
		throw new RuntimeException("Exception while calling URL:"+ myURL, e);
	} 

	return sb.toString();
			}

}
