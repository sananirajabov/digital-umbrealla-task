package com.digirella.dto.request;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ValType")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class ValType {

    @XmlAttribute(name = "Type")
    private String type;

    @XmlElement(name = "Valute")
    private List<Valute> valutes;
}