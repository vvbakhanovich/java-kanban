package http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import tasks.BasicTask;
import tasks.Subtask;
import tasks.Task;

import java.lang.reflect.Type;

/**
 * Адаптер для сериализации и десириализации объектов абстракного класса Task
 */
public class TaskTypeAdapter implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String elementType = jsonElement.getAsJsonObject().get("taskType").getAsString();
        switch (elementType)  {
            case "BASIC_TASK":
                return jsonDeserializationContext.deserialize(jsonElement, BasicTask.class);
            case "SUBTASK":
                return jsonDeserializationContext.deserialize(jsonElement, Subtask.class);
            default:
                throw new IllegalArgumentException("Недопустимый тип задачи!");
        }
    }
}
