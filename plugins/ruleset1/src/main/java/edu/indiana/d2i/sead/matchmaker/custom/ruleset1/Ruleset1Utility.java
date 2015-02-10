package edu.indiana.d2i.sead.matchmaker.custom.ruleset1;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ruleset1Utility {
	
	static String DEFAULT_UNIT = "MB";
/*	
	public boolean sizeOK(JsonNode repo, JsonNode ro){
		boolean result=false;
		double repoSize=repo.path("max_size").path("value").intValue()*BinaryUnitConverter(repo.path("max_size").path("unit").asText());
		double roSize=repo.path("max_size").path("value").intValue()*BinaryUnitConverter(ro.path("max_size").path("unit").asText());
		if(repoSize>=roSize){
			result=true;
		}
		return result;
	}
*/	
	public double computeBinaryUnitConverter(String unit){
		if (unit.equals("b"))	return 1;
		if (unit.equals("B"))	return 8*computeBinaryUnitConverter("b");
		if (unit.equals("KB"))	return 1024*computeBinaryUnitConverter("B");
		if (unit.equals("MB"))	return 1024*computeBinaryUnitConverter("KB");
		if (unit.equals("GB"))	return 1024*computeBinaryUnitConverter("MB");
		if (unit.equals("TB"))	return 1024*computeBinaryUnitConverter("GB");
		if (unit.equals("PB"))	return 1024*computeBinaryUnitConverter("TB");
		if (unit.equals("EB"))	return 1024*computeBinaryUnitConverter("PB");
		if (unit.equals("ZB"))	return 1024*computeBinaryUnitConverter("EB");
		if (unit.equals("YB"))	return 1024*computeBinaryUnitConverter("ZB");
		return computeBinaryUnitConverter("MB");
		
	}
/*	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
*/	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Ruleset1Utility rulesImpl=new Ruleset1Utility();
		double result=rulesImpl.computeBinaryUnitConverter("b");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("B");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("KB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("MB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("GB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("TB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("PB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("EB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("ZB");
		System.out.println(result);
		result=rulesImpl.computeBinaryUnitConverter("YB");
		System.out.println(result);
	}

}
