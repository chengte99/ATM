package com.chengte99.atm.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey
    @NonNull
    String name;
    String mean;
}
