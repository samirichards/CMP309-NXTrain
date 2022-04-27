
package com.thalesgroup.rtti._2017_10_01.ldb.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A list of coaches in a train formation.
 * 
 * <p>Java class for ArrayOfCoaches complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCoaches"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="coach" type="{http://thalesgroup.com/RTTI/2017-10-01/ldb/types}CoachData" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCoaches", propOrder = {
    "coach"
})
public class ArrayOfCoaches {

    @XmlElement(required = true)
    protected List<CoachData> coach;

    /**
     * Gets the value of the coach property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coach property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoach().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoachData }
     * 
     * 
     */
    public List<CoachData> getCoach() {
        if (coach == null) {
            coach = new ArrayList<CoachData>();
        }
        return this.coach;
    }

}
