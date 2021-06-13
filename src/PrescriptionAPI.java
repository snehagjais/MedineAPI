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

public class PrescriptionAPI extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/manisha", "root",
                    "Pitney@1a");
            
            String userId=request.getParameter("userId");
            String problemId=request.getParameter("problemId");
			String query = "select * from prescription where userId = ? and problemId = ? ";
			
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			ps.setString(1, userId);
			ps.setString(2, problemId);
			
			ResultSet rs = (ResultSet) ps.executeQuery();
			
            JSONArray ja = new JSONArray();
            while (rs.next()) {
                Prescription prescription = new Prescription(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
                JSONObject js = prescription.getPrescriptionJson();
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

            PreparedStatement ps=(PreparedStatement) con.prepareStatement("insert into prescription (doctorName, medicines, description, userName, userId, problemId) values (?,?,?,?,?,?)");
            ps.setString(1, obj.get("doctorName").toString());
            ps.setString(2, obj.get("medicines").toString());
            ps.setString(3, obj.get("description").toString());
            ps.setString(4, obj.get("userName").toString());
            ps.setInt(5, Integer.parseInt(obj.get("userId").toString()));
            ps.setInt(6, Integer.parseInt(obj.get("problemId").toString()));
            ps.executeUpdate();
            
            ps=(PreparedStatement) con.prepareStatement("Update userproblem set active = true where problemId = ? ");
            ps.setInt(1, Integer.parseInt(obj.get("problemId").toString()));
            ps.executeUpdate();

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
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
