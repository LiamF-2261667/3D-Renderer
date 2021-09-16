package com.liamfroyen;

public class Stars3D {

    private final float fov;

    private final float spread;
    private final float speed;

    private final float starX[];
    private final float starY[];
    private final float starZ[];

    public Stars3D(int numStars, float spread_, float speed_, float fovInDegrees_) {
        spread = spread_;
        speed = speed_;
        fov = (float)Math.tan(Math.toRadians(fovInDegrees_/2));

        starX = new float[numStars];
        starY = new float[numStars];
        starZ = new float[numStars];

        for (int i = 0; i < starX.length; i++) {
            InitStar(i);
        }
    }

    private void InitStar(int i) {
        starX[i] = 2 * ((float)Math.random() - 0.5f) * spread;
        starY[i] = 2 * ((float)Math.random() - 0.5f) * spread;
        starZ[i] = ((float)Math.random()) * spread;
    }

    public void UpdateAndRender(Bitmap target, float delta) {
        target.Clear((byte)0x00);

        float halfWidth = target.GetWidth()/2;
        float halfHeight = target.GetHeight()/2;

        for (int i = 0; i < starX.length; i++) {
            starZ[i] -= delta * speed; //met delta de tijd sinds de vorige update

            if (starZ[i] <= 0) {
                InitStar(i);
            }

            int x = (int)((starX[i]/(starZ[i] * fov)) * halfWidth + halfWidth);
            int y = (int)((starY[i]/(starZ[i] * fov)) * halfHeight + halfHeight);

            if (x < 0 || x >= target.GetWidth() || y < 0 || y >= target.GetHeight())  {
                InitStar(i);
            } else {
                target.DrawPixel(x, y, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF);
            }
        }
    }

}
