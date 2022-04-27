
package com.thalesgroup.rtti._2017_10_01.ldb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.DeparturesBoardWithDetails;


/**
 * <p>Java class for DeparturesBoardWithDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeparturesBoardWithDetailsResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DeparturesBoard" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}DeparturesBoardWithDetails" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeparturesBoardWithDetailsResponseType", propOrder = {
    "departuresBoard"
})
public class DeparturesBoardWithDetailsResponseType {

    @XmlElement(name = "DeparturesBoard")
    protected DeparturesBoardWithDetails departuresBoard;

    /**
     * Gets the value of the departuresBoard property.
     * 
     * @return
     *     possible object is
     *     {@link DeparturesBoardWithDetails }
     *     
     */
    public DeparturesBoardWithDetails getDeparturesBoard() {
        return departuresBoard;
    }

    /**
     * Sets the value of the departuresBoard property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeparturesBoardWithDetails }
     *     
     */
    public void setDeparturesBoard(DeparturesBoardWithDetails value) {
        this.departuresBoard = value;
    }

}
