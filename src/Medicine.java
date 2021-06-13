import org.json.simple.JSONObject;

public class Medicine {
	String company_name;
	String medicine_name;
	String composition;
	String type;
	String effectivePart;
	int quantity;
	double unit_price;
	
	Medicine(String mn, String cn, String comp, String ty, String effective, int q, int up){ 
        company_name=cn;
		medicine_name=mn;
		composition = comp;
		type = ty;
		effectivePart = effective;
		quantity=q;
		unit_price=up;
	}
	
	public JSONObject med(){
		JSONObject js=new JSONObject();
		js.put("company_name",company_name);
		js.put("medicine_name",medicine_name);
		js.put("composition",composition);
		js.put("type",type);
		js.put("effective_for",effectivePart);
		js.put("quantity",quantity);
		js.put("unit_price",unit_price);
		return js;
	}
	
	
}
