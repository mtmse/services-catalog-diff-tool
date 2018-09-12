
package se.mtm.servicesCatalogDiffTool.marc21;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for recordType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="leader" type="{http://www.loc.gov/MARC21/slim}leaderFieldType"/>
 *         &lt;element name="controlfield" type="{http://www.loc.gov/MARC21/slim}controlFieldType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="datafield" type="{http://www.loc.gov/MARC21/slim}dataFieldType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.loc.gov/MARC21/slim}recordTypeType" />
 *       &lt;attribute name="id" type="{http://www.loc.gov/MARC21/slim}idDataType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", namespace = "http://www.loc.gov/MARC21/slim", propOrder = {
    "leader",
    "controlfield",
    "datafield"
})
public class RecordType {

    @XmlElement(namespace = "http://www.loc.gov/MARC21/slim")
    protected LeaderFieldType leader;
    @XmlElement(namespace = "http://www.loc.gov/MARC21/slim")
    protected List<ControlFieldType> controlfield;
    @XmlElement(namespace = "http://www.loc.gov/MARC21/slim")
    protected List<DataFieldType> datafield;
    @XmlAttribute(name = "type")
    protected RecordTypeType type;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    /**
     * Gets the value of the leader property.
     * 
     * @return
     *     possible object is
     *     {@link LeaderFieldType }
     *     
     */
    public LeaderFieldType getLeader() {
        return leader;
    }

    /**
     * Sets the value of the leader property.
     * 
     * @param value
     *     allowed object is
     *     {@link LeaderFieldType }
     *     
     */
    public void setLeader(LeaderFieldType value) {
        this.leader = value;
    }

    /**
     * Gets the value of the controlfield property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlfield property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlfield().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ControlFieldType }
     * 
     * 
     */
    public List<ControlFieldType> getControlfield() {
        if (controlfield == null) {
            controlfield = new ArrayList<ControlFieldType>();
        }
        return this.controlfield;
    }

    /**
     * Gets the value of the datafield property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datafield property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatafield().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataFieldType }
     * 
     * 
     */
    public List<DataFieldType> getDatafield() {
        if (datafield == null) {
            datafield = new ArrayList<DataFieldType>();
        }
        return this.datafield;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link RecordTypeType }
     *     
     */
    public RecordTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordTypeType }
     *     
     */
    public void setType(RecordTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
