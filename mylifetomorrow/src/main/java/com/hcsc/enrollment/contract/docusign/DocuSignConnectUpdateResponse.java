
package com.hcsc.enrollment.contract.docusign;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocuSignConnectUpdateResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "docuSignConnectUpdateResult"
})
@XmlRootElement(name = "DocuSignConnectUpdateResponse")
public class DocuSignConnectUpdateResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DocuSignConnectUpdateResult")
    protected String docuSignConnectUpdateResult;

    /**
     * Gets the value of the docuSignConnectUpdateResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocuSignConnectUpdateResult() {
        return docuSignConnectUpdateResult;
    }

    /**
     * Sets the value of the docuSignConnectUpdateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocuSignConnectUpdateResult(String value) {
        this.docuSignConnectUpdateResult = value;
    }

}
