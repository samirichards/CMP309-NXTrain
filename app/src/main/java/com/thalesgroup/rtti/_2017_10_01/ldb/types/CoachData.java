
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.commontypes.ToiletAvailabilityType;


/**
 * The data for an individual coach in a formation.
 * 
 * <p>Java class for CoachData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoachData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="coachClass" type="{http://thalesgroup.com/RTTI/2017-02-02/ldb/commontypes}CoachClassType" minOccurs="0"/&gt;
 *         &lt;element name="toilet" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/commontypes}ToiletAvailabilityType" minOccurs="0"/&gt;
 *         &lt;element name="loading" type="{http://thalesgroup.com/RTTI/2017-02-02/ldb/commontypes}LoadingValue" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="number" use="required" type="{http://thalesgroup.com/RTTI/2017-02-02/ldb/commontypes}CoachNumberType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoachData", propOrder = {
    "coachClass",
    "toilet",
    "loading"
})
public class CoachData {

    protected String coachClass;
    protected ToiletAvailabilityType toilet;
    @XmlSchemaType(name = "unsignedInt")
    protected Long loading;
    @XmlAttribute(name = "number", required = true)
    protected String number;

    /**
     * Gets the value of the coachClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoachClass() {
        return coachClass;
    }

    /**
     * Sets the value of the coachClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoachClass(String value) {
        this.coachClass = value;
    }

    /**
     * Gets the value of the toilet property.
     * 
     * @return
     *     possible object is
     *     {@link ToiletAvailabilityType }
     *     
     */
    public ToiletAvailabilityType getToilet() {
        return toilet;
    }

    /**
     * Sets the value of the toilet property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToiletAvailabilityType }
     *     
     */
    public void setToilet(ToiletAvailabilityType value) {
        this.toilet = value;
    }

    /**
     * Gets the value of the loading property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLoading() {
        return loading;
    }

    /**
     * Sets the value of the loading property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLoading(Long value) {
        this.loading = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

}
