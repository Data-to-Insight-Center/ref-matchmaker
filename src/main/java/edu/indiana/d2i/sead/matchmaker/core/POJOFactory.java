package edu.indiana.d2i.sead.matchmaker.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.Jsonschema2Pojo;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.rules.RuleFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class POJOFactory {

	public static void createClass(String className, String packageName, JsonNode json, File output) throws IOException{
		JCodeModel codeModel = new JCodeModel();
		JsonNode jsonSchema=new SchemaGenerator().schemaFromExample(json);
		String jsonString =jsonSchema.toString();
		new SchemaMapper().generate(codeModel, className, packageName,jsonString);
		codeModel.build(output);
		//Jsonschema2Pojo.generate(new DefaultGenerationConfig());;
	}

	public static void main(String[] args) throws JsonProcessingException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\person.json"));
		POJOFactory.createClass("Person","edu.indiana.d2i.sead.matchmaker.pojo", rootNode, new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\plugins\\ruleset1\\src\\main\\java"));
		
		rootNode = mapper.readTree(new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\research_object.json"));
		POJOFactory.createClass("ResearchObject","edu.indiana.d2i.sead.matchmaker.pojo", rootNode, new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\plugins\\ruleset1\\src\\main\\java"));
		
		rootNode = mapper.readTree(new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\repositories.json"));
		POJOFactory.createClass("Repositories","edu.indiana.d2i.sead.matchmaker.pojo", rootNode, new File("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\plugins\\ruleset1\\src\\main\\java"));
	} 
}
