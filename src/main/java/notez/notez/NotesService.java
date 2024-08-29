package notez.notez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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


}
