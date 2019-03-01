package braintrain.model;

import java.util.List;

import braintrain.model.lesson.Lesson;

/**
 * Wraps all lesson data in memory.
 */
public class Lessons {
    private List<Lesson> lessons;

    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Removes lesson at index.
     * @param index
     */
    public void deleteLesson(int index) {
        //TODO Handle exceptions
        lessons.remove(index);
    }

    /**
     * Adds a lesson to the current list.
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void setLesson(int index, Lesson lesson) {
        lessons.set(index, lesson);
    }
}
