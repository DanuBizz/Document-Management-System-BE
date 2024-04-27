package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.fh.documentmanagementservice.userDocument.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserDocumentServiceTest {

    @InjectMocks
    private UserDocumentService userDocumentService;

    @Mock
    private UserDocumentRepository userDocumentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void markDocumentAsReadSuccessfully() {
        UserDocumentRequestDTO requestDTO = new UserDocumentRequestDTO(1L, 1L, true);
        User user = new User();
        user.setId(1L); // Set ID for User
        Document document = new Document();
        document.setId(1L); // Set ID for Document
        UserDocument userDocument = new UserDocument();
        userDocument.setUser(user);
        userDocument.setDocument(document);
        userDocument.setHasRead(true);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));
        when(userDocumentRepository.findByUserIdAndDocumentId(anyLong(), anyLong())).thenReturn(Optional.of(userDocument));
        when(userDocumentRepository.save(any(UserDocument.class))).thenReturn(userDocument);

        UserDocumentResponseDTO responseDTO = userDocumentService.markDocumentAsRead(requestDTO);

        assertEquals(userDocument.getId(), responseDTO.getId());
        assertEquals(userDocument.getUser().getId(), responseDTO.getUserId());
        assertEquals(userDocument.getDocument().getId(), responseDTO.getDocumentId());
        assertEquals(userDocument.getHasRead(), responseDTO.getHasRead());

        verify(userRepository, times(1)).findById(anyLong());
        verify(documentRepository, times(1)).findById(anyLong());
        verify(userDocumentRepository, times(1)).findByUserIdAndDocumentId(anyLong(), anyLong());
        verify(userDocumentRepository, times(1)).save(any(UserDocument.class));
    }
}
