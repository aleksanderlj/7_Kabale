package com.example.a7_kabale.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.a7_kabale.Database.Entity.Instruction;

import java.util.List;

@Dao
public interface InstructionDAO {

    @Transaction
    @Query("SELECT * FROM Instruction")
    List<Instruction> getAll();

    @Transaction
    @Query("SELECT * FROM Instruction WHERE instruction_id IN (:instruction_ids)")
    List<Instruction> getAllByIds(List<Long> instruction_ids);

    @Transaction
    @Query("SELECT * FROM Instruction WHERE instruction_id = :instruction_id")
    Instruction getById(long instruction_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Instruction instruction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertList(List<Instruction> instructions);

    @Update
    void update(Instruction instruction);

    @Update
    void updateList(List<Instruction> instructions);

    @Delete
    void delete(Instruction instruction);

    @Delete
    void deleteList(List<Instruction> instructions);

    @Query("DELETE FROM Instruction")
    public void nuke();
}
