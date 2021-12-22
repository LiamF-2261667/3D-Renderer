package com.liamfroyen;

public class Vertex {
    private float x;
    private float y;

    public float GetX() { return x; }
    public float GetY() { return y; }

    public void SetX(float _x) { x = _x; }
    public void SetY(float _y) { y = _y; }

    public Vertex (float _x, float _y) {
        x = _x;
        y = _y;
    }

    public float TriangleArea(Vertex b, Vertex c) {
        float x1 = b.GetX() - x;
        float y1 = b.GetY() - y;

        float x2 = c.GetX() - x;
        float y2 = c.GetY() - y;

        return x1 * y2 - x2 * y1;
    }
}
