
package net.docusign.api._3;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SSN4Information complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SSN4Information">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SSN4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SSN4Information", propOrder = {
    "ssn4"
})
public class Ssn4Information
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SSN4")
    protected String ssn4;

    /**
     * Gets the value of the ssn4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsn4() {
        return ssn4;
    }

    /**
     * Sets the value of the ssn4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsn4(String value) {
        this.ssn4 = value;
    }

}
