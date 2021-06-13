import org.json.simple.JSONObject;

public class UserProblemDTO {
	private int doctorId;
	private int contact;
	private int age;
	private int sufferingDays;
	private String symptom;
	private String desc;
	private String userName;
	private boolean active;
	private int problemId;
	private int userId;
	
	public UserProblemDTO(int contact, int age, String symptom, String description, int sufferingDays, 
			int doctorId, String name, boolean active, int id, int userId) {
		super();
		this.doctorId = doctorId;
		this.contact = contact;
		this.age = age;
		this.sufferingDays = sufferingDays;
		this.symptom = symptom;
		this.desc = description;
		this.userName = name;
		this.active = active;
		this.problemId = id;
		this.userId = userId;
	}
	
	public JSONObject getProblemJson() {
		JSONObject js = new JSONObject();
        js.put("doctorId", doctorId);
        js.put("contact", contact);
        js.put("age", age);
        js.put("sufferingDays", sufferingDays);
        js.put("symptom", symptom);
        js.put("desc", desc);
        js.put("userName", userName);
        js.put("active", active);
        js.put("problemId", problemId);
        js.put("userId", userId);
        return js;
	}
}
