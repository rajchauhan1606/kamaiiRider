/*
 * *
 *  * Created by WebKnight Infosystem on 14/5/20 10:52 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 14/5/20 10:37 AM
 *
 */

package com.webknight.filemanager.filter.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import static android.provider.MediaStore.MediaColumns.MIME_TYPE;


public class AudioLoader extends CursorLoader {
    private static final String[] AUDIO_PROJECTION = {
            //Base File
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            //Audio File
            MediaStore.Audio.Media.DURATION
    };

    private AudioLoader(Context context, Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    public AudioLoader(Context context) {
        super(context);

        setProjection(AUDIO_PROJECTION);
        setUri(MediaStore.Files.getContentUri("external"));
        setSortOrder(MediaStore.Audio.Media.DATE_ADDED + " DESC");

        setSelection(MIME_TYPE + "=? or "
                + MIME_TYPE + "=? or "
//                + MIME_TYPE + "=? or "
                + MIME_TYPE + "=?");
        String[] selectionArgs;
        selectionArgs = new String[]{"audio/mpeg", "audio/mp3", "audio/x-ms-wma"};
        setSelectionArgs(selectionArgs);
    }
}
