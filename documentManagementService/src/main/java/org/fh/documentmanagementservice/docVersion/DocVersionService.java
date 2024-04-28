package org.fh.documentmanagementservice.docVersion;

import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DocVersionService {

    @Autowired
    private DocVersionRepository docVersionRepository;

    public List<DocVersion> getAllDocVersions() {
        return docVersionRepository.findAll();
    }

    public Optional<DocVersion> getDocVersionById(Long id) {
        return docVersionRepository.findById(id);
    }

    public DocVersion createDocVersion(DocVersionRequestDTO docVersionRequestDTO) {
        DocVersion docVersion = DocVersion.builder()
                .filePath(docVersionRequestDTO.getFilePath())
                .isVisible(docVersionRequestDTO.isVisible())
                .isRead(docVersionRequestDTO.isRead())
                .isLatest(docVersionRequestDTO.isLatest())
                .categories(docVersionRequestDTO.getCategories())
                .document(docVersionRequestDTO.getDocument())
                .timestamp(docVersionRequestDTO.getTimestamp())
                .build();

        return docVersionRepository.save(docVersion);
    }

    public DocVersion updateDocVersion(Long id, DocVersionRequestDTO docVersionRequestDTO) {
        DocVersion docVersion = docVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DocVersion not found with id: " + id));

        docVersion.setFilePath(docVersionRequestDTO.getFilePath());
        docVersion.setVisible(docVersionRequestDTO.isVisible());
        docVersion.setRead(docVersionRequestDTO.isRead());
        docVersion.setLatest(docVersionRequestDTO.isLatest());
        docVersion.setCategories(docVersionRequestDTO.getCategories());
        docVersion.setDocument(docVersionRequestDTO.getDocument());
        docVersion.setTimestamp(docVersionRequestDTO.getTimestamp());

        return docVersionRepository.save(docVersion);
    }

    public void deleteDocVersion(Long id) {
        DocVersion docVersion = docVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DocVersion not found with id: " + id));

        docVersionRepository.delete(docVersion);
    }
}