package com.enesay.notes.controller

import com.enesay.notes.dto.CreateNoteDto
import com.enesay.notes.dto.NoteDto
import com.enesay.notes.dto.UpdateNoteDto
import com.enesay.notes.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService
) {
    @GetMapping
    fun getAllNotes(): ResponseEntity<List<NoteDto>> =
        ResponseEntity.ok(noteService.getAllNotes())

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Long): ResponseEntity<NoteDto> =
        ResponseEntity.ok(noteService.getNoteById(id))

    @PostMapping
    fun createNote(@RequestBody createNoteDto: CreateNoteDto): ResponseEntity<NoteDto> =
        ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(createNoteDto))

    @PutMapping("/{id}")
    fun updateNote(
        @PathVariable id: Long,
        @RequestBody updateNoteDto: UpdateNoteDto
    ): ResponseEntity<NoteDto> =
        ResponseEntity.ok(noteService.updateNote(id, updateNoteDto))

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Unit> =
        ResponseEntity.noContent().build<Unit>().also { noteService.deleteNote(id) }
} 