import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
public class F {
    public static void main(String[] args) throws AxisFault {
        ServiceClient serviceClient = new ServiceClient();
        //创建服务地址WebService的URL,注意不是WSDL的URL
        String url = "http://172.16.15.199:8181/drsp-ebsm-server/api/services/version/32addac36701453e835e092730cf348a/soapWebService.DemoService.";
        EndpointReference targetEPR = new EndpointReference(url);
        Options options = serviceClient.getOptions();
        options.setTo(targetEPR);
        //确定调用方法（wsdl 命名空间地址 (wsdl文档中的targetNamespace) 和 方法名称 的组合）
        options.setAction("http://service.webservice.cly.com/sayHello");
        OMFactory fac = OMAbstractFactory.getOMFactory();
        /*
         * 指定命名空间，参数：
         * uri--即为wsdl文档的targetNamespace，命名空间
         * perfix--可不填
         */
        OMNamespace omNs = fac.createOMNamespace("http://service.webservice.cly.com", "ser");
        // 指定方法
        OMElement method = fac.createOMElement("sayHello", omNs);
        //为方法指定参数
        OMElement theRegionCode = fac.createOMElement("user", omNs);
        theRegionCode.setText("12323");
        method.addChild(theRegionCode);
        method.addChild(method);
        method.build();

        //远程调用web服务
        OMElement result = serviceClient.sendReceive(method);
        System.out.println(result);
    }
}
