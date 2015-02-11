/**
 * 
 */
package edu.indiana.d2i.sead.matchmaker.core;

/**
 * @author yuanluo
 *
 */
public class BasicRuleUtility {
	
	static String DEFAULT_UNIT = "MB";
	
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
		return computeBinaryUnitConverter(DEFAULT_UNIT);
		
	}
	
	public static void main(String[] args) {	
		BasicRuleUtility util=new BasicRuleUtility();
		double result=util.computeBinaryUnitConverter("b");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("B");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("KB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("MB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("GB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("TB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("PB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("EB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("ZB");
		System.out.println(result);
		result=util.computeBinaryUnitConverter("YB");
		System.out.println(result);
	}
}
