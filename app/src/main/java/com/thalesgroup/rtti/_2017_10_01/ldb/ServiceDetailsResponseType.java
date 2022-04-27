
package com.thalesgroup.rtti._2017_10_01.ldb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ServiceDetails;


/**
 * <p>Java class for ServiceDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDetailsResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetServiceDetailsResult" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ServiceDetails" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDetailsResponseType", propOrder = {
    "getServiceDetailsResult"
})
public class ServiceDetailsResponseType {

    @XmlElement(name = "GetServiceDetailsResult")
    protected ServiceDetails getServiceDetailsResult;

    /**
     * Gets the value of the getServiceDetailsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDetails }
     *     
     */
    public ServiceDetails getGetServiceDetailsResult() {
        return getServiceDetailsResult;
    }

    /**
     * Sets the value of the getServiceDetailsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDetails }
     *     
     */
    public void setGetServiceDetailsResult(ServiceDetails value) {
        this.getServiceDetailsResult = value;
    }

}
