package edu.indiana.d2i.sead.matchmaker.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class POJOGenerator {
	
	private JsonNode jsonTree=null;
	private Class<?> pojo=null;
	
	public POJOGenerator(String className) throws ClassNotFoundException{
		ClassLoader cls =ClassLoader.getSystemClassLoader();
		this.pojo=cls.loadClass(className);
	}
	
	public void fromPath(String filePath){
		ObjectMapper mapper = new ObjectMapper();
		 
		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(
				new FileReader(filePath));
			JsonNode rootNode = mapper.readTree(fileReader);
			this.jsonTree=rootNode;	
	 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fromFile(File jsonFile){
		ObjectMapper mapper = new ObjectMapper();
		 
		try {
			JsonNode rootNode = mapper.readTree(jsonFile);
			this.jsonTree=rootNode;
				
	 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fromString(String jsonString){
		ObjectMapper mapper = new ObjectMapper();
		 
		try {
			JsonNode rootNode = mapper.readTree(jsonString);
			this.jsonTree=rootNode;
				
	 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JsonNode getJsonTree(){
		return this.jsonTree;
	}
	
	public Object generate() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		
		if(this.jsonTree.isArray()){
			ArrayNode jarray=(ArrayNode)this.jsonTree;
			Object[] objs=new Object[jarray.size()];
			for (int i=0;i<jarray.size();i++){
				objs[i]=mapper.readValue(jarray.get(i).toString(), this.pojo);
			}
			return objs;
		}else {
			Object obj=mapper.readValue(this.jsonTree.toString(), this.pojo);
			return obj;
		}
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/*
		// TODO Auto-generated method stub
		POJOGenerator repositories = new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.Repository");
		repositories.fromPath("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\repositories.json");
		POJOGenerator person = new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.Person");
		person.fromPath("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\person.json");
		POJOGenerator researchObject=new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.ResearchObject");
		researchObject.fromPath("C:\\Users\\yuanluo\\WorkZone\\workspace\\MatchMaker\\profile\\research_object.json");
		
		Object[] repos=(Object[]) repositories.generate();
		for(Object repo: repos){
			Class<?> classTypeRepository=Class.forName("edu.indiana.d2i.sead.matchmaker.pojo.Repository");
			Object maxSize=classTypeRepository.cast(repo).getClass().getMethod("getMaxSize", null).invoke(repo);
			Class<?> classTypeMaxSize=Class.forName("edu.indiana.d2i.sead.matchmaker.pojo.MaxSize");
			Object value=classTypeMaxSize.cast(maxSize).getClass().getMethod("getValue", null).invoke(maxSize);
			System.out.println(value.toString());
		}
*/		
	}

}
