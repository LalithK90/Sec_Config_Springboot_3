package com.springsecurity3withthymeleaf.asset.common_asset.controller;


import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.util.service.DateTimeAgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UiController {

    private final UserService userService;
    private final DateTimeAgeService dateTimeAgeService;


    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/home", "/mainWindow"})
    public String getHome(Model model) {
        //do some logic here if you want something to be done whenever
        /*User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Petition> petitionSet = new HashSet<>();
        minutePetitionService
                .findByEmployeeAndCreatedAtBetween(authUser.getEmployee(),
                        dateTimeAgeService
                                .dateTimeToLocalDateStartInDay(LocalDate.now()),
                        dateTimeAgeService
                                .dateTimeToLocalDateEndInDay(LocalDate.now())).forEach(
                minutePetition -> {
                    petitionSet.add(petitionService.findById(minutePetition.getPetition().getId()));
                });
        model.addAttribute("petitions", petitionSet.toArray());*/
        return "mainWindow";
    }

    @GetMapping(value = {"/login"})
    public String getLogin() {
        return "login/login";
    }


}