package com.fullmob.jiraboard.ui.printing;

import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;

import java.io.FileOutputStream;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
class JiraBoardPdfDocumentAdapter extends PrintDocumentAdapter {
    private PrintedPdfDocument document;
    private final Listener listener;

    public JiraBoardPdfDocumentAdapter(PrintedPdfDocument document, Listener listener) {
        this.document = document;
        this.listener = listener;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
            .Builder("pdf_output.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(1);
        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        try {
            document.writeTo(new FileOutputStream(destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            if (document == null) {
                return;
            }
            document.close();
            document = null;
        }
        callback.onWriteFinished(pages);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    public interface Listener {
        void onPrintingStarted();
        void onPrintingFinished();
    }
}
