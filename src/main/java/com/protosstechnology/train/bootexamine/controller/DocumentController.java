package com.protosstechnology.train.bootexamine.controller;

import com.protosstechnology.train.bootexamine.model.Document;
import com.protosstechnology.train.bootexamine.model.DocumentDto;
import com.protosstechnology.train.bootexamine.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @Operation(summary = "Get Document by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the document", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Document.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id document", content = @Content),
        @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable String id) {
        ResponseEntity responseEntity;
        try {
            Document document = documentService.read(Long.parseLong(id));
            if (document != null) {
                responseEntity = ResponseEntity.ok(document);
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    @Operation(summary = "Create Document")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Created the Document")})
    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDto documentDto) {
        Document document = new Document(documentDto);
        documentService.create(document);
        return ResponseEntity.ok(document);
    }

    @Operation(summary = "Update the Document")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated the Document")})
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable String id, @RequestBody DocumentDto documentDto) {
        Document document = new Document(documentDto);
        document.setId(Long.parseLong(id));
        documentService.update(document);
        return ResponseEntity.ok(document);
    }

    @Operation(summary = "Delete the Document")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Deleted the Document")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        StringBuilder stringBuilder = new StringBuilder(String.format("Delete Document %s ", id));
        try {
            documentService.delete(Long.parseLong(id));
            stringBuilder.append("successfully");
        } catch (Exception ex) {
            stringBuilder.append("unsuccessfully");
        }
        return ResponseEntity.ok(stringBuilder.toString());
    }
}
