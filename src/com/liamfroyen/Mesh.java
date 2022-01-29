package com.liamfroyen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mesh {

    private List<Vertex> vertices;
    private List<Integer> indices;

    public Vertex getVertex(int i) { return vertices.get(i); }
    public int getIndex(int i) { return indices.get(i); }
    public int getNumIndices() { return indices.size(); }

    public Mesh(String fileName) throws IOException {
        IndexedModel model = new OBJModel(fileName).ToIndexedModel();

        vertices = new ArrayList<Vertex>();
        for (int i = 0; i < model.GetPositions().size(); i++) {
            vertices.add(new Vertex(model.GetPositions().get(i)));
        }

        indices = model.GetIndices();
    }
}
