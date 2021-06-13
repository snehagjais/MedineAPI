import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class CompanyServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		try {
			String name=request.getParameter("name");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
					"Pitney@1a");
			
			name = "%" + name + "%";
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from  medicine where company like ? ");
			ps.setString(1, name);
			ResultSet rs = (ResultSet) ps.executeQuery();
			
			JSONArray ja = new JSONArray();
			while (rs.next()) {
				Medicine mn = new Medicine(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
				JSONObject js = mn.med();
				ja.add(js);
			}

			System.out.println(ja);
			con.close();
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
			response.getOutputStream().print(ja.toJSONString());
			}catch (Exception e) {
			System.out.println(e);
		}
		

	}

}
