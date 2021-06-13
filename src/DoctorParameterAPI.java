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

public class DoctorParameterAPI extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		try {
			String name=request.getParameter("name");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
					"Pitney@1a");
			
			name = "%" + name +"%";
			String query = "select * from  doctors where degree like ? ";
			
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = (ResultSet) ps.executeQuery();
			
			JSONArray ja = new JSONArray();
			while (rs.next()) {
				Doctor doc = new Doctor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6));
				JSONObject js = doc.getDoctor();
				ja.add(js);
			}

			System.out.println(ja);
			con.close();
            response.setContentType("application/text");
            response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().print(ja.toJSONString());
			}catch (Exception e) {
			System.out.println(e);
		}
	}

}
