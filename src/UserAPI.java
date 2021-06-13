import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.DriverManager;

public class UserAPI extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
                    "Pitney@1a");
            
            String name=request.getParameter("email");
			String pass=request.getParameter("password");
			String query = "select * from  users where email = ? and password = ? ";
			
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, pass);
			ResultSet rs = (ResultSet) ps.executeQuery();
			
            JSONArray ja = new JSONArray();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                JSONObject js = user.getJson();
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

        String body=getJson(req);
        JSONParser par=new JSONParser();
        JSONObject obj=null;
        try {
            obj= (JSONObject) par.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        String email = obj.get("email").toString();
        String password = obj.get("password").toString();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
                    "Pitney@1a");

            PreparedStatement ps=(PreparedStatement) con.prepareStatement("insert into users (name, email, password, type) values (?,?,?,?)");
            ps.setString(1, obj.get("name").toString());
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, obj.get("type").toString());
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
