package com.digirella.dto.request;

import lombok.*;

import javax.xml.bind.annotation.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Valute {

    @XmlAttribute(name = "Code")
    private String code;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    private Double value;
}