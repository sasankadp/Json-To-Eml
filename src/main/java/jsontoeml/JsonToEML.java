package jsontoeml;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class JsonToEML {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws MessagingException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try {
            //Read JSON file
            Object obj = jsonParser.parse(new FileReader("employees.json"));
            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
            //Iterate over employee array
            Iterator<JSONObject> iterator = employeeList.iterator();
            while(iterator.hasNext()){
                parseEmployeeObject(iterator.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseEmployeeObject(JSONObject employee) throws IOException, MessagingException {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String from = (String) employeeObject.get("firstName");
        System.out.println(from);

        //Get employee last name
        String to = (String) employeeObject.get("lastName");
        System.out.println(to);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);

        //Content
        String message = (String) employeeObject.get("message");
        createMessage(from, to, website, message);
    }

    public static Message createMessage(String from, String to, String subject, String content) throws MessagingException, IOException {
        File outputFile = new File("draft.eml");
        Message msg = new MimeMessage((Session) null);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        MimeBodyPart body = new MimeBodyPart();
        body.setText(content);
        msg.setContent(new MimeMultipart(body));

        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            msg.writeTo(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Desktop.getDesktop().open(outputFile);
        return msg;
    }

}
