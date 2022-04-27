
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2015_11_27.ldb.types.BaseStationBoard;


/**
 * A structure containing details of a basic departure board for a specific location.
 * 
 * <p>Java class for StationBoard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationBoard"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://thalesgroup.com/RTTI/2015-11-27/ldb/types}BaseStationBoard"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="trainServices" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ArrayOfServiceItems" minOccurs="0"/&gt;
 *         &lt;element name="busServices" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ArrayOfServiceItems" minOccurs="0"/&gt;
 *         &lt;element name="ferryServices" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}ArrayOfServiceItems" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationBoard", propOrder = {
    "trainServices",
    "busServices",
    "ferryServices"
})
public class StationBoard
    extends BaseStationBoard
{

    protected ArrayOfServiceItems trainServices;
    protected ArrayOfServiceItems busServices;
    protected ArrayOfServiceItems ferryServices;

    /**
     * Gets the value of the trainServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public ArrayOfServiceItems getTrainServices() {
        return trainServices;
    }

    /**
     * Sets the value of the trainServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public void setTrainServices(ArrayOfServiceItems value) {
        this.trainServices = value;
    }

    /**
     * Gets the value of the busServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public ArrayOfServiceItems getBusServices() {
        return busServices;
    }

    /**
     * Sets the value of the busServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public void setBusServices(ArrayOfServiceItems value) {
        this.busServices = value;
    }

    /**
     * Gets the value of the ferryServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public ArrayOfServiceItems getFerryServices() {
        return ferryServices;
    }

    /**
     * Sets the value of the ferryServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceItems }
     *     
     */
    public void setFerryServices(ArrayOfServiceItems value) {
        this.ferryServices = value;
    }

}
