
package com.thalesgroup.rtti._2017_10_01.ldb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.StationBoardWithDetails;


/**
 * <p>Java class for StationBoardWithDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationBoardWithDetailsResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetStationBoardResult" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}StationBoardWithDetails" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationBoardWithDetailsResponseType", propOrder = {
    "getStationBoardResult"
})
public class StationBoardWithDetailsResponseType {

    @XmlElement(name = "GetStationBoardResult")
    protected StationBoardWithDetails getStationBoardResult;

    /**
     * Gets the value of the getStationBoardResult property.
     * 
     * @return
     *     possible object is
     *     {@link StationBoardWithDetails }
     *     
     */
    public StationBoardWithDetails getGetStationBoardResult() {
        return getStationBoardResult;
    }

    /**
     * Sets the value of the getStationBoardResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link StationBoardWithDetails }
     *     
     */
    public void setGetStationBoardResult(StationBoardWithDetails value) {
        this.getStationBoardResult = value;
    }

}
