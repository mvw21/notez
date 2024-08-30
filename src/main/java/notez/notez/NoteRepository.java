package notez.notez;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    // Method to count notes by type
    long countByType(NoteType type);
    // Method to find notes by type with pagination
    Page<NoteEntity> findByType(NoteType type, Pageable pageable);
}
