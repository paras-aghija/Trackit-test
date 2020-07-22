package com.example.trackit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepositry repositry;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repositry = new NoteRepositry(application);
        allNotes = repositry.getAllNotes();
    }

   public void insert(Note note){
      repositry.insert(note);
    }
   public void update(Note note){
        repositry.update(note);
   }
   public void delete(Note note){
        repositry.delete(note);
   }
   public void deleteAllNotes(){
        repositry.deleteAllNotes();
    }
   public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
