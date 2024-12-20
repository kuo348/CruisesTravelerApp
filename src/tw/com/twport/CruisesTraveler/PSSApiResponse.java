package tw.com.twport.CruisesTraveler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>PSSApiResponse complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="PSSApiResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="processStatus" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="errorHappend" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="msgObj" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="errorMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="applyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PSSApiResponse", propOrder = {
    "processStatus",
    "errorHappend",
    "msg",
    "msgObj",
    "errorMsg",
    "applyNo"
})
public class PSSApiResponse {

    protected boolean processStatus;
    protected boolean errorHappend;
    protected String msg;
    protected Object msgObj;
    protected String errorMsg;
    protected String applyNo;

    /**
     * 取得 processStatus 特性的值.
     * 
     */
    public boolean isProcessStatus() {
        return processStatus;
    }

    /**
     * 設定 processStatus 特性的值.
     * 
     */
    public void setProcessStatus(boolean value) {
        this.processStatus = value;
    }

    /**
     * 取得 errorHappend 特性的值.
     * 
     */
    public boolean isErrorHappend() {
        return errorHappend;
    }

    /**
     * 設定 errorHappend 特性的值.
     * 
     */
    public void setErrorHappend(boolean value) {
        this.errorHappend = value;
    }

    /**
     * 取得 msg 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 設定 msg 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

    /**
     * 取得 msgObj 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMsgObj() {
        return msgObj;
    }

    /**
     * 設定 msgObj 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMsgObj(Object value) {
        this.msgObj = value;
    }

    /**
     * 取得 errorMsg 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 設定 errorMsg 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

    /**
     * 取得 applyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyNo() {
        return applyNo;
    }

    /**
     * 設定 applyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyNo(String value) {
        this.applyNo = value;
    }

}
