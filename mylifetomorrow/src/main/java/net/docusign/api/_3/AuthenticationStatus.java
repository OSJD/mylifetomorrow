
package net.docusign.api._3;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticationStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticationStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccessCodeResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="IDQuestionsResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="IDLookupResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="AgeVerifyResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="STANPinResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="OFACResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="PhoneAuthResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="LiveIDResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="FacebookResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="GoogleResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="LinkedinResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="SalesforceResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="TwitterResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="OpenIDResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="AnySocialIDResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *         &lt;element name="YahooResult" type="{http://www.docusign.net/API/3.0}EventResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticationStatus", propOrder = {
    "accessCodeResult",
    "idQuestionsResult",
    "idLookupResult",
    "ageVerifyResult",
    "stanPinResult",
    "ofacResult",
    "phoneAuthResult",
    "liveIdResult",
    "facebookResult",
    "googleResult",
    "linkedinResult",
    "salesforceResult",
    "twitterResult",
    "openIdResult",
    "anySocialIdResult",
    "yahooResult"
})
public class AuthenticationStatus
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "AccessCodeResult")
    protected EventResult accessCodeResult;
    @XmlElement(name = "IDQuestionsResult")
    protected EventResult idQuestionsResult;
    @XmlElement(name = "IDLookupResult")
    protected EventResult idLookupResult;
    @XmlElement(name = "AgeVerifyResult")
    protected EventResult ageVerifyResult;
    @XmlElement(name = "STANPinResult")
    protected EventResult stanPinResult;
    @XmlElement(name = "OFACResult")
    protected EventResult ofacResult;
    @XmlElement(name = "PhoneAuthResult")
    protected EventResult phoneAuthResult;
    @XmlElement(name = "LiveIDResult")
    protected EventResult liveIdResult;
    @XmlElement(name = "FacebookResult")
    protected EventResult facebookResult;
    @XmlElement(name = "GoogleResult")
    protected EventResult googleResult;
    @XmlElement(name = "LinkedinResult")
    protected EventResult linkedinResult;
    @XmlElement(name = "SalesforceResult")
    protected EventResult salesforceResult;
    @XmlElement(name = "TwitterResult")
    protected EventResult twitterResult;
    @XmlElement(name = "OpenIDResult")
    protected EventResult openIdResult;
    @XmlElement(name = "AnySocialIDResult")
    protected EventResult anySocialIdResult;
    @XmlElement(name = "YahooResult")
    protected EventResult yahooResult;

    /**
     * Gets the value of the accessCodeResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getAccessCodeResult() {
        return accessCodeResult;
    }

    /**
     * Sets the value of the accessCodeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setAccessCodeResult(EventResult value) {
        this.accessCodeResult = value;
    }

    /**
     * Gets the value of the idQuestionsResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getIdQuestionsResult() {
        return idQuestionsResult;
    }

    /**
     * Sets the value of the idQuestionsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setIdQuestionsResult(EventResult value) {
        this.idQuestionsResult = value;
    }

    /**
     * Gets the value of the idLookupResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getIdLookupResult() {
        return idLookupResult;
    }

    /**
     * Sets the value of the idLookupResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setIdLookupResult(EventResult value) {
        this.idLookupResult = value;
    }

    /**
     * Gets the value of the ageVerifyResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getAgeVerifyResult() {
        return ageVerifyResult;
    }

    /**
     * Sets the value of the ageVerifyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setAgeVerifyResult(EventResult value) {
        this.ageVerifyResult = value;
    }

    /**
     * Gets the value of the stanPinResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getStanPinResult() {
        return stanPinResult;
    }

    /**
     * Sets the value of the stanPinResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setStanPinResult(EventResult value) {
        this.stanPinResult = value;
    }

    /**
     * Gets the value of the ofacResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getOfacResult() {
        return ofacResult;
    }

    /**
     * Sets the value of the ofacResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setOfacResult(EventResult value) {
        this.ofacResult = value;
    }

    /**
     * Gets the value of the phoneAuthResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getPhoneAuthResult() {
        return phoneAuthResult;
    }

    /**
     * Sets the value of the phoneAuthResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setPhoneAuthResult(EventResult value) {
        this.phoneAuthResult = value;
    }

    /**
     * Gets the value of the liveIdResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getLiveIdResult() {
        return liveIdResult;
    }

    /**
     * Sets the value of the liveIdResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setLiveIdResult(EventResult value) {
        this.liveIdResult = value;
    }

    /**
     * Gets the value of the facebookResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getFacebookResult() {
        return facebookResult;
    }

    /**
     * Sets the value of the facebookResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setFacebookResult(EventResult value) {
        this.facebookResult = value;
    }

    /**
     * Gets the value of the googleResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getGoogleResult() {
        return googleResult;
    }

    /**
     * Sets the value of the googleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setGoogleResult(EventResult value) {
        this.googleResult = value;
    }

    /**
     * Gets the value of the linkedinResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getLinkedinResult() {
        return linkedinResult;
    }

    /**
     * Sets the value of the linkedinResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setLinkedinResult(EventResult value) {
        this.linkedinResult = value;
    }

    /**
     * Gets the value of the salesforceResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getSalesforceResult() {
        return salesforceResult;
    }

    /**
     * Sets the value of the salesforceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setSalesforceResult(EventResult value) {
        this.salesforceResult = value;
    }

    /**
     * Gets the value of the twitterResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getTwitterResult() {
        return twitterResult;
    }

    /**
     * Sets the value of the twitterResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setTwitterResult(EventResult value) {
        this.twitterResult = value;
    }

    /**
     * Gets the value of the openIdResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getOpenIdResult() {
        return openIdResult;
    }

    /**
     * Sets the value of the openIdResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setOpenIdResult(EventResult value) {
        this.openIdResult = value;
    }

    /**
     * Gets the value of the anySocialIdResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getAnySocialIdResult() {
        return anySocialIdResult;
    }

    /**
     * Sets the value of the anySocialIdResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setAnySocialIdResult(EventResult value) {
        this.anySocialIdResult = value;
    }

    /**
     * Gets the value of the yahooResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventResult }
     *     
     */
    public EventResult getYahooResult() {
        return yahooResult;
    }

    /**
     * Sets the value of the yahooResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResult }
     *     
     */
    public void setYahooResult(EventResult value) {
        this.yahooResult = value;
    }

}
