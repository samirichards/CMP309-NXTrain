
package com.thalesgroup.rtti._2017_10_01.ldb.commontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * The availability of a toilet in coach formation data. If no availability is supplied, it should be assumed to have the value "Unknown".
 * 
 * <p>Java class for ToiletAvailabilityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ToiletAvailabilityType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://thalesgroup.com/RTTI/2017-10-01/ldb/commontypes&gt;ToiletType"&gt;
 *       &lt;attribute name="status" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/commontypes}ToiletStatus" default="InService" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToiletAvailabilityType", propOrder = {
    "value"
})
public class ToiletAvailabilityType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "status")
    protected ToiletStatus status;

    /**
     * An indication of the availability of a toilet in a coach in a train formation. E.g. "Unknown", "None" , "Standard" or "Accessible". Note that other values may be supplied in the future without a schema change.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ToiletStatus }
     *     
     */
    public ToiletStatus getStatus() {
        if (status == null) {
            return ToiletStatus.IN_SERVICE;
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToiletStatus }
     *     
     */
    public void setStatus(ToiletStatus value) {
        this.status = value;
    }

}
