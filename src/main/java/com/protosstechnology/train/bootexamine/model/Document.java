package com.protosstechnology.train.bootexamine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
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

    public Document(DocumentDto documentDto) {
        documentNumber = documentDto.getDocumentNumber();
        description = documentDto.getDescription();
    }
}
