package com.sevixoo.android3dge.android;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by seweryn on 24.07.2017.
 */

public class AndroidContext extends SystemContext {

    private Context mContext;

    public AndroidContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String loadFile(String path) throws IOException {
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();
        try {
            reader = new BufferedReader( new InputStreamReader(mContext.getAssets().open(path)));
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

}