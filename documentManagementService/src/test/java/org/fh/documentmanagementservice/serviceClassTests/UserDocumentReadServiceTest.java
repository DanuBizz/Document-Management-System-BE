package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.documentVersion.DocumentVersionRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.fh.documentmanagementservice.userDocumentRead.UserDocumentReadRepository;
import org.fh.documentmanagementservice.userDocumentRead.UserDocumentReadRequestDTO;
import org.fh.documentmanagementservice.userDocumentRead.UserDocumentReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDocumentReadServiceTest {

    @InjectMocks
    private UserDocumentReadService userDocumentReadService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentVersionRepository documentVersionRepository;

    @Mock
    private UserDocumentReadRepository userDocumentReadRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void markDocumentAsRead_invalidUserId_throwsException() {
        UserDocumentReadRequestDTO request = new UserDocumentReadRequestDTO();
        request.setUserId(1L);
        request.setDocumentVersionId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userDocumentReadService.markDocumentAsRead(request));
    }

    @Test
    public void markDocumentAsRead_invalidDocumentVersionId_throwsException() {
        UserDocumentReadRequestDTO request = new UserDocumentReadRequestDTO();
        request.setUserId(1L);
        request.setDocumentVersionId(1L);

        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(documentVersionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userDocumentReadService.markDocumentAsRead(request));
    }
}
