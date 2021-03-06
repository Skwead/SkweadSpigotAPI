package api.skwead.storage.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

/**
 * Handles json configuration files based on GSON
 *
 * @param <T> the data type to be stored
 */
@SuppressWarnings("unused")
public class JSONConfig<T> {
    public static final Gson GSON = new GsonBuilder().serializeNulls().create();

    private T data;
    private final Class<T> Tclass;
    private final String path;

    /**
     * Creates a configuration object
     *
     * @param path   the path to the file
     * @param Tclass the class of the data to be stored
     */
    public JSONConfig(String path, Class<T> Tclass) {
        this.path = path;
        this.Tclass = Tclass;

        try {
            loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File f = new File(path);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.data == null) {
            try {
                this.data = Tclass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the file into memory
     *
     * @throws FileNotFoundException if the file was not found
     * @see FileReader
     */
    public void loadFile() throws FileNotFoundException {
        final BufferedReader bufferedReader = new BufferedReader(
                new FileReader(this.path));

        final Type type = new TypeToken<T>(){}.getType();

        this.data = GSON.fromJson(bufferedReader, this.Tclass);
//        try{
//            this.data = GSON.fromJson(bufferedReader, type);
//        } catch (ClassCastException e){
//            this.data = GSON.fromJson(bufferedReader, (Type) this.data.getClass());
//        }
    }

    /**
     * Saves the data in memory to the file
     *
     * @throws IOException if any problem occurs creating the {@link FileWriter}
     * @see FileWriter
     */
    public void saveFile() throws IOException {
        Writer fw = new FileWriter(new File(this.path));

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        gson.toJson(data, fw);

        fw.close();
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getPath() {
        return path;
    }
}
