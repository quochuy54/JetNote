package com.midterm.jetnote.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midterm.jetnote.data.NoteRepository
import com.midterm.jetnote.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel(){
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    public val noteList = _noteList.asStateFlow()

    //Display List
    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllNote().distinctUntilChanged()
                .collect {
                    listOfNote ->
                    if(listOfNote.isNullOrEmpty()){
                        Log.d("Debug", "Null or empty List Notes")
                    }
                    else{
                        _noteList.value = listOfNote
                    }
                }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }
    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
}