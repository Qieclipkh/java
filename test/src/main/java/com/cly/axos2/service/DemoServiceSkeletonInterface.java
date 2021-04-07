/**
 * DemoServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.cly.axos2.service;

/**
 *  DemoServiceSkeletonInterface java skeleton interface for the axisService
 */
public interface DemoServiceSkeletonInterface {
    /**
     * Auto generated method signature
     *
     * @param sendServerImage
     */
    public com.cly.webservice.service.SendServerImageResponseE sendServerImage(
        com.cly.webservice.service.SendServerImageE sendServerImage);

    /**
     * Auto generated method signature
     *
     * @param noSayHello
     */
    public com.cly.webservice.service.NoSayHelloResponseE noSayHello(
        com.cly.webservice.service.NoSayHelloE noSayHello);

    /**
     * Auto generated method signature
     *
     * @param saySorry
     */
    public com.cly.webservice.service.SaySorryResponseE saySorry(
        com.cly.webservice.service.SaySorryE saySorry);

    /**
     * Auto generated method signature
     *
     * @param receiveClientImage
     */
    public com.cly.webservice.service.ReceiveClientImageResponseE receiveClientImage(
        com.cly.webservice.service.ReceiveClientImageE receiveClientImage);

    /**
     * Auto generated method signature
     *
     * @param sayHello
     */
    public com.cly.webservice.service.SayHelloResponseE sayHello(
        com.cly.webservice.service.SayHelloE sayHello);
}
