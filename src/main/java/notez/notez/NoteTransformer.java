package notez.notez;

import org.springframework.stereotype.Component;

@Component
public class NoteTransformer {
    public NoteEntity toEntity(NoteDto noteDto){
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteDto.getTitle());
        noteEntity.setContent(noteDto.getContent());
        noteEntity.setType(noteDto.getType());
        return noteEntity;
    }

    public NoteDto toDto(NoteEntity noteEntity){
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(noteEntity.getTitle());
        noteDto.setContent(noteEntity.getContent());
        noteDto.setType(noteEntity.getType());
        return noteDto;
    }
}
