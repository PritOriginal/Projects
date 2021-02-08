package com.example.kvantorium;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Coder extends AsyncTask<String, Integer, ImageView> {

    private QRCodeFragment act;
    private ProgressDialog dialog;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int ID = 34646456;
    LinearLayout layout;

    public Coder(QRCodeFragment act, LinearLayout layout) {
        this.act = act;
        this.layout = layout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ImageView doInBackground(String... params) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        ImageView image = new ImageView(act.getContext());
        try {
            BitMatrix matrix = new QRCodeWriter().encode(params[0],
                    com.google.zxing.BarcodeFormat.QR_CODE, width, width);
            image.setImageBitmap(matrixToBitmap(matrix));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        image.setId(ID);
        return image;
    }

    @Override
    protected void onPostExecute(ImageView image) {
        layout.addView(image, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        super.onPostExecute(image);
    }

    private Bitmap matrixToBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap image = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}