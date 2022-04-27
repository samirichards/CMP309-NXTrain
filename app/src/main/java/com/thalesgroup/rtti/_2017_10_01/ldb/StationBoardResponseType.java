
package com.thalesgroup.rtti._2017_10_01.ldb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.StationBoard;


/**
 * <p>Java class for StationBoardResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationBoardResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetStationBoardResult" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}StationBoard" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationBoardResponseType", propOrder = {
    "getStationBoardResult"
})
public class StationBoardResponseType {

    @XmlElement(name = "GetStationBoardResult")
    protected StationBoard getStationBoardResult;

    /**
     * Gets the value of the getStationBoardResult property.
     * 
     * @return
     *     possible object is
     *     {@link StationBoard }
     *     
     */
    public StationBoard getGetStationBoardResult() {
        return getStationBoardResult;
    }

    /**
     * Sets the value of the getStationBoardResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link StationBoard }
     *     
     */
    public void setGetStationBoardResult(StationBoard value) {
        this.getStationBoardResult = value;
    }

}
