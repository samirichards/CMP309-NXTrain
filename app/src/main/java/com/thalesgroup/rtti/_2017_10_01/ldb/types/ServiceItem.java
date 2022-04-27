
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * An individual service's summary details for display on a basic departure board.
 * 
 * <p>Java class for ServiceItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://thalesgroup.com/RTTI/2016-02-16/ldb/types}ServiceItem"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="formation" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}FormationData" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceItem", propOrder = {
    "formation"
})
@XmlSeeAlso({
    ServiceItemWithCallingPoints.class
})
public class ServiceItem
    extends com.thalesgroup.rtti._2016_02_16.ldb.types.ServiceItem
{

    protected FormationData formation;

    /**
     * Gets the value of the formation property.
     * 
     * @return
     *     possible object is
     *     {@link FormationData }
     *     
     */
    public FormationData getFormation() {
        return formation;
    }

    /**
     * Sets the value of the formation property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormationData }
     *     
     */
    public void setFormation(FormationData value) {
        this.formation = value;
    }

}
