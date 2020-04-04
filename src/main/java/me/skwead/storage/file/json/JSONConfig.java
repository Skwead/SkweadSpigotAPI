package me.skwead.storage.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

@SuppressWarnings("unused")
public class JSONConfig<T> {
    private T data;
    private Class<T> Tclass;
    private String path;

    public JSONConfig(String path, Class<T> Tclass) {
        this.path = path;
        this.Tclass = Tclass;

        try {
            loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File f = new File(path);
        if(!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(this.data == null) {
            try {
                this.data = Tclass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public T loadFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(this.path));

        T res = gson.fromJson(bufferedReader, this.Tclass);
        this.data = res;
        return res;
    }

    public void saveFile() throws IOException {
        Writer fw = new FileWriter(new File(this.path));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
