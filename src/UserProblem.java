import org.json.simple.JSONObject;

public class UserProblem {

	private int doctorId;
	private int contact;
	private int age;
	private int sufferingDays;
	private String symptom;
	private String desc;
	private int userId;
	private boolean active;
	
	public UserProblem(int doctorId, int contact, int age, int sufferingDays, String symptom, String desc, int userId, boolean active) {
		super();
		this.doctorId = doctorId;
		this.contact = contact;
		this.age = age;
		this.sufferingDays = sufferingDays;
		this.symptom = symptom;
		this.desc = desc;
		this.userId = userId;
		this.active = active;
	}
	
	public JSONObject getProblemJson() {
		JSONObject js = new JSONObject();
        js.put("doctorId", doctorId);
        js.put("contact", contact);
        js.put("age", age);
        js.put("sufferingDays", sufferingDays);
        js.put("symptom", symptom);
        js.put("desc", desc);
        js.put("userId", userId);
        js.put("active", active);
        return js;
	}
}
