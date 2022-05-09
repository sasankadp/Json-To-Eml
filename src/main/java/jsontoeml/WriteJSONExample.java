package jsontoeml;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSONExample
{
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
    	//First Employee
    	JSONObject employeeDetails = new JSONObject();
    	employeeDetails.put("firstName", "Sasa");
    	employeeDetails.put("lastName", "Pere");
    	employeeDetails.put("website", "https://github.com/sasankadp?tab=repositories");
		employeeDetails.put("message","Message 1 to test message");

    	JSONObject employeeObject = new JSONObject(); 
    	employeeObject.put("employee", employeeDetails);
		
    	
    	//Add employees to list
    	JSONArray employeeList = new JSONArray();
    	employeeList.add(employeeObject);
    	
    	//Write JSON file
    	try (FileWriter file = new FileWriter("employees.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
