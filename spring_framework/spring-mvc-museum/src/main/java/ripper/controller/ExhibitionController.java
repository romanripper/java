package ripper.controller;

import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ripper.model.Exhibit;
import ripper.model.Exhibition;
import ripper.model.IndividualExhibition;
import ripper.model.Person;
import ripper.model.Role;
import ripper.model.ThemedExhibition;
import ripper.service.ExhibitService;
import ripper.service.ExhibitionService;
import ripper.service.PersonService;
import ripper.util.Utils;
import ripper.wrapper_for_form.Exhibit_IdsFormWrapper;

@Controller
@RequestMapping("/exhibition")
@SessionAttributes("exhibition")
public class ExhibitionController {

	@Autowired
	private ExhibitionService exhibitionService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ExhibitService exhibitService;

	/**
	 * Trims strings for validation
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String showExhibitionList(Model model) {
		/**
		 * needed for form
		 */
		List<Exhibition> exhibitions = exhibitionService.getAllExhibitions();
		model.addAttribute("exhibitions", exhibitions);

		return "exhibitions-list";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam String type, Model model) {
		/**
		 * needed for form
		 */
		List<Person> curators = personService.getPersonsByRole(Role.CURATOR);
		model.addAttribute("curators", curators);

		if (type.equals("individual")) {

			/**
			 * If model does not contain the bindingResult then user want to get empty form
			 * to create IndividualExhibition so we have to create new object of type
			 * IndividualExhibition and add it to the model. If model contains the
			 * bindingResult then user submitted the form and validation failed so we don't
			 * have to add empty object to the model.
			 */
			if (!model.containsAttribute("org.springframework.validation.BindingResult.exhibition")) {
				IndividualExhibition exhibition = new IndividualExhibition();
				model.addAttribute("exhibition", exhibition);
			}

			/**
			 * needed for form
			 */
			List<Person> artists = personService.getPersonsByRole(Role.ARTIST);
			model.addAttribute("artists", artists);
		} else {

			/**
			 * If model does not contain the bindingResult then user want to get empty form
			 * to create ThemedExhibition so we have to create new object of type
			 * ThemedExhibition and add it to the model. If model contains the bindingResult
			 * then user submitted the form and validation failed so we don't have to add
			 * empty object to the model.
			 */

			if (!model.containsAttribute("org.springframework.validation.BindingResult.exhibition")) {
				ThemedExhibition exhibition = new ThemedExhibition();
				model.addAttribute("exhibition", exhibition);
			}
		}

		return "exhibition-form";
	}

	@PostMapping("/showFormForChoosingExhibits")
	public String showFormForChoosingExhibits(@Valid @ModelAttribute("exhibition") Exhibition exhibition,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		// validating the form
		String result = validateExhibitionForm(exhibition, bindingResult, redirectAttributes);
		if (result != null) {
			return result;
		}

		List<Exhibit> availableExhibits = null;

		// needed for getting IDs of chosen exhibits
		Exhibit_IdsFormWrapper exhibit_IdFormWrapper = new Exhibit_IdsFormWrapper();

		if (exhibition instanceof IndividualExhibition) {
			// get artist's exhibits that are available for the time new exhibition is
			// planned
			availableExhibits = exhibitService
					.getAvailableExhibitsForIndividualExhibition(((IndividualExhibition) exhibition));
		} else {
			// get exhibits that are available for the time new exhibition is
			// planned
			availableExhibits = exhibitService.getAvailableExhibitsForThemedExhibition(((ThemedExhibition) exhibition));
		}

		if (exhibition.getId() != 0) {
			// exhibition is old so we also have to load it's exhibits
			List<Exhibit> chosenExhibits = exhibitService.getExhibitsOfExhibition(exhibition);
			availableExhibits.addAll(chosenExhibits);

			// we set IDs for wrapper to show on form which exhibits were chosen before
			exhibit_IdFormWrapper.setExhibit_ids(Utils.getIdsFromExhibits(chosenExhibits));
		}

		model.addAttribute("exhibit_idsFormWrapper", exhibit_IdFormWrapper);
		model.addAttribute("exhibits", availableExhibits);

		// persist new exhibition or merge old one
		exhibitionService.saveExhibition(exhibition);

		return "choose-exhibits-form";
	}

	@PostMapping("/chooseExhibits")
	public String chooseExhibits(@ModelAttribute("exhibition") Exhibition exhibition,
			@ModelAttribute("exhibit_idsFormWrapper") Exhibit_IdsFormWrapper exhibit_idsFormWrapper) {

		// because the exhibits attribute is lazy loaded so we need to do it manually
		exhibition.setExhibits(new HashSet<Exhibit>(exhibitService.getExhibitsOfExhibition(exhibition)));

		// get exhibits that user chose and add them to new exhibition
		exhibitService.getExhibitsByIds(exhibit_idsFormWrapper.getExhibit_ids()).stream()
				.forEach(exhibit -> exhibition.addExhibit(exhibit));

		exhibitionService.saveExhibition(exhibition);

		return "redirect:/exhibition/showFormForUpdate?exhibitionId=" + exhibition.getId();
	}

	@PostMapping("/saveExhibition")
	public String saveExhibition(@Valid @ModelAttribute("exhibition") Exhibition exhibition,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, SessionStatus status) {

		// validating the form
		String result = validateExhibitionForm(exhibition, bindingResult, redirectAttributes);
		if (result != null) {
			return result;
		}

		// save exhibition
		exhibitionService.saveExhibition(exhibition);

		// remove session attributes
		status.setComplete();

		return "redirect:/exhibition/list";
	}

	/**
	 * Returns path where user has to be redirected or null if there are no
	 * validating errors on form
	 * 
	 * @param exhibition
	 * @param bindingResult
	 * @param redirectAttributes
	 * @return string containing path
	 */
	private String validateExhibitionForm(Exhibition exhibition, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exhibition",
					bindingResult);
			redirectAttributes.addFlashAttribute("exhibition", exhibition);

			/**
			 * exhibition is not saved in DB so we need to redirect user to
			 * '/exhibition/showFormForAdd'
			 */
			if (exhibition.getId() == 0) {
				if (exhibition instanceof IndividualExhibition)
					return "redirect:/exhibition/showFormForAdd?type=individual";
				else
					return "redirect:/exhibition/showFormForAdd?type=themed";
			}
			/**
			 * exhibition is saved in DB so we need to redirect user to
			 * '/exhibition/showFormForUpdate'
			 */
			else {
				return "redirect:/exhibition/showFormForUpdate?exhibitionId=" + exhibition.getId();
			}
		} 
		/**
		 * there was no errors so we return null
		 */
		else {
			return null;
		}
	}

	@GetMapping("showFormForUpdate")
	public String showFormForUpdate(@RequestParam("exhibitionId") long id, Model model) {

		// load exhibition from DB that have to be updated
		Exhibition exhibition = exhibitionService.getExhibitionById(id);

		
		// needed for form
		List<Person> curators = personService.getPersonsByRole(Role.CURATOR);
		model.addAttribute("curators", curators);

		if (exhibition instanceof IndividualExhibition) {

			/**
			 * If model does not contain the bindingResult then user want to get empty form
			 * to create IndividualExhibition so we have to create new object of type
			 * IndividualExhibition and add it to the model. If model contains the
			 * bindingResult then user submitted the form and validation failed so we don't
			 * have to add empty object to the model.
			 */

			if (!model.containsAttribute("org.springframework.validation.BindingResult.exhibition")) {
				model.addAttribute("exhibition", ((IndividualExhibition) exhibition));
			}
			
			// needed for form
			List<Person> artists = personService.getPersonsByRole(Role.ARTIST);
			model.addAttribute("artists", artists);
		} else {
			
			/**
			 * If model does not contain the bindingResult then user want to get empty form
			 * to create ThemedExhibition so we have to create new object of type
			 * ThemedExhibition and add it to the model. If model contains the bindingResult
			 * then user submitted the form and validation failed so we don't have to add
			 * empty object to the model.
			 */
			if (!model.containsAttribute("org.springframework.validation.BindingResult.exhibition")) {
				model.addAttribute("exhibition", ((ThemedExhibition) exhibition));
			}
		}
		return "exhibition-form";
	}

	@GetMapping("/deleteExhibition")
	public String deleteExhibition(@RequestParam("exhibitionId") long id) {

		// delete exhibition
		exhibitionService.deteleExhibitionById(id);

		return "redirect:/exhibition/list";
	}

}
