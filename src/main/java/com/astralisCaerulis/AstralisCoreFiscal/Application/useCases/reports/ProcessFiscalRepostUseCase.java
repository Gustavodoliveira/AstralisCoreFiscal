
package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.reports;

import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;

@Service
public class ProcessFiscalRepostUseCase {

  // Mantém o método antigo para compatibilidade, mas recomenda-se usar o novo
  public BigDecimal executeFromBytes(List<byte[]> nfsFiles) {
    BigDecimal totalPis = BigDecimal.ZERO;
    BigDecimal totalCofins = BigDecimal.ZERO;

    for (byte[] fileBytes : nfsFiles) {
      try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(fileBytes));
        doc.getDocumentElement().normalize();

        NodeList pisList = doc.getElementsByTagName("PIS");
        for (int i = 0; i < pisList.getLength(); i++) {
          Element pisElement = (Element) pisList.item(i);
          NodeList vPisNodes = pisElement.getElementsByTagName("vPIS");
          if (vPisNodes.getLength() > 0) {
            String valor = vPisNodes.item(0).getTextContent();
            totalPis = totalPis.add(new BigDecimal(valor));
          }
        }

        NodeList cofinsList = doc.getElementsByTagName("COFINS");
        for (int i = 0; i < cofinsList.getLength(); i++) {
          Element cofinsElement = (Element) cofinsList.item(i);
          NodeList vCofinsNodes = cofinsElement.getElementsByTagName("vCOFINS");
          if (vCofinsNodes.getLength() > 0) {
            String valor = vCofinsNodes.item(0).getTextContent();
            totalCofins = totalCofins.add(new BigDecimal(valor));
          }
        }
      } catch (Exception e) {
        // Trate exceções conforme necessário (log, lançar, etc)
        e.printStackTrace();
      }
    }
    // Retorne a soma total de PIS e COFINS (ou crie um DTO para retornar separado)
    return totalPis.add(totalCofins);
  }
}
