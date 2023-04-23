package com.springsecurity3withthymeleaf.asset.user_profile.controller;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Gender;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Title;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfileFiles;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileFilesService;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/userDetails")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final UserProfileFilesService userProfileFilesService;

    // Common things for an userDetails add and update
    private String commonThings(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "userDetails/addUserDetails";
    }

    //When scr called file will send to
    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("filename") String filename) {
        UserProfileFiles file = userProfileFilesService.findByNewID(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all userDetails data
    @RequestMapping
    public String findAll(Model model) {
        model.addAttribute("userDetailses", userProfileService.findAll());
        model.addAttribute("contendHeader", "User Details Registration");
        return "userDetails/userDetails";
    }

    //Send on userDetails details
    @GetMapping(value = "/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        UserProfile userProfile = userProfileService.findById(id);
        model.addAttribute("userDetail", userProfile);
        model.addAttribute("file", userProfileFilesService.userDetailsFileDownloadLinks(userProfile));
        return "userDetails/userDetails-detail";
    }

    //Send userDetails data edit
    @GetMapping(value = "/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        UserProfile userProfile = userProfileService.findById(id);
        model.addAttribute("userDetails", userProfile);
        model.addAttribute("addStatus", false);
        model.addAttribute("file", userProfileFilesService.userDetailsFileDownloadLinks(userProfile));
        return commonThings(model);
    }

    //Send an userDetails add form
    @GetMapping(value = {"/add"})
    public String addForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("userDetails", new UserProfile());
        return commonThings(model);
    }

    //Employee add and update
    @PostMapping(value = {"/save", "/update"})
    public String add(@Valid @ModelAttribute UserProfile userProfile, BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("userDetails", userProfile);
            return commonThings(model);
        }
        //after save userDetails files and save userDetails
        UserProfile userProfileDb = userProfileService.persist(userProfile);

        try {
            //save userDetails images file
            if (userProfile.getFile() != null) {
                UserProfileFiles userProfileFiles =
                        userProfileFilesService.findByUserDetails(userProfileDb);
                if (userProfileFiles != null) {
                    // update new contents
                    userProfileFiles.setPic(userProfile.getFile().getBytes());
                    // Save all to database
                } else {
                    userProfileFiles = new UserProfileFiles(userProfile.getFile().getOriginalFilename(),
                                                            userProfile.getFile().getContentType(),
                                                            userProfile.getFile().getBytes(),
                                                            userProfile.getNic().concat("-" + LocalDateTime.now()),
                                                            UUID.randomUUID().toString().concat("userDetails"));
                    userProfileFiles.setUserProfile(userProfile);
                }
                userProfileFilesService.persist(userProfileFiles);
            }
            return "redirect:/userDetails";

        } catch (Exception e) {
            ObjectError error = new ObjectError("userDetails",
                                                "There is already in the system. \n Error happened because of Image. \n " +
                            "System message -->" + e);
            result.addError(error);
            model.addAttribute("addStatus", true);
            model.addAttribute("userDetails", userProfile);
            return commonThings(model);
        }
    }

    @GetMapping(value = "/remove/{id}")
    public String remove(@PathVariable Long id) {
        userProfileService.delete(id);
        return "redirect:/userDetails";
    }

    //To search userDetails any giving userDetails parameter
    @GetMapping(value = "/search")
    public String search(Model model, UserProfile userProfile) {
        model.addAttribute("userDetails", userProfileService.search(userProfile));
        return "userDetails/userDetails-detail";
    }

}
