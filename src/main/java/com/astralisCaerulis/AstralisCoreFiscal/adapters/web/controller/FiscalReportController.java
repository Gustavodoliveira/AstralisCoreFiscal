package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.controller;

import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.reports.ProcessFiscalRepostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.web.util.ZipXmlExtractor;
import java.io.InputStream;

@RestController
@RequestMapping("/fiscal-report")
public class FiscalReportController {

  @Autowired
  private ProcessFiscalRepostUseCase processFiscalRepostUseCase;

  @PostMapping(value = "/upload-zip", consumes = "multipart/form-data")
  public ResponseEntity<String> uploadZip(@RequestParam("file") MultipartFile zipFile) {
    List<byte[]> xmlFiles;
    try (InputStream is = zipFile.getInputStream()) {
      xmlFiles = ZipXmlExtractor.extractXmlFiles(is);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao processar o arquivo ZIP: " + e.getMessage());
    }
    var total = processFiscalRepostUseCase.executeFromBytes(xmlFiles);
    return ResponseEntity.ok("Total de PIS + COFINS: " + total);
  }
}
