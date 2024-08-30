package notez.notez;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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
        Logger logger = LoggerFactory.getLogger(NotesService.class);

        // Fetch all notes of type TODO from the database
        List<NoteEntity> todoNotes = notesRepository.findAll()
                .stream()
                .filter(note -> note.getType() == NoteType.TODO)
                .collect(Collectors.toList());

        // Check if there are any TODO notes
        if (todoNotes.isEmpty()) {
            logger.info("No TODO notes found.");
            return null; // Handle the case where there are no TODO notes
        }

        // Generate a random index within the list of TODO notes
        int randomIndex = (int) (Math.random() * todoNotes.size());
        logger.info("Random Index: " + randomIndex);

        // Get the random TODO note
        NoteEntity randomTodoNote = todoNotes.get(randomIndex);
        logger.info("Selected Note ID: " + randomTodoNote.getId() + ", Title: " + randomTodoNote.getTitle());

        // Convert the NoteEntity to NoteDto and return it
        return noteTransformer.toDto(randomTodoNote);
    }


}
