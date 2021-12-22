package com.liamfroyen;

public class RenderContext extends Bitmap{

    private final int scanBuffer[];

    public RenderContext (int width, int height) {
        super(width, height);
        scanBuffer = new int[height * 2];
    }

    public void DrawScanBuffer(int yCoord, int xMin, int xMax) {
        scanBuffer[yCoord * 2    ] = xMin;
        scanBuffer[yCoord * 2 + 1] = xMax;
    }

    public void FillShape(int yMin, int yMax) {
        for (int i = yMin; i < yMax; i++) {
            int xMin = scanBuffer[i * 2    ];
            int xMax = scanBuffer[i * 2 + 1];

            for (int j = xMin; j < xMax; j++) {
                DrawPixel(j, i, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
            }
        }
    }

    public void ScanConvertTriangle(Vertex minVert, Vertex midVert, Vertex maxVert, int whichSide) {
        ScanConvertLine(minVert, maxVert, 0 + whichSide);
        ScanConvertLine(minVert, midVert, 1 - whichSide);
        ScanConvertLine(midVert, maxVert, 1 - whichSide);
    }

    public void FillTriangle(Vertex v1, Vertex v2, Vertex v3) {
        Vertex minVert_ = v1;
        Vertex midVert_ = v2;
        Vertex maxVert_ = v3;

        if(maxVert_.GetY() < midVert_.GetY()) SwapVertex(maxVert_, midVert_);
        if(midVert_.GetY() < minVert_.GetY()) SwapVertex(midVert_, minVert_);
        if(maxVert_.GetY() < midVert_.GetY()) SwapVertex(maxVert_, midVert_);

        float area = minVert_.TriangleArea(maxVert_, midVert_);
        int whichSide = area >= 0 ? 1 : 0;

        ScanConvertTriangle(minVert_, midVert_, maxVert_, whichSide);
        FillShape((int) minVert_.GetY(), (int) maxVert_.GetY());
    }

    private void SwapVertex(Vertex toSwap, Vertex target) {
        Vertex temp = toSwap;
        toSwap = target;
        target = temp;
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
