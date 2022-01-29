package com.liamfroyen;

import javax.xml.bind.DatatypeConverter;

public class RenderContext extends Bitmap{

    private final int scanBuffer[];

    public RenderContext (int width, int height) {
        super(width, height);
        scanBuffer = new int[height * 2];
    }

    public void FillShape(int yMin, int yMax, byte r, byte g, byte b) {
        for (int i = yMin; i < yMax; i++) {
            int xMin = scanBuffer[i * 2    ];
            int xMax = scanBuffer[i * 2 + 1];

            for (int j = xMin; j < xMax; j++) {
                DrawPixel(j, i, (byte)0xFF, g, b, r);
            }
        }
    }

    public void DrawMesh(Mesh mesh, Matrix4f trans) {
        for (int i = 0; i < mesh.getNumIndices(); i += 3) {
            FillTriangle(
                    mesh.getVertex(mesh.getIndex(i)).Transform(trans),
                    mesh.getVertex(mesh.getIndex(i + 1)).Transform(trans),
                    mesh.getVertex(mesh.getIndex(i + 2)).Transform(trans));
        }
    }

    public void FillTriangle(Vertex v1, Vertex v2, Vertex v3) {
        Matrix4f screenSpaceTransform = new Matrix4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);

        Vertex minVert_ = v1.Transform(screenSpaceTransform).PerspectiveDivide();
        Vertex midVert_ = v2.Transform(screenSpaceTransform).PerspectiveDivide();
        Vertex maxVert_ = v3.Transform(screenSpaceTransform).PerspectiveDivide();

        if (minVert_.TriangleArea(maxVert_, midVert_) >= 0) return;

        if(maxVert_.GetY() < midVert_.GetY())
        {
            Vertex temp = maxVert_;
            maxVert_ = midVert_;
            midVert_ = temp;
        }

        if(midVert_.GetY() < minVert_.GetY())
        {
            Vertex temp = midVert_;
            midVert_ = minVert_;
            minVert_ = temp;
        }

        if(maxVert_.GetY() < midVert_.GetY())
        {
            Vertex temp = maxVert_;
            maxVert_ = midVert_;
            midVert_ = temp;
        }


        float area = minVert_.TriangleArea(maxVert_, midVert_);
        int whichSide = area >= 0 ? 1 : 0;

        ScanConvertTriangle(minVert_, midVert_, maxVert_, whichSide);

        //getting the color from depth
        float z1 = maxVert_.GetZ();
        float z2 = midVert_.GetZ();
        float z3 = maxVert_.GetZ();

        float avr = (z1 + z2 + z3)/3;
        int amountOfGray = 555 - Math.round(avr*255*2f);
        if (amountOfGray < 0) amountOfGray = 0;

        String hex = String.format("%02x%02x%02x", amountOfGray, amountOfGray, amountOfGray);
        byte[] decodedToByte = DatatypeConverter.parseHexBinary(hex);

        FillShape((int) minVert_.GetY(), (int) maxVert_.GetY(), decodedToByte[0], decodedToByte[1], decodedToByte[2]);

    }

    public void ScanConvertTriangle(Vertex minVert_, Vertex midVert_, Vertex maxVert_, int whichSide) {
        ScanConvertLine(minVert_, maxVert_, 0 + whichSide);
        ScanConvertLine(minVert_, midVert_, 1 - whichSide);
        ScanConvertLine(midVert_, maxVert_, 1 - whichSide);
    }

    private void ScanConvertLine(Vertex minVert, Vertex maxVert, int whichSide) {
        int yStart = (int) minVert.GetY();
        int yEnd   = (int) maxVert.GetY();
        int xStart = (int) minVert.GetX();
        int xEnd   = (int) maxVert.GetX();

        int yDist = yEnd - yStart;
        int xDist = xEnd - xStart;

        //out of display
        if (yDist <= 0) return;

        float ricoX = (float) xDist/yDist;
        float currX = (float) xStart;

        for (int i = yStart; i < yEnd; i++) {
            scanBuffer[i * 2 + whichSide] = (int) currX;
            currX += ricoX;
        }
    }
}
