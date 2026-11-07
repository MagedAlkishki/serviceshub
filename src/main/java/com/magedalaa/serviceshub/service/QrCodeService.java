package com.magedalaa.serviceshub.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    private static final int SIZE_PX = 300;

    /** Renders the given text as a QR code PNG, encoded straight to bytes - never written to disk. */
    public byte[] generate(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Enter some text or a URL to encode");
        }
        try {
            BitMatrix matrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, SIZE_PX, SIZE_PX);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't generate a QR code for that input");
        }
    }
}
