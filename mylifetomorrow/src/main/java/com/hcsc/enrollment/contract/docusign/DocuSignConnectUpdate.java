
package com.hcsc.enrollment.contract.docusign;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.docusign.api._3.DocuSignEnvelopeInformation;


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
 *         &lt;element name="DocuSignEnvelopeInformation" type="{http://www.docusign.net/API/3.0}DocuSignEnvelopeInformation" minOccurs="0"/>
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
    "docuSignEnvelopeInformation"
})
@XmlRootElement(name = "DocuSignConnectUpdate")
public class DocuSignConnectUpdate
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DocuSignEnvelopeInformation")
    protected DocuSignEnvelopeInformation docuSignEnvelopeInformation;

    /**
     * Gets the value of the docuSignEnvelopeInformation property.
     * 
     * @return
     *     possible object is
     *     {@link DocuSignEnvelopeInformation }
     *     
     */
    public DocuSignEnvelopeInformation getDocuSignEnvelopeInformation() {
        return docuSignEnvelopeInformation;
    }

    /**
     * Sets the value of the docuSignEnvelopeInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocuSignEnvelopeInformation }
     *     
     */
    public void setDocuSignEnvelopeInformation(DocuSignEnvelopeInformation value) {
        this.docuSignEnvelopeInformation = value;
    }

}
