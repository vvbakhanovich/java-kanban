package service;

import model.Epic;
import model.Subtask;

import java.util.ArrayList;

public class EpicManager extends BasicManager<Epic> {

    // получение списка подзадач для эпика
    public ArrayList<Subtask> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

}
