package ma.ensao.backend_pfa.controller;

import ma.ensao.backend_pfa.service.csvlist.EleveImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
    @RequestMapping("/api/import")
    public class EleveImportController {
    /*

        private final EleveImportService importService;

        public EleveImportController(EleveImportService importService) {
            this.importService = importService;
        }

        @PostMapping("/eleves")
        public ResponseEntity<String> importEleves(@RequestParam("file") MultipartFile file) {
            importService.importFromCSV(file);
            return ResponseEntity.ok("Importation réussie !");
        }

     */
}
