package com.hcsc.enrollment.contract.docusign;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2016-11-18T19:08:56.440-06:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "EnrollmentConnectService", 
                  wsdlLocation = "file:/C:/Users/i30062c/git/BA-ENROLL/com.hcsc.enrollment/com.hcsc.enrollment.facade/src/main/webapp/WEB-INF/wsdl/docusign-connect-enrollment.wsdl",
                  targetNamespace = "http://enrollment.hcsc.com/contract/docusign") 
public class EnrollmentConnectService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://enrollment.hcsc.com/contract/docusign", "EnrollmentConnectService");
    public final static QName EnrollmentConnectGatewayPort = new QName("http://enrollment.hcsc.com/contract/docusign", "EnrollmentConnectGatewayPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/i30062c/git/BA-ENROLL/com.hcsc.enrollment/com.hcsc.enrollment.facade/src/main/webapp/WEB-INF/wsdl/docusign-connect-enrollment.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EnrollmentConnectService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/i30062c/git/BA-ENROLL/com.hcsc.enrollment/com.hcsc.enrollment.facade/src/main/webapp/WEB-INF/wsdl/docusign-connect-enrollment.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EnrollmentConnectService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EnrollmentConnectService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EnrollmentConnectService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns EnrollmentConnectGatewaySoap
     */
    @WebEndpoint(name = "EnrollmentConnectGatewayPort")
    public EnrollmentConnectGatewaySoap getEnrollmentConnectGatewayPort() {
        return super.getPort(EnrollmentConnectGatewayPort, EnrollmentConnectGatewaySoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EnrollmentConnectGatewaySoap
     */
    @WebEndpoint(name = "EnrollmentConnectGatewayPort")
    public EnrollmentConnectGatewaySoap getEnrollmentConnectGatewayPort(WebServiceFeature... features) {
        return super.getPort(EnrollmentConnectGatewayPort, EnrollmentConnectGatewaySoap.class, features);
    }

}
