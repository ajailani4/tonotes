package com.tonotes.note_data.local

import androidx.room.Dao
import androidx.room.Query
import com.tonotes.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query(
        """
            SELECT *
            FROM NoteEntity
            WHERE title LIKE '%' || :searchQuery || '%'
            OR description LIKE '%' || :searchQuery || '%'
            ORDER BY id DESC
        """
    )
    fun getNotes(searchQuery: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id=:id")
    fun getNoteDetail(id: Int): Flow<NoteEntity>
}