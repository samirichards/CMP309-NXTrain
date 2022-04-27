
package com.thalesgroup.rtti._2017_10_01.ldb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.DeparturesBoard;


/**
 * <p>Java class for DeparturesBoardResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeparturesBoardResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DeparturesBoard" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}DeparturesBoard" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeparturesBoardResponseType", propOrder = {
    "departuresBoard"
})
public class DeparturesBoardResponseType {

    @XmlElement(name = "DeparturesBoard")
    protected DeparturesBoard departuresBoard;

    /**
     * Gets the value of the departuresBoard property.
     * 
     * @return
     *     possible object is
     *     {@link DeparturesBoard }
     *     
     */
    public DeparturesBoard getDeparturesBoard() {
        return departuresBoard;
    }

    /**
     * Sets the value of the departuresBoard property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeparturesBoard }
     *     
     */
    public void setDeparturesBoard(DeparturesBoard value) {
        this.departuresBoard = value;
    }

}
