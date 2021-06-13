import java.io.BufferedReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class MedicineAPIServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
					"Pitney@1a");
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery("select * from  medicine");
			JSONArray ja = new JSONArray();
			while (rs.next()) {
				Medicine mn = new Medicine(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
				JSONObject js = mn.med();
				ja.add(js);
			}

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print(ja);
			response.flushBuffer();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body=getJson(req);
		JSONParser par=new JSONParser();
		JSONObject obj=null;
		try {
			obj= (JSONObject) par.parse(body);
			System.out.println(obj.get("quantity"));
			System.out.println(obj.get("company_name"));
			System.out.println(obj.get("medicine_name"));
			System.out.println(obj.get("unit_price"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
					"Pitney@1a");

			PreparedStatement ps=(PreparedStatement) con.prepareStatement("insert into medicine values (?,?,?,?,?)");
			ps.setString(1, "7");
			ps.setString(2, obj.get("medicine_name").toString());
			ps.setString(3, obj.get("company_name").toString());
			ps.setString(4, obj.get("quantity").toString());
			ps.setString(5, obj.get("unit_price").toString());
			ps.executeUpdate();

			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getOutputStream().print("Done");
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}
	
	public String getJson(HttpServletRequest req) {
		StringBuffer jb = new StringBuffer(); 
		String line = null; 
		try { 
			BufferedReader reader = req.getReader(); 
			while ((line = reader.readLine()) != null) 
				jb.append(line);
		} catch (Exception e) { 
			System.out.println("Error"); 
		}
		return jb.toString();
	}

}
