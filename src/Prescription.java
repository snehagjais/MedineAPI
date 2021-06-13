import org.json.simple.JSONObject;

public class Prescription {
	
	private int prescriptionId;
	private String doctorName;
	private String medicines;
	private String description;
	private String userName;
	private int userId;
	private int problemId;
	
	public Prescription(int prescriptionId, String doctorName, String medicines, String description,
			String userName, int userId, int problemId) {
		super();
		this.prescriptionId = prescriptionId;
		this.doctorName = doctorName;
		this.medicines = medicines;
		this.description = description;
		this.userName = userName;
		this.userId = userId;
		this.problemId = problemId;
	}

	public JSONObject getPrescriptionJson() {
		JSONObject js = new JSONObject();
        js.put("prescriptionId", prescriptionId);
        js.put("doctorName", doctorName);
        js.put("medicines", medicines);
        js.put("description", description);
        js.put("userName", userName);
        js.put("userId", userId);
        js.put("problemId", problemId);
        return js;
	}
}
