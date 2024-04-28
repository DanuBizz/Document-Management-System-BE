package org.fh.documentmanagementservice.docVersion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docVersions")
public class DocVersionController {

    @Autowired
    private DocVersionService docVersionService;

    @GetMapping
    public ResponseEntity<List<DocVersion>> getAllDocVersions() {
        return ResponseEntity.ok(docVersionService.getAllDocVersions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocVersion> getDocVersionById(@PathVariable Long id) {
        return docVersionService.getDocVersionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<DocVersion> createDocVersion(@RequestBody DocVersionRequestDTO docVersionRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docVersionService.createDocVersion(docVersionRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocVersion> updateDocVersion(@PathVariable Long id, @RequestBody DocVersionRequestDTO docVersionRequestDTO) {
        return ResponseEntity.ok(docVersionService.updateDocVersion(id, docVersionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocVersion(@PathVariable Long id) {
        docVersionService.deleteDocVersion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}