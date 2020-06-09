package com.example.a7_kabale;

import org.opencv.core.Rect;

import java.util.ArrayList;

public class RecognizedCard {
    int classID;
    float confidence;
    Rect rect;
    ArrayList<String> classNames;

    public RecognizedCard(int classID, float confidence, Rect rect) {
        this.classID = classID;
        this.confidence = confidence;
        this.rect = rect;
    }

    public float getConf() {
        return confidence;
    }

    public Rect getRect() {
        return rect;
    }

    public int getClassID() {
        return classID;
    }

    public String getClassName() {
        return ClassNames.cardList[classID];
    }

}
