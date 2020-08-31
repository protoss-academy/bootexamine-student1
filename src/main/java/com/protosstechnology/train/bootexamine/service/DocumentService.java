package com.protosstechnology.train.bootexamine.service;

import com.protosstechnology.train.bootexamine.model.Document;

public interface DocumentService {
    void create(Document document);
    Document read(Long id);
    Document update(Document document);
    void delete(Long id);
}
