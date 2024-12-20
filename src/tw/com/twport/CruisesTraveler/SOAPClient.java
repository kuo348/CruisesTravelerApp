package tw.com.twport.CruisesTraveler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import org.tempuri.PSSApiResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import jakarta.xml.bind.Element;

import org.w3c.dom.Node;
public class SOAPClient {
/**
* soap调用webService
*/
public static String sendSoapPost(String url, String xml, String contentType, String soapAction) {
HttpURLConnection conn = null;
OutputStream out = null;
String returnXml = "";
try {
conn = (HttpURLConnection) new URL(url).openConnection();
conn.setRequestProperty("Content-Type", contentType);
if (null != soapAction) {
conn.setRequestProperty("SOAPAction", soapAction);
}
conn.setRequestMethod("POST");
conn.setConnectTimeout(5000);
conn.setDoOutput(true); // 向服务器发送数据
conn.setDoInput(true); // 获取服务端的响应
conn.connect();
out = conn.getOutputStream();
out.write(xml.getBytes("UTF-8"));
out.flush();
out.close();
int code = conn.getResponseCode();
String tempString = null;
StringBuffer sb = new StringBuffer();
BufferedReader bufferedReader = null;
if (code == conn.HTTP_OK) {
bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
} else {
bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
}
while ((tempString = bufferedReader.readLine()) != null) {
sb.append(tempString);
}
if (null != bufferedReader) {
bufferedReader.close();
}
// 响应报文
returnXml = sb.toString();
} catch (Exception e) {
e.printStackTrace();
}
return returnXml;
}
public static void getSubNode(Node node)
{
	NodeList nodeList=node.getChildNodes();
	for (int j = 0; j< nodeList.getLength(); j++) {
        Node node2 = nodeList.item(j);
        System.out.println("Node name: " + node2.getNodeName());
        
        if(node.hasChildNodes()) {
        	//System.out.println("Node value: " + node2.getTextContent());
        	getSubNode(node2);
        }
       
    }
	
}
public static PSSApiResponse getvaluefromxml(String inxml) {
String tempxml = inxml;
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder;
InputSource is;
String outval = "";
PSSApiResponse rsp=new PSSApiResponse();
try {
builder = factory.newDocumentBuilder();
is = new InputSource(new StringReader(tempxml));
Document doc = builder.parse(is);
NodeList n1=doc.getElementsByTagName("processStatus");
System.out.println(((Node)n1.item(0)).getTextContent());
rsp.setProcessStatus(((Node)n1.item(0)).getTextContent().equals("true"));
NodeList n2=doc.getElementsByTagName("errorHappend"); 
rsp.setErrorHappend(((Node)n2.item(0)).getTextContent().equals("True"));
NodeList n3=doc.getElementsByTagName("msg");    
rsp.setMsg(((Node)n3.item(0)).getTextContent());
NodeList n4=doc.getElementsByTagName("msgObj");    
rsp.setMsgObj(((Node)n4.item(0)).getTextContent());
NodeList n5=doc.getElementsByTagName("errorMsg");    
//rsp.setErrorMsg(((Node)n5.item(0)).getTextContent());
NodeList n6=doc.getElementsByTagName("applyNo");    
//rsp.setApplyNo(((Node)n6.item(0)).getTextContent());
// System.out.println(outval);
} catch (ParserConfigurationException e) {
} catch (SAXException e) {
} catch (IOException e) {
}
return rsp;
}
/*
public static void main(String[] args) {
String requrl = "http://localhost:8080/SOAPServer/services/HelloWorld";
String reqxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
+ "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\r\n"
+ " <soap:Body>\r\n" + " <addValue xmlns=\"http://service.web.com\">\r\n"
+ " <value>20</value>\r\n" + " </addValue>\r\n" + " </soap:Body>\r\n" + "</soap:Envelope>";
String reqcontentType = "text/xml; charset=utf-8";
String reqsoapAction = "";
String resxml = sendSoapPost(requrl, reqxml, reqcontentType, reqsoapAction);
System.out.println(resxml);
System.out.println(getvaluefromxml(resxml));
}*/
}