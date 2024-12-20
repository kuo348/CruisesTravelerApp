package tw.com.twport.CruisesTraveler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLoggerContextFactory;
import javax.xml.namespace.QName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.xml.bind.DatatypeConverter;

import java.text.ParseException;
/*
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Statement;*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


public class CruisesTraveler {
	private static final Logger logger = (Logger) LogManager.getLogger(CruisesTraveler.class);	

    public static void ReadData(Connection conn,List<String> row) {
		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>";
  
		try {
			
			// Connection conn=null;
			// Statement stmt =null;
			String vesselEName = row.get(9);
			String vesselCName = row.get(10);
			String sDate = row.get(15);
			String sTime = row.get(17);
			String eDate = row.get(19);
			String eTime = row.get(21);
			String status = row.get(row.size() - 2);
			// System.out.println(row);
			
			// conn = DriverManager.getConnection(connectionUrl);
			  
			  String sql = "insert into TPNETCT(C0,C1,C2,C3,C4," +
			  "C5,C6,C7,C8,C9,"+ 
			  "C10,C11,C12,C13,C14,"+ 
			  "C15,C16,C17,C18,C19,"+ 
			  "C20,C21,C22,C23,C24,"+ 
			  "C25,C26,C27,C28,C29,"+ 
			  "C30,C31,C32)\r\n";
			  sql+="values(?,?,?,?,?,"+
					 "?,?,?,?,?,"+
					 "?,?,?,?,?,"+
					 "?,?,?,?,?,"+
					 "?,?,?,?,?,"+
					 "?,?,?,?,?,"+
					 "?,?,?)\r\n";
			  PreparedStatement pstmt = conn.prepareStatement(sql);
			  for(int i=0;i<row.size();i++)
				  pstmt.setString(i+1,row.get(i));
			  pstmt.execute();
			  System.out.println(String.format("英文船名:%-20s,中文船名:%-10s,到港日期:%-16s,離港日期:%-16s,狀態:%-4s", vesselEName.trim(),
						vesselCName.trim(), sDate + " " + sTime, eDate + " " + eTime, status));
			// displayRow("PRODUCTS", rs);
		}
		// Handle any errors that may have occurred.
		catch(SQLException e) {
		    e.printStackTrace();
		   System.out.println(e.getMessage());
	     }
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	protected static Workbook getWorkbook(String path) throws FileNotFoundException, IOException {
		Workbook wb = null;
		if(path == null) {
			System.out.println(path);
			System.out.println("File Path is Empty!!!");
			return null;
		}
		String extString = path.substring(path.lastIndexOf("."));
		InputStream is = new FileInputStream(path);
		if(".xls".equals(extString)){
		    wb = new HSSFWorkbook(is);
		}else {
		    wb = new XSSFWorkbook(is);
		}
		return wb;
	}

	protected static Sheet getSheet(Workbook workbook, int sheetNo) {
		return (Sheet) workbook.getSheetAt(sheetNo);
	}

	protected static String readCell(Cell cell) {
		return (String) getCellFormatValue(cell);
	}

	protected static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case NUMERIC:
				cellValue = cell.getNumericCellValue();
				break;
			case FORMULA:
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue();
				} else {
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	protected static List<List<String>> readFields(Workbook workbook, int sheetNo, int firstRow, int firstCol)
			throws Exception {
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetNo);
		Row row = sheet.getRow(0);
		int rownum = (int) sheet.getPhysicalNumberOfRows();
		int colnum = row.getPhysicalNumberOfCells();
		List<List<String>> list = new ArrayList<>();
		List<String> _inner;
		for (int i = firstRow; i < rownum; i++) {
			row = (Row) sheet.getRow(i);
			_inner = new ArrayList<>();
			if (row != null) {
				for (int j = firstCol; j < colnum; j++) {
					_inner.add(readCell(row.getCell(j)));
				}
				list.add(_inner);
			} else {
				break;
			}
		}
		return list;
	}
	protected static String getDbConnectionString(String propName) {
		String rs="";
		String file="./config.properties";
		Properties prop = new Properties();
		try {
			if(System.getProperty("os.name").toLowerCase().startsWith("windows")){	
				String filePath=new File(CruisesTraveler.class.getProtectionDomain().
		        		  getCodeSource().getLocation().getPath()).getParent();
				
				filePath=  filePath.replace("/", "\\");
				 file = filePath+"\\config.properties";
			}	
			System.out.println(file);
		    //load a properties file from class path, inside static method
			InputStream input = new FileInputStream(file);
		    prop.load(input);

		    //get the property value and print it out
		    byte []data = DatatypeConverter.parseBase64Binary(prop.getProperty(propName));
		    rs = new String(data, StandardCharsets.US_ASCII);	   
		   

		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return rs;		
	}
	public static void main(String[] args) {
		System.setProperty("log4j2.loggerContextFactory","org.apache.logging.log4j.simple.SimpleLoggerContextFactory");
		logger.info("CruieseTraveler start...");	
		// TODO Auto-generated method stub
		Date today = new Date();
		Date nextday =org.apache.commons.lang3.time.DateUtils.addDays(today, 10);
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		if(args.length > 0) {
			for(int i=0;i<args.length;i++) {
				try {
				if(args[i].endsWith("-s")&&i+1<=args.length) {					
						today=sm.parse(args[i+1].toString());					
				}
				if(args[i].endsWith("-e")&&i+1<=args.length) {					
	                  nextday=sm.parse(args[i+1].toString());					
				}					

			  } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}	
			
		}
		
		// System.out.
		String requrl = "https://tpnet.twport.com.tw/HOPWS/WS/CruisesTravelerWebService.asmx";

		String soapRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>\r\n" + "<GetExcel xmlns=\"http://tempuri.org/\">\r\n" +
				// "<sDate>2024/10/07</sDate>\r\n"+
				// "<eDate>2024/10/15</eDate>\r\n"+
				"<sDate>" + sm.format(today) + "</sDate>\r\n" + "<eDate>"
				+ sm.format(nextday) + "</eDate>\r\n"
				+ "<cruMode></cruMode>\r\n" + "<shipType></shipType>\r\n" + "<cruCate></cruCate>\r\n"
				+ "<vesType>1</vesType>\r\n" + "<firstCru></firstCru>\r\n" + "<dirRoute></dirRoute>\r\n"
				+ "<vesselCname></vesselCname>\r\n" + "<vesselNo></vesselNo>\r\n" + "<callSign></callSign>\r\n"
				+ "<port>KEL</port>\r\n" + "</GetExcel>\r\n" + "</soap:Body>\r\n" + "</soap:Envelope>";
		String reqcontentType = "text/xml; charset=utf-8";
		String reqsoapAction = "http://tempuri.org/GetExcel";
		String resxml = SOAPClient.sendSoapPost(requrl, soapRequest, reqcontentType, reqsoapAction);
		// System.out.println(resxml);
		PSSApiResponse rsp = SOAPClient.getvaluefromxml(resxml);
		if (!rsp.isErrorHappend() && rsp.isProcessStatus()) {
			// System.out.println(rsp.getMsgObj());
			byte[] data = DatatypeConverter.parseBase64Binary(rsp.getMsgObj().toString());
			try {
				String outputPath=new File(CruisesTraveler.class.getProtectionDomain().
		        		  getCodeSource().getLocation().getPath()).getParent();
				String file = outputPath+"/CruisesTravelerList.xls";
				//System.out.println(System.getProperty("os.name").toLowerCase());
				if(System.getProperty("os.name").toLowerCase().startsWith("windows")){	
					System.out.println(outputPath);
					outputPath = outputPath.replace("/", "\\");
					 file = outputPath+"\\CruisesTravelerList.xls";
				}				
				System.out.println(file);
		        //System.out.println(CruisesTraveler.class.getProtectionDomain().
		       // 		  getCodeSource().getLocation().getPath());
				FileOutputStream stream = new FileOutputStream(file);
				stream.write(data);
				stream.close();
				Thread.sleep(1000);
				System.out.println("file output complete...");
				Workbook wb = getWorkbook(file);
				if (wb != null) {
					System.out.println("read file successful!! ");
					// Sheet sheet1= getSheet(wb,0);
					List<List<String>> mylist = readFields(wb, 0, 0, 0);
					// System.out.println(mylist);
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String connectionUrl = getDbConnectionString("jdbc-driver");
					String userId=getDbConnectionString("UserID");
					String passwd=getDbConnectionString("Password");
					System.out.println(userId);
					System.out.println(passwd);
					System.out.println(connectionUrl);
					Enumeration<Driver> drivers = DriverManager.getDrivers();			        
			        if (!drivers.hasMoreElements()) {
			            System.out.println("No JDBC drivers found in the classpath.");
			        } else {
			            System.out.println("Available JDBC drivers:");
			            int count = 1;
			            while (drivers.hasMoreElements()) {
			                Driver driver = drivers.nextElement();
			                System.out.println(count + ". " + driver.getClass().getName());
			                count++;
			            }
			        }
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");               
					Connection conn= DriverManager.getConnection(connectionUrl,userId,passwd);
					System.out.println(conn.isClosed());
					Statement stmt = conn.createStatement();
				    String sql = "truncate table TPNETCT";
					boolean rs = stmt.execute(sql);
					//System.out.println("result:"+String.valueOf(rs));
                   System.out.println("truncate table TPNETCT is complete!!! ");
					
					
					for (int i = 2; i < mylist.size(); i++) {
						List<String> collist = mylist.get(i);
						// System.out.println(collist);
						if (i >= 2) {							
							ReadData(conn,collist);
						}
					}
					conn.close();

				}else {
					System.out.println("WB is null!!! ");
					
				}

			} catch (FileNotFoundException e) {
				e.getStackTrace();
				System.out.println(e.getMessage());

			} catch (Exception e) {
				//e.getStackTrace();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String sStackTrace = sw.toString(); // stack trace as a string
				System.out.println(sStackTrace);				
				System.out.println(e.getMessage());
			}

		}
	}

}
