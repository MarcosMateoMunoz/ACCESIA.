package org.iesalixar.daw2.MarcosMateoMunoz.accesia.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.EstablecimientoDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.FileStorageException;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.mappers.EstablecimientoMapper;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.services.EstablecimientoService;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.services.FileStorageService;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.services.UsuarioService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CRUD de Establecimiento. Cada establecimiento está asociado a un Usuario propietario (relación 1:N).
 */
@Controller
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    private static final String VIEW_LIST = "establecimiento-list";
    private static final String VIEW_DETAIL = "establecimiento-detail";
    private static final String VIEW_FORM = "establecimiento-form";
    private static final String REDIRECT_LIST = "redirect:/establecimientos";

    private final EstablecimientoService establecimientoService;
    private final UsuarioService usuarioService;
    private final EstablecimientoMapper mapper;
    private final FileStorageService fileStorage;

    public EstablecimientoController(EstablecimientoService establecimientoService,
                                     UsuarioService usuarioService,
                                     EstablecimientoMapper mapper,
                                     FileStorageService fileStorage) {
        this.establecimientoService = establecimientoService;
        this.usuarioService = usuarioService;
        this.mapper = mapper;
        this.fileStorage = fileStorage;
    }

    // ------------------------------------------------------------------ LISTADO / DETALLE

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("establecimientos", establecimientoService.listar());
        return VIEW_LIST;
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("establecimiento", establecimientoService.obtener(id));
        return VIEW_DETAIL;
    }

    // ------------------------------------------------------------------ ALTA

    @GetMapping("/new")
    public String nuevo(Model model) {
        EstablecimientoDTO dto = new EstablecimientoDTO();
        model.addAttribute("establecimiento", dto);
        prepararFormulario(model, false);
        return VIEW_FORM;
    }

    @PostMapping("/insert")
    public String insertar(@Valid @ModelAttribute("establecimiento") EstablecimientoDTO dto,
                           BindingResult result,
                           @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        validarReglasPropias(dto, imagenFile, result);
        if (result.hasErrors()) {
            prepararFormulario(model, false);
            return VIEW_FORM;
        }

        try {
            establecimientoService.crear(dto, imagenFile);
            redirectAttributes.addFlashAttribute("successKey", "establecimiento.msg.created");
            return REDIRECT_LIST;
        } catch (DuplicateResourceException ex) {
            result.rejectValue("nombre", "Duplicate", "Ya existe un establecimiento con ese nombre");
        } catch (FileStorageException ex) {
            model.addAttribute("errorKey", "error.file");
        } catch (DataAccessException ex) {
            model.addAttribute("errorKey", "error.db");
        }
        prepararFormulario(model, false);
        return VIEW_FORM;
    }

    // ------------------------------------------------------------------ MODIFICACIÓN

    @GetMapping("/{id}/edit")
    public String editar(@PathVariable Long id, Model model) {
        Establecimiento establecimiento = establecimientoService.obtener(id);
        model.addAttribute("establecimiento", mapper.toDTO(establecimiento));
        prepararFormulario(model, true);
        return VIEW_FORM;
    }

    @PostMapping("/{id}/update")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("establecimiento") EstablecimientoDTO dto,
                             BindingResult result,
                             @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        dto.setId(id);
        // Si no existe, el GlobalExceptionHandler muestra la página 404.
        dto.setImagen(establecimientoService.obtener(id).getImagen());

        validarReglasPropias(dto, imagenFile, result);
        if (result.hasErrors()) {
            prepararFormulario(model, true);
            return VIEW_FORM;
        }

        try {
            establecimientoService.actualizar(id, dto, imagenFile);
            redirectAttributes.addFlashAttribute("successKey", "establecimiento.msg.updated");
            return REDIRECT_LIST;
        } catch (DuplicateResourceException ex) {
            result.rejectValue("nombre", "Duplicate", "Ya existe un establecimiento con ese nombre");
        } catch (FileStorageException ex) {
            model.addAttribute("errorKey", "error.file");
        } catch (DataAccessException ex) {
            model.addAttribute("errorKey", "error.db");
        }
        prepararFormulario(model, true);
        return VIEW_FORM;
    }

    // ------------------------------------------------------------------ BORRADO

    @PostMapping("/{id}/delete")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            establecimientoService.eliminar(id);
            redirectAttributes.addFlashAttribute("successKey", "establecimiento.msg.deleted");
        } catch (FileStorageException ex) {
            redirectAttributes.addFlashAttribute("errorKey", "error.file");
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("errorKey", "error.db");
        }
        return REDIRECT_LIST;
    }

    // Si el usuario cambia de idioma estando en el formulario tras un error, la URL es un POST: lo reconducimos.

    @GetMapping("/insert")
    public String insertarGet() {
        return "redirect:/establecimientos/new";
    }

    @GetMapping("/{id}/update")
    public String actualizarGet(@PathVariable Long id) {
        return "redirect:/establecimientos/" + id + "/edit";
    }

    // ------------------------------------------------------------------ AUXILIARES

    private void prepararFormulario(Model model, boolean modoEdicion) {
        model.addAttribute("modoEdicion", modoEdicion);
        model.addAttribute("tipos", Establecimiento.TIPOS);
        model.addAttribute("usuarios", usuarioService.listarActivos());   // contenido del <select>
    }

    /**
     * Validaciones propias que no se pueden expresar con anotaciones del DTO.
     */
    private void validarReglasPropias(EstablecimientoDTO dto, MultipartFile imagenFile, BindingResult result) {

        // 1) El tipo debe ser uno de los valores permitidos
        if (!result.hasFieldErrors("tipo") && !Establecimiento.TIPOS.contains(dto.getTipo())) {
            result.rejectValue("tipo", "InvalidTipo", "Tipo no válido");
        }

        // 2) El propietario seleccionado debe existir y estar activo
        if (!result.hasFieldErrors("idPropietario") && dto.getIdPropietario() != null
                && !usuarioService.existeActivo(dto.getIdPropietario())) {
            result.rejectValue("idPropietario", "InvalidPropietario", "Propietario no válido");
        }

        // 3) Si se exige edad mínima de 18 o más, la cuenta confirmada es obligatoria
        if (!result.hasFieldErrors("edadMinima") && dto.getEdadMinima() != null
                && dto.getEdadMinima() >= 18 && !dto.isExigeCuentaConfirmada()) {
            result.rejectValue("exigeCuentaConfirmada", "AgeRequiresConfirmed",
                    "Con edad mínima de 18 o más hay que exigir cuenta confirmada");
        }

        // 4) Imagen: tamaño máximo y tipo permitido
        if (imagenFile != null && !imagenFile.isEmpty()) {
            if (imagenFile.getSize() > FileStorageService.MAX_SIZE_BYTES) {
                result.rejectValue("imagen", "ImageSize", "La imagen supera el tamaño máximo (2 MB)");
            } else if (!fileStorage.isAllowedType(imagenFile.getContentType())) {
                result.rejectValue("imagen", "ImageType", "Formato de imagen no permitido");
            }
        }
    }
}
