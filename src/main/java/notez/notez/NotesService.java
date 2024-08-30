package notez.notez;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static sun.jvm.hotspot.runtime.BasicObjectLock.size;

@Service
public class NotesService implements NoteService{

    @Autowired
    private NoteRepository notesRepository;

    @Autowired
    private NoteTransformer noteTransformer;
    private final Random random = new Random();

    @Override
    public List<NoteEntity> getAllNotes() {
        return notesRepository.findAll();
    }

    @Override
    public void save(NoteDto noteDto) {
        notesRepository.save(noteTransformer.toEntity(noteDto));
    }

    @Override
    public void edit(NoteDto noteDto) {
        // Load the existing note entity and throw an exception if not found
        NoteEntity noteEntity = notesRepository.findById(noteDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        // Update the fields of the existing note with the new data
        noteEntity.setTitle(noteDto.getTitle());
        noteEntity.setContent(noteDto.getContent());
        noteEntity.setType(noteDto.getType());

        // Save the updated entity back to the database
        notesRepository.save(noteEntity);
    }

    @Override
    public void delete(Long id) {
        notesRepository.deleteById(id);
    }

    @Override
    public NoteDto getRandomNote() {
        long count = notesRepository.count(); // Get total count of notes

        if (count == 0) {
            return null; // Handle the case where there are no notes
        }

        int randomIndex = (int) (Math.random() * count); // Generate a random index
        Pageable pageable = PageRequest.of(randomIndex, 1); // Fetch one note at the random index

        List<NoteEntity> randomNotes = notesRepository.findAll(pageable).getContent();

        if (!randomNotes.isEmpty()) {
            NoteEntity randomNote = randomNotes.get(0);
            return noteTransformer.toDto(randomNote);
        } else {
            return null; // Handle the case where the note wasn't found
        }
    }

    @Override
    public NoteDto getRandomTodoNote() {
        // Get total count of TODO notes directly from the repository
//        long count = notesRepository.countByType(NoteType.OTHER);
        List<NoteEntity> other = notesRepository.findAll().stream().filter(x -> x.getType().name().equals("OTHER")).toList();
        long count = other.size();

        if (count == 0) {
            return null; // Handle the case where there are no TODO notes
        }

        int randomIndex = (int) (Math.random() * count); // Generate a random index
        Pageable pageable = PageRequest.of(randomIndex, 1); // Fetch one TODO note at the random index

//        List<NoteEntity> randomNotes = notesRepository.findByType(NoteType.OTHER, pageable).getContent();
        List<NoteEntity> randomNotes = notesRepository.findAll(pageable).getContent();
        List<NoteEntity> other1 = randomNotes.stream().filter(x -> x.getType().name().equals("OTHER")).collect(Collectors.toList());

        if (!other1.isEmpty()) {
            NoteEntity randomNote = other1.get(0);
            return noteTransformer.toDto(randomNote);
        } else {
            return null; // Handle the case where the note wasn't found
        }
    }


}
