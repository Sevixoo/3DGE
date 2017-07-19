package com.sevixoo.android3dge_app.loader;

import android.util.Log;

import com.sevixoo.android3dge_app.FileParser;
import com.sevixoo.android3dge_app.Object3D;
import com.sevixoo.android3dge_app.math.Vector2f;
import com.sevixoo.android3dge_app.math.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pi19124 on 28.06.2017.
 */

public class ObjFileParser2 implements FileParser{

    public Object3D parse(BufferedReader reader)throws IOException{

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArray = null;
        float[] texturesArray = null;
        float[] normalsArray = null;
        int[] indicesArray = null;

        String line = null;
        float scale = 1.0f;

        while (true) {
            line = reader.readLine();
            line = line.replace("  "," ");
            String[] currentLine = line.split(" ");
            if(line.startsWith("v ")){
                Vector3f vertex = new Vector3f(
                        Float.parseFloat(currentLine[1])*scale,
                        Float.parseFloat(currentLine[2])*scale,
                        Float.parseFloat(currentLine[3])*scale);
                vertices.add(vertex);
            }else if(line.startsWith("vt ")){
                Vector2f texture = new Vector2f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]));
                textures.add(texture);
            }else if(line.startsWith("vn ")){
                Vector3f normal = new Vector3f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]),
                        Float.parseFloat(currentLine[3]));
                normals.add(normal);
            }else if(line.startsWith("f ")){
                texturesArray = new float[vertices.size()*2];
                normalsArray = new float[vertices.size()*3];
                break;
            }
        }

        while (line!=null){
            if(!line.startsWith("f ")){
                line = reader.readLine();
                continue;
            }
            String[] currentLine = line.split(" ");
            String[] vertex1 = currentLine[1].split("/");
            String[] vertex2 = currentLine[1].split("/");
            String[] vertex3 = currentLine[1].split("/");
            try {
                processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
            }catch (Exception ex){
                Log.e("Exception","line=" + line);
                throw ex;
            }
            line = reader.readLine();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex:vertices){
            verticesArray[vertexPointer++] = vertex.x();
            verticesArray[vertexPointer++] = vertex.y();
            verticesArray[vertexPointer++] = vertex.z();
        }

        for(int i = 0; i < indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }

        return new Object3D(verticesArray,texturesArray,normalsArray);
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1])-1);
        textureArray[currentVertexPointer*2] = currentTexture.x();
        textureArray[currentVertexPointer*2+1] = 1 - currentTexture.y();
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*2] = currentNorm.x();
        normalsArray[currentVertexPointer*2+1] = currentNorm.y();
        normalsArray[currentVertexPointer*2+2] = currentNorm.z();
    }

}
