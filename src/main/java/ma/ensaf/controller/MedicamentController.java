package ma.ensaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ma.ensaf.model.Medicament;
import ma.ensaf.repository.MedicamentRepository;

@Controller
public class MedicamentController {

    @Autowired
    private MedicamentRepository medicamentRepository;

    // Consultation (lecture seule) : ADMIN, PHARMACIEN, CAISSIER, CLIENT
    @GetMapping("/medicaments/liste")
    public String liste(Model model) {
        List<Medicament> medicaments = medicamentRepository.findAll();
        model.addAttribute("medicaments", medicaments);
        return "medicaments";
    }

    // Gestion globale des médicaments : ADMIN
    @GetMapping("/medicaments/gerer")
    public String gerer(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message",
                "Gestion des médicaments (ADMIN) : fonctionnalité accessible.");
        return "redirect:/medicaments/liste";
    }

    // Modification du stock : PHARMACIEN (et ADMIN)
    @GetMapping("/medicaments/action/stock")
    public String modifierStock(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message",
                "Modification du stock (PHARMACIEN) : fonctionnalité accessible.");
        return "redirect:/medicaments/liste";
    }

    // Vente par le pharmacien : PHARMACIEN (et ADMIN)
    @GetMapping("/medicaments/action/vendre")
    public String vendre(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message",
                "Vente de médicaments (PHARMACIEN) : fonctionnalité accessible.");
        return "redirect:/medicaments/liste";
    }

    // Enregistrement d'une vente : CAISSIER (et ADMIN)
    @GetMapping("/ventes/enregistrer")
    public String enregistrerVente(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message",
                "Enregistrement d'une vente (CAISSIER) : fonctionnalité accessible.");
        return "redirect:/medicaments/liste";
    }
}