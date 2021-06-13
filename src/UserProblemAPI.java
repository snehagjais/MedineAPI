import java.io.BufferedReader;
import java.io.IOException;
import java.sql.DriverManager;

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

public class UserProblemAPI extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
                    "Pitney@1a");
            
            String name=request.getParameter("name");
            String doctorId=request.getParameter("id");
			String query = "select contact, age, symptom, description, sufferingDays, doctorId, name, active, problemId, userId "
					+ "from userproblem up inner join users us on up.userId = us.id ";
			
			if(name.equalsIgnoreCase("doctor")) {
				query = query + "where doctorId = ? ";
			} else {
				query = query + "where userId = ? ";
			}
			
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			ps.setString(1, doctorId);
			
			ResultSet rs = (ResultSet) ps.executeQuery();
			
            JSONArray ja = new JSONArray();
            while (rs.next()) {
                UserProblemDTO userProblem = new UserProblemDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), 
                		rs.getInt(6), rs.getString(7), rs.getBoolean(8), rs.getInt(9), rs.getInt(10));
                JSONObject js = userProblem.getProblemJson();
                ja.add(js);
            }

            System.out.println(ja);
            con.close();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(ja);
			response.flushBuffer();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = getJson(req);
        JSONParser par = new JSONParser();
        JSONObject obj = null;
        try {
            obj= (JSONObject) par.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
                    "Pitney@1a");

            PreparedStatement ps=(PreparedStatement) con.prepareStatement("insert into userproblem (contact, age, symptom, description, sufferingDays, doctorId, userId, active) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(obj.get("contact").toString()));
            ps.setInt(2, Integer.parseInt(obj.get("age").toString()));
            ps.setString(3, obj.get("symptom").toString());
            ps.setString(4, obj.get("desc").toString());
            ps.setInt(5, Integer.parseInt(obj.get("sufferingDays").toString()));
            ps.setInt(6, Integer.parseInt(obj.get("doctorId").toString()));
            ps.setInt(7, Integer.parseInt(obj.get("userId").toString()));
            ps.setBoolean(8, Boolean.getBoolean(obj.get("active").toString()));
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
