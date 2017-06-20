package generics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import common.AutomationConstants;


//@Service("nestMongoDBMorphiaUtil")
public class MongoDBUtilMorphia implements AutomationConstants
{
	private static MongoClient mongoClient;
	private  String user = "";
	private  String password = "";
	private  String proddatabase = "nextory";
	private  String logdatabase ="nlob";
	private  String authDatabases="nlob,e2go_old,mongoui,nextory";
	private  String serverAddress = "localhost:27017";
	public static TimeZone timeZone = TimeZone.getDefault();
	
	private static Properties prop = new Properties();
	
	private static final Map<String,Datastore> dataStoreCache = new HashMap<String,Datastore>();

	public MongoDBUtilMorphia() 
	{
		System.setProperty("DEBUG.MONGO", "false");
	}
	public String getAuthDatabases() 
	{
		return authDatabases;
	}

	public void setAuthDatabases(String authDatabases) 
	{
		this.authDatabases = authDatabases;
	}

	public String getUser() 
	{
		return user;
	}

	public void setUser(String user) 
	{
		this.user = user;
	}

	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProddatabase() {
		return proddatabase;
	}
	public void setProddatabase(String proddatabase) 
	{
		this.proddatabase = proddatabase;
	}
	public String getLogdatabase()
	{
		return logdatabase;
	}
	public void setLogdatabase(String logdatabase) {
		this.logdatabase = logdatabase;
	}
	
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public MongoClient getInstance() 
	{
       if(mongoClient != null)
       {
        	return mongoClient;
       }
		String[] serverAddressList = StringUtils.split(serverAddress, ",");
			List<ServerAddress> addressList = new ArrayList<ServerAddress>();
			for (int i = 0; i < serverAddressList.length; i++) {
				String[] serverdetails = StringUtils.split(serverAddressList[i], ":");
				if(serverdetails.length == 2)
					addressList.add(new ServerAddress(serverdetails[0],Integer.parseInt(serverdetails[1])));
				else
					addressList.add(new ServerAddress(serverdetails[0]));
			}
			
			if(StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password)){
				final String[] dbtoAuth = StringUtils.split(authDatabases, ",");
	    		final List<MongoCredential> mongoCred = new ArrayList<MongoCredential>();
	    		for (int i = 0; i < dbtoAuth.length; i++) {
	    			mongoCred.add(MongoCredential.createCredential(user, dbtoAuth[i], password.toCharArray()));
				}
	    		mongoClient = new MongoClient(addressList,mongoCred);
	    		//MongoCredential mongoCredential =  null;
	    		//mongoCredential = MongoCredential.createMongoCRCredential(user, proddatabase, password.toCharArray());
	    		//mongoClient = new MongoClient(new ServerAddress(serverAddress),Arrays.asList(mongoCredential));
	    		//mongoClient = new MongoClient(addressList,Arrays.asList(mongoCredential));
	    		//com.mongodb.ReadPreference.primaryPreferred();
	    	}else{
	    		mongoClient = new MongoClient(addressList);
	    	}
			//BSON.addEncodingHook(DateTime.class, new JodaTimeTransformer());
			//BSON.addDecodingHook(Date.class, new JodaTimeTransformer());
			//BSON.addDecodingHook(Timestamp.class,new JodaTimeTransformer());
			//BSON.addDecodingHook(java.sql.Date.class,new JodaTimeTransformer());
			mongoClient.setWriteConcern(WriteConcern.SAFE);
			return mongoClient;
    }
	
	public  MongoCollection<Document> getMongoCollection(String db,String collectionName) {
		return mongoClient.getDatabase(db).getCollection(collectionName);
	}
	
	public  MongoCollection<Document> getMongoCollection(String collectionName) {
		return mongoClient.getDatabase(proddatabase).getCollection(collectionName);
	}
	
	public  MongoCollection<Document> getMongoCollectionForNex(String collectionName) {
		return mongoClient.getDatabase(proddatabase).getCollection(collectionName);
	}
	
	public  MongoCollection<Document> getMongoCollectionNlob(String collectionName) {
		return mongoClient.getDatabase(logdatabase).getCollection(collectionName);
	}
	
	public  org.mongodb.morphia.Datastore getConnection() {
		return getMorphiaDatastore(proddatabase);
	}
	// To be completed
/*	public AggregateIterable<Document> aggregate(NestAggregationPipeLine pipeline){
		try {
			mongoClient = getInstance();
			MongoDatabase mongoDatabase =  mongoClient.getDatabase(proddatabase);
			return pipeline.aggregate(mongoDatabase);
		} catch (UnknownHostException e) {
			throw new MongoNotAvailableException(e);
		}
	}*/
	
	public  MongoDatabase getMongoConnection(String alternateDB) {
			return mongoClient.getDatabase(alternateDB);
	}
	
	
	public  MongoDatabase getLogMongoConnection() {
		return mongoClient.getDatabase(logdatabase);
	}
	
	/*public  MongoCollection<Document> getMongoCollection(String collection) {
		return getMongoConnection(database).getCollection(database);
	}*/
	//-------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------Nextory------------------------------------------------------------
	public  Datastore getMorphiaDatastoreForProduct() {       
		return getMorphiaDatastore(proddatabase);
	}
	//-----------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------Nlob-------------------------------------------------------------------
	public  Datastore getMorphiaDatastoreForNlob() {
		return getMorphiaDatastore(logdatabase);
	}
	
	private  Datastore getMorphiaDatastore(String schema) 
	{
		if(dataStoreCache.containsKey(schema))
			return dataStoreCache.get(schema);		
		mongoClient = getInstance();
		mongoClient.setWriteConcern(WriteConcern.FSYNCED);
		Morphia morphia1 = new Morphia();
		morphia1.getMapper().getOptions().setStoreEmpties(true);
		morphia1.getMapper().getOptions().setStoreNulls(true);
		Datastore db = morphia1.createDatastore(mongoClient, schema);
		db.ensureIndexes();
		dataStoreCache.put(schema, db);
		return db;
	}
	
/*	public  Datastore getCollection(String collectionName) throws UnknownHostException
	{
		//MongoCollection<Document> collection =null;				
		Datastore
		return ;		
	}*/
	 
	    /*public static void main( String[] args ) throws UnknownHostException, MongoException {
	 
	     
	    }	*/
	 @Bean
	 public MongoOperations mongoNxDB() throws Exception 
	 { 
		// System.out.println("Creating bean mongoTemplate "+proddatabase);
		    return new MongoTemplate(new SimpleMongoDbFactory(mongoClient, proddatabase));
	 } 
	 
	 @Bean
	 public MongoOperations mongoNlobDB() throws Exception
	 { 
		// System.out.println("Creating bean mongoNlobTemplate "+logdatabase);
		 MongoDbFactory  mongoDbFactory = new SimpleMongoDbFactory(mongoClient, logdatabase);
		//remove _class
		 DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter =	new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
			
		    return new MongoTemplate(mongoDbFactory,converter);
	 } 
	
	}