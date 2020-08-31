package com.protosstechnology.train.bootexamine.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id @GeneratedValue private long id;
    @NonNull private String documentNumber;
    @NonNull private String description;

    public Document(String documentNumber, String description) {
        this.documentNumber = documentNumber;
        this.description = description;
    }
}
