package com.hcsc.enrollment.facade.jaxws;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import net.docusign.api._3.ArrayOfRecipientStatus;
import net.docusign.api._3.ArrayOfTabStatus;
import net.docusign.api._3.DocuSignEnvelopeInformation;
import net.docusign.api._3.EnvelopeStatus;
import net.docusign.api._3.RecipientStatus;
import net.docusign.api._3.TabStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.hcsc.enrollment.contract.docusign.EnrollmentConnectGatewaySoap;



/**
 * This web service implementation will be used to capture DocuSign TAB data. 
 * This web service will be called my DocuSign Connect and push envelope data to Enrollment system.
 */

@Component("enrollmentConnectServiceImpl")
public class EnrollmentConnectServiceImpl implements EnrollmentConnectGatewaySoap {

	private static final Logger LOG = LoggerFactory
			.getLogger(EnrollmentConnectServiceImpl.class);

	private static final JAXBContext CONTEXT = initContext();
	private static final QName DocuSignEnvelopeInformation_QNAME=new QName("http://www.docusign.net/API/3.0","DocuSignEnvelopeInformation");
	private static JAXBContext initContext() {
		try {
			if(CONTEXT==null){
				return JAXBContext.newInstance("net.docusign.api._3");
			} else {
				return CONTEXT;
			}
		} catch (JAXBException jxException) {
			LOG.error(
					"An error occured while initializing JAXB Context for DocuSign",
					jxException);
			throw new RuntimeException(
					"An error occured while initializing JAXB Context for DocuSign");
		}

	}
	private static final List<String> DOCUSIGN_FIELDS_TO_LOAD=new ArrayList<String>();
	
	static{
		try {
			final Resource docuSignFieldResource=new ClassPathResource("docusign/DocuSignFieldsToLoad.properties");
			Properties prop=new Properties();
			prop.load(docuSignFieldResource.getInputStream());
			Enumeration<Object> enumObj=prop.keys();
			while(enumObj.hasMoreElements()){
				DOCUSIGN_FIELDS_TO_LOAD.add((String)enumObj.nextElement());
			}
		} catch (IOException e) {
			LOG.error("DOCUSIGN LOAD FIELD NAME PROPERTIES ERROR",e);
			throw new RuntimeException("DOCUSIGN LOAD FIELD NAME PROPERTIES ERROR");
		}

	}
	

	public String docuSignConnectUpdate(
			DocuSignEnvelopeInformation docuSignEnvelopeInformation) {
		try {
			System.out.println("----------------------------------------DOCUSIGN INFO----------------------------------------------------------------------");
			final EnvelopeStatus envelopeStatus=docuSignEnvelopeInformation.getEnvelopeStatus();
			if(envelopeStatus!=null){
				final ArrayOfRecipientStatus recipientStatuses=envelopeStatus.getRecipientStatuses();
				if(recipientStatuses!=null){
					final List<RecipientStatus> recipientStatusList=recipientStatuses.getRecipientStatus();
					if(recipientStatusList!=null){
						for(RecipientStatus recipientStatus:recipientStatusList){
							final ArrayOfTabStatus arrayOfTabStatus=recipientStatus.getTabStatuses();
							if(arrayOfTabStatus!=null){
								final List<TabStatus> tabStatus=arrayOfTabStatus.getTabStatus();
								if(tabStatus!=null){
									for(TabStatus tab:tabStatus){
										System.out.println("-----------------------------------> TABLABEL:"+tab.getTabLabel()+" | TABNAME:"+tab.getTabName());
									}
								}
							 }
						  }
					  }
				}
			}

			//final String connectXML=generateXML(docuSignEnvelopeInformation);
			//System.out.println(connectXML);
			return docuSignEnvelopeInformation.getEnvelopeStatus().getEnvelopeId();
		//} catch (JAXBException e) {
			//LOG.error("Error while marshling DocuSignEnvelopeInformation object to XML string",e);
		} catch (Exception e){
			LOG.error("Error with DocuSign Connect Request message",e);			
		}
		return "FAIL";
	}
	
	private String generateXML(DocuSignEnvelopeInformation docuSignEnvelopeInformation) throws JAXBException{
		final StringWriter requestWriter = new StringWriter();
		final Marshaller reqMarshaller = CONTEXT.createMarshaller();
		reqMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		final JAXBElement<DocuSignEnvelopeInformation> jaxbElement=new JAXBElement<DocuSignEnvelopeInformation>(DocuSignEnvelopeInformation_QNAME, DocuSignEnvelopeInformation.class, null, docuSignEnvelopeInformation);
		reqMarshaller.marshal(jaxbElement, requestWriter);
		LOG.debug(requestWriter.toString());
		return requestWriter.toString();
	}
	
}
