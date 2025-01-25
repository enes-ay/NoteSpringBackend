package com.enesay.notes.service

import com.enesay.notes.domain.Note
import com.enesay.notes.dto.CreateNoteDto
import com.enesay.notes.dto.NoteDto
import com.enesay.notes.dto.UpdateNoteDto
import com.enesay.notes.repository.NoteRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NoteService(
    private val noteRepository: NoteRepository
) {
    fun getAllNotes(): List<NoteDto> =
        noteRepository.findAll().map { it.toDto() }

    fun getNoteById(id: Long): NoteDto =
        noteRepository.findByIdOrNull(id)?.toDto()
            ?: throw NoSuchElementException("Note not found with id: $id")

    @Transactional
    fun createNote(createNoteDto: CreateNoteDto): NoteDto {
        val note = Note(
            title = createNoteDto.title,
            content = createNoteDto.content
        )
        return noteRepository.save(note).toDto()
    }

    @Transactional
    fun updateNote(id: Long, updateNoteDto: UpdateNoteDto): NoteDto {
        val note = noteRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Note not found with id: $id")

        updateNoteDto.title?.let { note.title = it }
        updateNoteDto.content?.let { note.content = it }
        note.updatedAt = LocalDateTime.now()

        return noteRepository.save(note).toDto()
    }

    @Transactional
    fun deleteNote(id: Long) {
        if (!noteRepository.existsById(id)) {
            throw NoSuchElementException("Note not found with id: $id")
        }
        noteRepository.deleteById(id)
    }

    private fun Note.toDto() = NoteDto(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
} 