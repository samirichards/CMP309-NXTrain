
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Formation data at a calling point.
 * 
 * <p>Java class for FormationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormationData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="avgLoading" type="{http://thalesgroup.com/RTTI/2017-02-02/ldb/commontypes}LoadingValue" minOccurs="0"/&gt;
 *         &lt;element name="coaches" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ArrayOfCoaches" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormationData", propOrder = {
    "avgLoading",
    "coaches"
})
public class FormationData {

    @XmlSchemaType(name = "unsignedInt")
    protected Long avgLoading;
    protected ArrayOfCoaches coaches;

    /**
     * Gets the value of the avgLoading property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAvgLoading() {
        return avgLoading;
    }

    /**
     * Sets the value of the avgLoading property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAvgLoading(Long value) {
        this.avgLoading = value;
    }

    /**
     * Gets the value of the coaches property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCoaches }
     *     
     */
    public ArrayOfCoaches getCoaches() {
        return coaches;
    }

    /**
     * Sets the value of the coaches property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCoaches }
     *     
     */
    public void setCoaches(ArrayOfCoaches value) {
        this.coaches = value;
    }

}
