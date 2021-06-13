import org.json.simple.JSONObject;

public class User {
	int id;
    String userName;
    String email;
    String password;
    String type;

    public User(int id, String userName, String email, String password, String type) {
    	this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public JSONObject getJson(){
        JSONObject js=new JSONObject();
        js.put("id", id);
        js.put("name", userName);
        js.put("email", email);
        js.put("type", type);
        return js;
    }
}
