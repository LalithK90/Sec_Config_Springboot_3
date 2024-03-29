package com.springsecurity3withthymeleaf.asset.role.controller;


import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @RequestMapping
    public String rolePage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/role";
    }


    @GetMapping( value = "/{id}" )
    public String roleView(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("addStatus", false);
        return "role/addRole";
    }


    @GetMapping( value = "/edit/{id}" )
    public String editRoleFrom(@PathVariable( "id" ) Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("addStatus", false);
        return "role/addRole";
    }


    @GetMapping( value = {"/add"} )
    public String roleAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("role", new Role());
        return "role/addRole";
    }

    @PostMapping( value = {"/add", "/update"} )
    public String addRole(@Valid @ModelAttribute Role role, BindingResult result, Model model
            , RedirectAttributes redirectAttributes) {

        if ( result.hasErrors() && role.getId() == null ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("role", role);
            return "role/addRole";
        }

        try {
            roleService.persist(role);
            return "redirect:/role";
        } catch ( Exception e ) {
            ObjectError error = new ObjectError("role",
                                                "This role is already in the System <br/>System message -->" + e);
            result.addError(error);
            model.addAttribute("addStatus", false);
            model.addAttribute("role", role);
            return "role/addRole";
        }

    }

    @GetMapping( value = "/remove/{id}")
    public String removeRole(@PathVariable( "id" ) Long id) {
        roleService.delete(id);
        return "redirect:/role";
    }

    @GetMapping( value = "/search" )
    public String search(Model model, Role role) {
        model.addAttribute("role", roleService.search(role));
        return "role/role";
    }
}
