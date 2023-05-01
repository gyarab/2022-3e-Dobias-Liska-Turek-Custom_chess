import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

class intArrayAdapter extends TypeAdapter<int[]> { 
    @Override 
    public int[] read(JsonReader reader) throws IOException { 
        int[] it = new int[2];
        reader.beginArray();
        it[0] = reader.nextInt();
        it[1] = reader.nextInt();
        reader.endArray();
        return it;
    } 
    
    @Override 
    public void write(JsonWriter writer, int[] it) throws IOException { 
        writer.beginArray();
        writer.value(it[0]);
        writer.value(it[1]);
        writer.endArray();
        writer.flush();
    } 
 }