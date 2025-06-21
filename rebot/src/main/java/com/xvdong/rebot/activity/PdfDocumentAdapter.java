package com.xvdong.rebot.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xvDong on 2025/6/21.
 */

public class PdfDocumentAdapter extends PrintDocumentAdapter {
    private final Context context;
    private final Uri pdfUri;
    private ParcelFileDescriptor fileDescriptor;

    public PdfDocumentAdapter(Context context, Uri pdfUri) {
        this.context = context;
        this.pdfUri = pdfUri;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        // 创建打印文档信息
        PrintDocumentInfo info = new PrintDocumentInfo.Builder("output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build();

        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                        CancellationSignal cancellationSignal,
                        WriteResultCallback callback) {
        try {
            // 打开 PDF 文件输入流
            InputStream input = context.getContentResolver().openInputStream(pdfUri);

            // 获取输出文件描述符
            OutputStream output = new FileOutputStream(destination.getFileDescriptor());

            // 复制文件内容
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

            // 关闭流
            input.close();
            output.close();

        } catch (Exception e) {
            callback.onWriteFailed(e.getMessage());
        }
    }
}