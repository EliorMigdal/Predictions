//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.29 at 03:51:00 PM IDT 
//


package engine.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRD-secondary-entity" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PRD-selection">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice>
 *                             &lt;element ref="{}PRD-condition" minOccurs="0"/>
 *                           &lt;/choice>
 *                           &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="entity" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}PRD-divide"/>
 *           &lt;element ref="{}PRD-multiply"/>
 *           &lt;sequence>
 *             &lt;element ref="{}PRD-condition"/>
 *             &lt;element ref="{}PRD-then"/>
 *             &lt;element ref="{}PRD-else" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="PRD-between">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;attribute name="target-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                     &lt;attribute name="source-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *             &lt;element name="PRD-env-depth">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;attribute name="of" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *             &lt;element ref="{}PRD-actions"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="mode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="scratch"/>
 *             &lt;enumeration value="derived"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="calculation"/>
 *             &lt;enumeration value="condition"/>
 *             &lt;enumeration value="decrease"/>
 *             &lt;enumeration value="increase"/>
 *             &lt;enumeration value="kill"/>
 *             &lt;enumeration value="set"/>
 *             &lt;enumeration value="proximity"/>
 *             &lt;enumeration value="replace"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="result-prop" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="by" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="kill" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="create" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prdSecondaryEntity",
    "prdDivide",
    "prdMultiply",
    "prdCondition",
    "prdThen",
    "prdElse",
    "prdBetween",
    "prdEnvDepth",
    "prdActions"
})
@XmlRootElement(name = "PRD-action")
public class PRDAction {

