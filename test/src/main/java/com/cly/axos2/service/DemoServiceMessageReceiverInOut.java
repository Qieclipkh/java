/**
 * DemoServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.cly.axos2.service;


/**
 *  DemoServiceMessageReceiverInOut message receiver
 */
public class DemoServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            DemoServiceSkeletonInterface skel = (DemoServiceSkeletonInterface) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(
                            op.getName().getLocalPart())) != null)) {
                if ("sendServerImage".equals(methodName)) {
                    com.cly.webservice.service.SendServerImageResponseE sendServerImageResponse31 =
                        null;
                    com.cly.webservice.service.SendServerImageE wrappedParam = (com.cly.webservice.service.SendServerImageE) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.cly.webservice.service.SendServerImageE.class);

                    sendServerImageResponse31 = skel.sendServerImage(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            sendServerImageResponse31, false,
                            new javax.xml.namespace.QName(
                                "http://service.webservice.cly.com",
                                "sendServerImageResponse"));
                } else
                 if ("noSayHello".equals(methodName)) {
                    com.cly.webservice.service.NoSayHelloResponseE noSayHelloResponse33 =
                        null;
                    com.cly.webservice.service.NoSayHelloE wrappedParam = (com.cly.webservice.service.NoSayHelloE) fromOM(msgContext.getEnvelope()
                                                                                                                                    .getBody()
                                                                                                                                    .getFirstElement(),
                            com.cly.webservice.service.NoSayHelloE.class);

                    noSayHelloResponse33 = skel.noSayHello(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            noSayHelloResponse33, false,
                            new javax.xml.namespace.QName(
                                "http://service.webservice.cly.com",
                                "noSayHelloResponse"));
                } else
                 if ("saySorry".equals(methodName)) {
                    com.cly.webservice.service.SaySorryResponseE saySorryResponse35 =
                        null;
                    com.cly.webservice.service.SaySorryE wrappedParam = (com.cly.webservice.service.SaySorryE) fromOM(msgContext.getEnvelope()
                                                                                                                                .getBody()
                                                                                                                                .getFirstElement(),
                            com.cly.webservice.service.SaySorryE.class);

                    saySorryResponse35 = skel.saySorry(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            saySorryResponse35, false,
                            new javax.xml.namespace.QName(
                                "http://service.webservice.cly.com",
                                "saySorryResponse"));
                } else
                 if ("receiveClientImage".equals(methodName)) {
                    com.cly.webservice.service.ReceiveClientImageResponseE receiveClientImageResponse37 =
                        null;
                    com.cly.webservice.service.ReceiveClientImageE wrappedParam = (com.cly.webservice.service.ReceiveClientImageE) fromOM(msgContext.getEnvelope()
                                                                                                                                                    .getBody()
                                                                                                                                                    .getFirstElement(),
                            com.cly.webservice.service.ReceiveClientImageE.class);

                    receiveClientImageResponse37 = skel.receiveClientImage(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            receiveClientImageResponse37, false,
                            new javax.xml.namespace.QName(
                                "http://service.webservice.cly.com",
                                "receiveClientImageResponse"));
                } else
                 if ("sayHello".equals(methodName)) {
                    com.cly.webservice.service.SayHelloResponseE sayHelloResponse39 =
                        null;
                    com.cly.webservice.service.SayHelloE wrappedParam = (com.cly.webservice.service.SayHelloE) fromOM(msgContext.getEnvelope()
                                                                                                                                .getBody()
                                                                                                                                .getFirstElement(),
                            com.cly.webservice.service.SayHelloE.class);

                    sayHelloResponse39 = skel.sayHello(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            sayHelloResponse39, false,
                            new javax.xml.namespace.QName(
                                "http://service.webservice.cly.com",
                                "sayHelloResponse"));
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SendServerImageE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SendServerImageE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SendServerImageResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SendServerImageResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.NoSayHelloE param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.NoSayHelloE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.NoSayHelloResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.NoSayHelloResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SaySorryE param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SaySorryE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SaySorryResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SaySorryResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.ReceiveClientImageE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.ReceiveClientImageE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.ReceiveClientImageResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.ReceiveClientImageResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SayHelloE param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SayHelloE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.cly.webservice.service.SayHelloResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.cly.webservice.service.SayHelloResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.cly.webservice.service.SendServerImageResponseE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.cly.webservice.service.SendServerImageResponseE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.cly.webservice.service.SendServerImageResponseE wrapsendServerImage() {
        com.cly.webservice.service.SendServerImageResponseE wrappedElement = new com.cly.webservice.service.SendServerImageResponseE();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.cly.webservice.service.NoSayHelloResponseE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.cly.webservice.service.NoSayHelloResponseE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.cly.webservice.service.NoSayHelloResponseE wrapnoSayHello() {
        com.cly.webservice.service.NoSayHelloResponseE wrappedElement = new com.cly.webservice.service.NoSayHelloResponseE();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.cly.webservice.service.SaySorryResponseE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.cly.webservice.service.SaySorryResponseE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.cly.webservice.service.SaySorryResponseE wrapsaySorry() {
        com.cly.webservice.service.SaySorryResponseE wrappedElement = new com.cly.webservice.service.SaySorryResponseE();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.cly.webservice.service.ReceiveClientImageResponseE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.cly.webservice.service.ReceiveClientImageResponseE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.cly.webservice.service.ReceiveClientImageResponseE wrapreceiveClientImage() {
        com.cly.webservice.service.ReceiveClientImageResponseE wrappedElement = new com.cly.webservice.service.ReceiveClientImageResponseE();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.cly.webservice.service.SayHelloResponseE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.cly.webservice.service.SayHelloResponseE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private com.cly.webservice.service.SayHelloResponseE wrapsayHello() {
        com.cly.webservice.service.SayHelloResponseE wrappedElement = new com.cly.webservice.service.SayHelloResponseE();

        return wrappedElement;
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (com.cly.webservice.service.NoSayHelloE.class.equals(type)) {
                return com.cly.webservice.service.NoSayHelloE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.NoSayHelloResponseE.class.equals(
                        type)) {
                return com.cly.webservice.service.NoSayHelloResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.ReceiveClientImageE.class.equals(
                        type)) {
                return com.cly.webservice.service.ReceiveClientImageE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.ReceiveClientImageResponseE.class.equals(
                        type)) {
                return com.cly.webservice.service.ReceiveClientImageResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SayHelloE.class.equals(type)) {
                return com.cly.webservice.service.SayHelloE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SayHelloResponseE.class.equals(type)) {
                return com.cly.webservice.service.SayHelloResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SaySorryE.class.equals(type)) {
                return com.cly.webservice.service.SaySorryE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SaySorryResponseE.class.equals(type)) {
                return com.cly.webservice.service.SaySorryResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SendServerImageE.class.equals(type)) {
                return com.cly.webservice.service.SendServerImageE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.cly.webservice.service.SendServerImageResponseE.class.equals(
                        type)) {
                return com.cly.webservice.service.SendServerImageResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
