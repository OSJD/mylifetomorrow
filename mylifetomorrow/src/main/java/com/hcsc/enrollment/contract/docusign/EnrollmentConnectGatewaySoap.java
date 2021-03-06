package com.hcsc.enrollment.contract.docusign;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2016-11-18T19:08:56.398-06:00
 * Generated source version: 2.7.11
 * 
 */
@WebService(targetNamespace = "http://enrollment.hcsc.com/contract/docusign", name = "EnrollmentConnectGatewaySoap")
@XmlSeeAlso({ObjectFactory.class, net.docusign.api._3.ObjectFactory.class})
public interface EnrollmentConnectGatewaySoap {

    /**
     * <strong>DocuSign Connect Update</strong><br />Called by DocuSign after a customer has completed signing.
     */
    @WebResult(name = "DocuSignConnectUpdateResult", targetNamespace = "http://enrollment.hcsc.com/contract/docusign")
    @RequestWrapper(localName = "DocuSignConnectUpdate", targetNamespace = "http://enrollment.hcsc.com/contract/docusign", className = "com.hcsc.enrollment.contract.docusign.DocuSignConnectUpdate")
    @WebMethod(operationName = "DocuSignConnectUpdate")
    @ResponseWrapper(localName = "DocuSignConnectUpdateResponse", targetNamespace = "http://enrollment.hcsc.com/contract/docusign", className = "com.hcsc.enrollment.contract.docusign.DocuSignConnectUpdateResponse")
    public java.lang.String docuSignConnectUpdate(
        @WebParam(name = "DocuSignEnvelopeInformation", targetNamespace = "http://enrollment.hcsc.com/contract/docusign")
        net.docusign.api._3.DocuSignEnvelopeInformation docuSignEnvelopeInformation
    );
}
