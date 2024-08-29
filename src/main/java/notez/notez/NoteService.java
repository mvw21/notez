package notez.notez;

import java.util.List;

public interface NoteService {

    List<NoteEntity> getAllNotes();
    void save(NoteDto noteDto);
    void edit(NoteDto noteDto);

    NoteDto getRandomNote();
}
