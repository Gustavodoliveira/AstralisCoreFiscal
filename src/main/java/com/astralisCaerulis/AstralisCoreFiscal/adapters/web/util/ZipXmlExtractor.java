package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.ByteArrayOutputStream;

public class ZipXmlExtractor {
  public static List<byte[]> extractXmlFiles(InputStream zipInputStream) throws IOException {
    List<byte[]> xmlFiles = new ArrayList<>();
    try (ZipInputStream zis = new ZipInputStream(zipInputStream)) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".xml")) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          byte[] buffer = new byte[4096];
          int read;
          while ((read = zis.read(buffer)) != -1) {
            baos.write(buffer, 0, read);
          }
          xmlFiles.add(baos.toByteArray());
        }
      }
    }
    return xmlFiles;
  }
}
