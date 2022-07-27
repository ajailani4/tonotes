package com.tonotes.note_data.local

import androidx.room.*
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
            ORDER BY date DESC
        """
    )
    fun getNotes(searchQuery: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id=:id")
    fun getNoteDetail(id: Int): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun editNote(noteEntity: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE id=:id")
    suspend fun deleteNote(id: Int)
}