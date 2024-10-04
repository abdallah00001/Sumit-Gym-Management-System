package com.summit.gym.Sumit_Gym_Management_System.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrCodeService {

    private final String QrSaveFolderPath = System.getProperty("user.dir") + File.separator;


    public String generateQrCode(Long value) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return encodeImageToBase64(image);
    }

    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] qrCodeBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(qrCodeBytes);
    }

    public String saveQrCodeToFile(Long value) throws WriterException, IOException {
        String filePath = QrSaveFolderPath + value.toString() + ".png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        ImageIO.write(image, "png", new File(filePath));
        return filePath;
    }

//    public void printQrCode(Long value) throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 200, 200);
//
//        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
//        for (int x = 0; x < 200; x++) {
//            for (int y = 0; y < 200; y++) {
//                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
//            }
//        }
//
//        // Print the QR code image
//        PrinterJob printerJob = PrinterJob.getPrinterJob();
//        printerJob.setPrintable(new Printable() {
//            @Override
//            public int print(Graphics g, PageFormat pf, int pageIndex) {
//                if (pageIndex > 0) {
//                    return NO_SUCH_PAGE;
//                }
//                g.drawImage(image, 0, 0, null);
//                return PAGE_EXISTS;
//            }
//        });
//        if (printerJob.printDialog()) {
//            printerJob.print();
//        }
//    }



    public static void main(String[] args) throws IOException, WriterException {
        QrCodeService qrCodeService = new QrCodeService();
//        String s = qrCodeService.generateQrCode(1L);
//        System.out.println(s);
        qrCodeService.saveQrCodeToFile(1L);
//        qrCodeService.saveQrCodeToFile(1L, ".");
    }

}

