
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2015_11_27.ldb.types.BaseStationBoard;


/**
 * A structure containing details of a "WithDetails" next/fastest departures board for a specific location.
 * 
 * <p>Java class for DeparturesBoardWithDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeparturesBoardWithDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://thalesgroup.com/RTTI/2015-11-27/ldb/types}BaseStationBoard"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="departures" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ArrayOfDepartureItemsWithCallingPoints"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeparturesBoardWithDetails", propOrder = {
    "departures"
})
public class DeparturesBoardWithDetails
    extends BaseStationBoard
{

    @XmlElement(required = true)
    protected ArrayOfDepartureItemsWithCallingPoints departures;

    /**
     * Gets the value of the departures property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDepartureItemsWithCallingPoints }
     *     
     */
    public ArrayOfDepartureItemsWithCallingPoints getDepartures() {
        return departures;
    }

    /**
     * Sets the value of the departures property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDepartureItemsWithCallingPoints }
     *     
     */
    public void setDepartures(ArrayOfDepartureItemsWithCallingPoints value) {
        this.departures = value;
    }

}
