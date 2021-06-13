import org.json.simple.JSONObject;

public class Doctor {
	int id;
	String name;
	String hospital;
	String experience;
	String degree;
	double fee;
	
	public Doctor(int id, String name, String hospital, String experience, String degree, double fee) {
		super();
		this.id = id;
		this.name = name;
		this.hospital = hospital;
		this.experience = experience;
		this.degree = degree;
		this.fee = fee;
	}

	public JSONObject getDoctor() {
		JSONObject js=new JSONObject();
		js.put("id", id);
		js.put("doctor_name", name);
		js.put("hospital_name", hospital);
		js.put("degree", degree);
		js.put("experience", experience);
		js.put("fee", fee);
		return js;
	}
	
}
