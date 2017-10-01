
package net.docusign.api._3;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocuSignEnvelopeInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocuSignEnvelopeInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EnvelopeStatus" type="{http://www.docusign.net/API/3.0}EnvelopeStatus" minOccurs="0"/>
 *         &lt;element name="DocumentPDFs" type="{http://www.docusign.net/API/3.0}ArrayOfDocumentPDF" minOccurs="0"/>
 *         &lt;element name="TimeZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimeZoneOffset" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocuSignEnvelopeInformation", propOrder = {
    "envelopeStatus",
    "documentPdFs",
    "timeZone",
    "timeZoneOffset"
})
public class DocuSignEnvelopeInformation
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "EnvelopeStatus")
    protected EnvelopeStatus envelopeStatus;
    @XmlElement(name = "DocumentPDFs")
    protected ArrayOfDocumentPdf documentPdFs;
    @XmlElement(name = "TimeZone")
    protected String timeZone;
    @XmlElement(name = "TimeZoneOffset")
    protected Integer timeZoneOffset;

    /**
     * Gets the value of the envelopeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link EnvelopeStatus }
     *     
     */
    public EnvelopeStatus getEnvelopeStatus() {
        return envelopeStatus;
    }

    /**
     * Sets the value of the envelopeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvelopeStatus }
     *     
     */
    public void setEnvelopeStatus(EnvelopeStatus value) {
        this.envelopeStatus = value;
    }

    /**
     * Gets the value of the documentPdFs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentPdf }
     *     
     */
    public ArrayOfDocumentPdf getDocumentPdFs() {
        return documentPdFs;
    }

    /**
     * Sets the value of the documentPdFs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentPdf }
     *     
     */
    public void setDocumentPdFs(ArrayOfDocumentPdf value) {
        this.documentPdFs = value;
    }

    /**
     * Gets the value of the timeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of the timeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZone(String value) {
        this.timeZone = value;
    }

    /**
     * Gets the value of the timeZoneOffset property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeZoneOffset() {
        return timeZoneOffset;
    }

    /**
     * Sets the value of the timeZoneOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeZoneOffset(Integer value) {
        this.timeZoneOffset = value;
    }

}
