

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.*;
import java.nio.charset.Charset;
import java.io.*;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
public static	double ValueMon;
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		input(request.getParameter("Country"));
		PrintWriter p= response.getWriter();
		p.println("Value: "+ValueMon);
		doGet(request, response);
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
				//System.out.println(sb.toString());

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
					 		ValueMon=Value;
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