    @XmlElement(name = "PRD-secondary-entity")
    protected PRDAction.PRDSecondaryEntity prdSecondaryEntity;
    @XmlElement(name = "PRD-divide")
    protected PRDDivide prdDivide;
    @XmlElement(name = "PRD-multiply")
    protected PRDMultiply prdMultiply;
    @XmlElement(name = "PRD-condition")
    protected PRDCondition prdCondition;
    @XmlElement(name = "PRD-then")
    protected PRDThen prdThen;
    @XmlElement(name = "PRD-else")
    protected PRDElse prdElse;
    @XmlElement(name = "PRD-between")
    protected PRDAction.PRDBetween prdBetween;
    @XmlElement(name = "PRD-env-depth")
    protected PRDAction.PRDEnvDepth prdEnvDepth;
    @XmlElement(name = "PRD-actions")
    protected PRDActions prdActions;
    @XmlAttribute(name = "mode")
    protected String mode;
    @XmlAttribute(name = "value")
    protected String value;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "result-prop")
    protected String resultProp;
    @XmlAttribute(name = "property")
    protected String property;
    @XmlAttribute(name = "entity")
    protected String entity;
    @XmlAttribute(name = "by")
    protected String by;
    @XmlAttribute(name = "kill")
    protected String kill;
    @XmlAttribute(name = "create")
    protected String create;

    /**
     * Gets the value of the prdSecondaryEntity property.
     * 
     * @return
     *     possible object is
     *     {@link PRDAction.PRDSecondaryEntity }
     *     
     */
    public PRDAction.PRDSecondaryEntity getPRDSecondaryEntity() {
        return prdSecondaryEntity;
    }

    /**
     * Sets the value of the prdSecondaryEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDAction.PRDSecondaryEntity }
     *     
     */
    public void setPRDSecondaryEntity(PRDAction.PRDSecondaryEntity value) {
        this.prdSecondaryEntity = value;
    }

    /**
     * Gets the value of the prdDivide property.
     * 
     * @return
     *     possible object is
     *     {@link PRDDivide }
     *     
     */
    public PRDDivide getPRDDivide() {
        return prdDivide;
    }

    /**
     * Sets the value of the prdDivide property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDDivide }
     *     
     */
    public void setPRDDivide(PRDDivide value) {
        this.prdDivide = value;
    }

    /**
     * Gets the value of the prdMultiply property.
     * 
     * @return
     *     possible object is
     *     {@link PRDMultiply }
     *     
     */
    public PRDMultiply getPRDMultiply() {
        return prdMultiply;
    }

    /**
     * Sets the value of the prdMultiply property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDMultiply }
     *     
     */
    public void setPRDMultiply(PRDMultiply value) {
        this.prdMultiply = value;
    }

    /**
     * Gets the value of the prdCondition property.
     * 
     * @return
     *     possible object is
     *     {@link PRDCondition }
     *     
     */
    public PRDCondition getPRDCondition() {
        return prdCondition;
    }

    /**
     * Sets the value of the prdCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDCondition }
     *     
     */
    public void setPRDCondition(PRDCondition value) {
        this.prdCondition = value;
    }

    /**
     * Gets the value of the prdThen property.
     * 
     * @return
     *     possible object is
     *     {@link PRDThen }
     *     
     */
    public PRDThen getPRDThen() {
        return prdThen;
    }

    /**
     * Sets the value of the prdThen property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDThen }
     *     
     */
    public void setPRDThen(PRDThen value) {
        this.prdThen = value;
    }

    /**
     * Gets the value of the prdElse property.
     * 
     * @return
     *     possible object is
     *     {@link PRDElse }
     *     
     */
    public PRDElse getPRDElse() {
        return prdElse;
    }

    /**
     * Sets the value of the prdElse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDElse }
     *     
     */
    public void setPRDElse(PRDElse value) {
        this.prdElse = value;
    }

    /**
     * Gets the value of the prdBetween property.
     * 
     * @return
     *     possible object is
     *     {@link PRDAction.PRDBetween }
     *     
     */
    public PRDAction.PRDBetween getPRDBetween() {
        return prdBetween;
    }

    /**
     * Sets the value of the prdBetween property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDAction.PRDBetween }
     *     
     */
    public void setPRDBetween(PRDAction.PRDBetween value) {
        this.prdBetween = value;
    }

    /**
     * Gets the value of the prdEnvDepth property.
     * 
     * @return
     *     possible object is
     *     {@link PRDAction.PRDEnvDepth }
     *     
     */
    public PRDAction.PRDEnvDepth getPRDEnvDepth() {
        return prdEnvDepth;
    }

    /**
     * Sets the value of the prdEnvDepth property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDAction.PRDEnvDepth }
     *     
     */
    public void setPRDEnvDepth(PRDAction.PRDEnvDepth value) {
        this.prdEnvDepth = value;
    }

    /**
     * Gets the value of the prdActions property.
     * 
     * @return
     *     possible object is
     *     {@link PRDActions }
     *     
     */
    public PRDActions getPRDActions() {
        return prdActions;
    }

    /**
     * Sets the value of the prdActions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDActions }
     *     
     */
    public void setPRDActions(PRDActions value) {
        this.prdActions = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the resultProp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultProp() {
        return resultProp;
    }

    /**
     * Sets the value of the resultProp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultProp(String value) {
        this.resultProp = value;
    }

    /**
     * Gets the value of the property property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the value of the property property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperty(String value) {
        this.property = value;
    }

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity(String value) {
        this.entity = value;
    }

    /**
     * Gets the value of the by property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBy() {
        return by;
    }

    /**
     * Sets the value of the by property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBy(String value) {
        this.by = value;
    }

    /**
     * Gets the value of the kill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKill() {
        return kill;
    }

    /**
     * Sets the value of the kill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKill(String value) {
        this.kill = value;
    }

    /**
     * Gets the value of the create property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreate() {
        return create;
    }

    /**
     * Sets the value of the create property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreate(String value) {
        this.create = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="target-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="source-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PRDBetween {

        @XmlAttribute(name = "target-entity")
        protected String targetEntity;
        @XmlAttribute(name = "source-entity")
        protected String sourceEntity;

        /**
         * Gets the value of the targetEntity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTargetEntity() {
            return targetEntity;
        }

        /**
         * Sets the value of the targetEntity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTargetEntity(String value) {
            this.targetEntity = value;
        }

        /**
         * Gets the value of the sourceEntity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSourceEntity() {
            return sourceEntity;
        }

        /**
         * Sets the value of the sourceEntity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSourceEntity(String value) {
            this.sourceEntity = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="of" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PRDEnvDepth {

        @XmlAttribute(name = "of", required = true)
        protected String of;

        /**
         * Gets the value of the of property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOf() {
            return of;
        }

        /**
         * Sets the value of the of property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOf(String value) {
            this.of = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="PRD-selection">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice>
     *                   &lt;element ref="{}PRD-condition" minOccurs="0"/>
     *                 &lt;/choice>
     *                 &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="entity" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "prdSelection"
    })
    public static class PRDSecondaryEntity {

        @XmlElement(name = "PRD-selection", required = true)
        protected PRDAction.PRDSecondaryEntity.PRDSelection prdSelection;
        @XmlAttribute(name = "entity", required = true)
        protected String entity;

        /**
         * Gets the value of the prdSelection property.
         * 
         * @return
         *     possible object is
         *     {@link PRDAction.PRDSecondaryEntity.PRDSelection }
         *     
         */
        public PRDAction.PRDSecondaryEntity.PRDSelection getPRDSelection() {
            return prdSelection;
        }

        /**
         * Sets the value of the prdSelection property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRDAction.PRDSecondaryEntity.PRDSelection }
         *     
         */
        public void setPRDSelection(PRDAction.PRDSecondaryEntity.PRDSelection value) {
            this.prdSelection = value;
        }

        /**
         * Gets the value of the entity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntity() {
            return entity;
        }

        /**
         * Sets the value of the entity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntity(String value) {
            this.entity = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;choice>
         *         &lt;element ref="{}PRD-condition" minOccurs="0"/>
         *       &lt;/choice>
         *       &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "prdCondition"
        })
        public static class PRDSelection {

            @XmlElement(name = "PRD-condition")
            protected PRDCondition prdCondition;
            @XmlAttribute(name = "count", required = true)
            protected String count;

            /**
             * Gets the value of the prdCondition property.
             * 
             * @return
             *     possible object is
             *     {@link PRDCondition }
             *     
             */
            public PRDCondition getPRDCondition() {
                return prdCondition;
            }

            /**
             * Sets the value of the prdCondition property.
             * 
             * @param value
             *     allowed object is
             *     {@link PRDCondition }
             *     
             */
            public void setPRDCondition(PRDCondition value) {
                this.prdCondition = value;
            }

            /**
             * Gets the value of the count property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCount() {
                return count;
            }

            /**
             * Sets the value of the count property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCount(String value) {
                this.count = value;
            }

        }

    }

}
