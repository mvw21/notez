package notez.notez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    NotesService notesService;
    @GetMapping(path = "/notes")
    public List<NoteEntity> getAllNotes() {
        notesService.getAllNotes();
        return notesService.getAllNotes();
    }

    @CrossOrigin(origins = "https://helpful-growth-production.up.railway.app")
    @PostMapping("/save")
    public ResponseEntity<String> saveNote(@RequestBody NoteDto noteDTO) {
        try {
            notesService.save(noteDTO);
            // Return 201 Created with a success message
            return ResponseEntity.status(HttpStatus.CREATED).body("Note saved successfully");
        } catch (Exception e) {
            // Log the exception and return 500 Internal Server Error with an error message
            e.printStackTrace(); // This will print the exception stack trace in the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save note");
        }
    }

    @CrossOrigin(origins = "https://helpful-growth-production.up.railway.app")
    @PostMapping("/edit")
    public ResponseEntity<String> editNote(@RequestBody NoteDto noteDTO) {
        try {
            notesService.edit(noteDTO);
            // Return 200 OK with a success message
            return ResponseEntity.status(HttpStatus.OK).body("Note edit successful");
        } catch (Exception e) {
            // Log the exception and return 500 Internal Server Error with an error message
            e.printStackTrace(); // This will print the exception stack trace in the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit note");
        }
    }

    @CrossOrigin(origins = "https://helpful-growth-production.up.railway.app")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteNote(@RequestBody NoteDto noteDTO) {
        try {
            notesService.delete(noteDTO.getId());
            // Return 200 OK with a success message
            return ResponseEntity.status(HttpStatus.OK).body("Note deleted successfully");
        } catch (Exception e) {
            // Log the exception and return 500 Internal Server Error with an error message
            e.printStackTrace(); // This will print the exception stack trace in the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete note");
        }
    }

    @GetMapping("/random")
    public ResponseEntity<NoteDto> getRandomNote() {
        NoteDto randomNote = notesService.getRandomNote();
        if (randomNote != null) {
            return ResponseEntity.ok(randomNote);
        } else {
            return ResponseEntity.noContent().build(); // Return 204 if no note found
        }
    }

    @GetMapping("/randomTodo")
    public ResponseEntity<NoteDto> getRandomTodoNote() {
        NoteDto randomTodoNote = notesService.getRandomTodoNote();
        if (randomTodoNote != null) {
            return ResponseEntity.ok(randomTodoNote);
        } else {
            return ResponseEntity.noContent().build(); // Return 204 if no note found
        }
    }


}
