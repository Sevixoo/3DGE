package com.sevixoo.android3dge_app;

import android.content.Context;

import com.sevixoo.android3dge_app.loader.ObjFileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by pi19124 on 26.06.2017.
 */

public class Loader {

    private Context mContext;

    public Loader(Context context) {
        this.mContext = context;
    }

    public String loadFile( String fileName )throws IOException{
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();
        try {
            reader = new BufferedReader( new InputStreamReader(mContext.getAssets().open(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                returnString.append(line);
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {}
            }
        }
        return returnString.toString();
    }

    public Object3D loadObj( String fileName )throws IOException{
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();
        try {
            reader = new BufferedReader( new InputStreamReader(mContext.getAssets().open(fileName)));
            return new ObjFileParser().parse(reader);
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {}
            }
        }
    }

}
