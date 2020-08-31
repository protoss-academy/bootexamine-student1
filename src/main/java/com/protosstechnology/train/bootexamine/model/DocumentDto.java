package com.protosstechnology.train.bootexamine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    @NonNull private String documentNumber;
    @NonNull private String description;
}
