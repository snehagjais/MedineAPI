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

public class CompositionAPI extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		try {
			String name=request.getParameter("name");
			String filter=request.getParameter("filter");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
					"Pitney@1a");
			
			boolean multiple = false;
			if(name.contains(",")) {
				multiple = true;
			} else {
				name = "%" + name +"%";
			}
			
			String query = "";
			if(filter.contains("composition")) {
				query = "select * from  medicine where composition like ? ";
			} else if(filter.contains("type")) {
				query = "select * from  medicine where type like ? ";
			} else if(filter.contains("symptom")) {
				if(multiple) {
					String[] strs = name.split(",");
					query = "select * from  medicine where ";
					for(int i = 0; i<strs.length; i++) {
						String st = strs[i];
						st = "%" + st.trim() + "%";
						if(i<strs.length-1) {
							query += "effective_part like '" + st + "' or ";
						} else {
							query += "effective_part like '" + st + "' ";
						}
					}
				} else {
					query = "select * from  medicine where effective_part like ? ";
				}
			}
			
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			if(!multiple) {
				ps.setString(1, name);
			}
			ResultSet rs = (ResultSet) ps.executeQuery();
			
			JSONArray ja = new JSONArray();
			while (rs.next()) {
				Medicine mn = new Medicine(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
				JSONObject js = mn.med();
				ja.add(js);
			}

			System.out.println(ja);
			con.close();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().print(ja.toJSONString());
			}catch (Exception e) {
			System.out.println(e);
		}
	}
}