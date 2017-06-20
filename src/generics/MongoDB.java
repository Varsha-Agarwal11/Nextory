package generics;

/*import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.javascript.host.Set;
import common.AutomationConstants;

public class MongoDB implements AutomationConstants
{
	public static String driver="jdbc:mongo://localhost:27017/nlob";
	public static String dbUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "MONGOURL"); 
	public static String username=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "MONGOUSER"); 
	public static String password=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "MONGOPWD");  
	static Logger log = Logger.getLogger(MongoDB.class);
	
	public static String executeQueryMongo(String query1)  
	{
		DBCollection table = db.getCollection("user");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "mkyong");

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) 
		{
			System.out.println(cursor.next());
		}
		return query1;	
	}
	
	public static String executeUpdateMongo(String query2)
	{
		DBCollection table = db.getCollection("user");

		BasicDBObject query = new BasicDBObject();
		query.put("name", "mkyong");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "mkyong-updated");

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);
	}
	
	public Connection getConnection()
	{
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
             
            List<String> databases = mongoClient.getDatabaseNames();
             
            for (String dbName : databases) {
                System.out.println("- Database: " + dbName);
                 
                DB db = mongoClient.getDB(dbName);
                 
                Set<String> collections = db.getCollectionNames();
                for (String colName : collections) 
                {
                    System.out.println("\t + Collection: " + colName);
                }
            }
             
            mongoClient.close();
             
        }
		catch (UnknownHostException ex)
		{
            ex.printStackTrace();
        }
         
    }
}
 
 */

/*
@RequestMapping(value="/test1",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody void getMongoDb( HttpServletRequestc request, HttpServletResponse response)
{
 //Map<String, String> params = request.getParameterMap();
 try {
  JsonFactory jsonFactory = new JsonFactory(); 
  JsonGenerator jsonGenerator =  jsonFactory.createJsonGenerator(response.getOutputStream());
  Datastore ds = nestMongoDBMorphiaUtil.getConnection();

  DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  ObjectMapper objectMapper= new ObjectMapper();
  objectMapper.getSerializationConfig().setDateFormat(myDateFormat);
  objectMapper.getDeserializationConfig().setDateFormat(myDateFormat); 
  jsonGenerator.setCodec(objectMapper);
  jsonGenerator.setCodec(objectMapper);
  jsonGenerator.writeStartObject();
  jsonGenerator.writeArrayFieldStart("products");

  testCollection ts = new testCollection();            //TestCollection1
  ts.setId("deba3");                                      
  ts.setDescription("MONGO MONGO 12");
  ts.setTitle("TEST 456");

  //for insert or update
  ds.updateFirst(ds.createQuery(testCollection.class).field("id").equal("deba3"), ts,true);

  //for update of particular fields
  UpdateOperations<testCollection> ops = ds.createUpdateOperations(testCollection.class).set("description", "MONGO MONGO deba");
  ds.update(ds.createQuery(testCollection.class).field("id").equal("deba3"), ops,true);

  //for specific save
  testCollection ts1 = new testCollection();            //TestCollection2
  ts1.setId("deba4");
  ts1.setDescription("MONGO MONGO 12");
  ts1.setTitle("TEST 456");

  ds.save(ts1);

  //for retrival
  List<testCollection>  cool = ds.createQuery(testCollection.class).order("description") .asList();
  for(testCollection a: cool)
  {
   jsonGenerator.writeObject(a);
  }

  jsonGenerator.writeEndArray();
  jsonGenerator.close();
 }
 catch (Exception e) 
 {
  e.printStackTrace();
 }
}
*/

