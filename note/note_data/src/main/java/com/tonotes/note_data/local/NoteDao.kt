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
            FROM noteentity 
            WHERE title LIKE '%' || :searchQuery || '%' 
            OR description LIKE '%' || :searchQuery || '%'
        """
    )
    fun getNotes(searchQuery: String): Flow<List<NoteEntity>>
}