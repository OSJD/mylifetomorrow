
package net.docusign.api._3;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaultingDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaultingDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EODTransactionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EODTransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EODDocumentProfileID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaultingDetails", propOrder = {
    "eodTransactionName",
    "eodTransactionId",
    "eodDocumentProfileId"
})
public class VaultingDetails
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "EODTransactionName")
    protected String eodTransactionName;
    @XmlElement(name = "EODTransactionID")
    protected String eodTransactionId;
    @XmlElement(name = "EODDocumentProfileID")
    protected String eodDocumentProfileId;

    /**
     * Gets the value of the eodTransactionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEodTransactionName() {
        return eodTransactionName;
    }

    /**
     * Sets the value of the eodTransactionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEodTransactionName(String value) {
        this.eodTransactionName = value;
    }

    /**
     * Gets the value of the eodTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEodTransactionId() {
        return eodTransactionId;
    }

    /**
     * Sets the value of the eodTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEodTransactionId(String value) {
        this.eodTransactionId = value;
    }

    /**
     * Gets the value of the eodDocumentProfileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEodDocumentProfileId() {
        return eodDocumentProfileId;
    }

    /**
     * Sets the value of the eodDocumentProfileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEodDocumentProfileId(String value) {
        this.eodDocumentProfileId = value;
    }

}
