package service;

import model.Epic;
import model.Subtask;

import java.util.ArrayList;

public class EpicManager extends BasicManager<Epic> {

    public ArrayList<Subtask> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

}
