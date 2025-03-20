package com.netcalc.IPCalculator.Controllers;

import com.netcalc.IPCalculator.Models.SubnetRequest;
import com.netcalc.IPCalculator.Services.StaticCalculatorService;
import com.netcalc.IPCalculator.Services.VlsmCalculatorService;
import com.netcalc.IPCalculator.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {
    private final StaticCalculatorService staticService;
    private final VlsmCalculatorService vlsmService;

    public CalculatorController(StaticCalculatorService staticService, VlsmCalculatorService vlsmService) {
        this.staticService = staticService;
        this.vlsmService = vlsmService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("subnetRequest", new SubnetRequest("", 0, "FLSM"));
        return "index";
    }

    @PostMapping("/calculate")
    @ResponseBody
    public ResponseEntity<String> calculateSubnets(@RequestBody SubnetRequest request) {
        try {
            if (!staticService.isValidIp(request.getIp())) {
                return ResponseEntity.badRequest().body("Błąd: Nieprawidłowy adres IP");
            }

            if (!staticService.isSubnettingPossible(request.getIp(), request.getAmountOfSubnets())) {
                return ResponseEntity.badRequest().body("Błąd: Nie można utworzyć tylu podsieci dla tego adresu IP");
            }

            String result = request.getMethod().equals("FLSM") ?
                    staticService.calculateStatic(request) :
                    vlsmService.calculateVlsm(request);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd: " + e.getMessage());
        }
    }

    @PostMapping("/download")
    @ResponseBody
    public String downloadResults(@RequestBody SubnetRequest request) {
        try {
            String result;
            if (request.getMethod().equals("FLSM")) {
                result = staticService.calculateStatic(request);
            } else {
                result = vlsmService.calculateVlsm(request);
            }

            String fileName = "wyniki_" + System.currentTimeMillis() + ".txt";
            FileUtil.writeFile(fileName, result);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania pliku: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = FileUtil.getFileAsResource(fileName);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}