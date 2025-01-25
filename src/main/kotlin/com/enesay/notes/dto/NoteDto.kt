package com.enesay.notes.dto

import java.time.LocalDateTime

data class NoteDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class CreateNoteDto(
    val title: String,
    val content: String
)

data class UpdateNoteDto(
    val title: String?,
    val content: String?
) 